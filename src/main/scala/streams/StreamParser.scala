package streams

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import io.SlickProfile.api._
import io.{Albums, Profiles, Ratings}
import models.{Profile, Rating, SoundOffId}
import parsers.{ProcessRating, ProcessSoundOff}

import scala.concurrent.{ExecutionContext, Future}

object StreamParser {

  type Seq[+A] = scala.collection.immutable.Seq[A]
  val Seq = scala.collection.immutable.Seq

  private val threads = 3
  private val batchSize = 5

  def apply()(implicit sys: ActorSystem,
              mat: Materializer,
              ec: ExecutionContext,
              db: Database): RunnableGraph[NotUsed] =
    Source(40 to 40)
      .map(SoundOffId.apply)
      .grouped(batchSize)
      .mapAsync(threads) {
        writeAlbumToDb
      }
      .mapConcat(identity)
      .to(insertRatings)

  //      .to(Sink.combine(insertArtistMetadata, insertRatings)(
  //        Broadcast[SoundOffId](_)))

  private def writeAlbumToDb(albums: Seq[SoundOffId])(implicit ec: ExecutionContext, db: Database): Future[Seq[SoundOffId]] =
    for {
      _ <- Future.successful(println(s"Entering ${albums.length} albums to the database"))
      _ <- db.run(DBIO.sequence(albums.map(Albums.insertAlbumBySoundOffId)))
    } yield {
      albums
    }

  private def getRatings(lineAndId: (String, SoundOffId)): Future[(Rating, Profile)] =
    Future.successful(ProcessRating.parseLineToRatingAndProfile(lineAndId))

  private def soundOffIdToLines(soundOffId: SoundOffId): Future[Seq[(String, SoundOffId)]] =
    Future.successful {
      val lines = ProcessSoundOff.getLines(soundOffId)
      println(s"Parsed $soundOffId and got ${lines.length} lines")
      lines
    }

  private def writeRatingsToDb(ratings: Seq[Rating])(implicit ec: ExecutionContext, db: Database): Future[Done] =
    for {
      _ <- Future.successful(ratings.map(println))
      _ <- db.run(DBIO.sequence(ratings.map(Ratings.insert)))
    } yield {
      Done
    }


  private def writeProfilesToDb(ratingAndProfile: Seq[(Rating, Profile)])(implicit ec: ExecutionContext, db: Database): Future[Seq[Rating]] = {
    ratingAndProfile.map(_._1).foreach(println)
    for {
      values <- db.run(DBIO.sequence(ratingAndProfile.map(insertProfileAndGetRating)))
    } yield {
      values
    }
  }

  /**
    * Checks if the profile already exists in the database to avoid duplicates.
    */
  private def insertProfileAndGetRating(ratingAndProfile: (Rating, Profile))
                                       (implicit ec: ExecutionContext, db: Database): DBIO[Rating] = {
    (for {
      profileIdOption <- Profiles.doesUsernameExist(ratingAndProfile._2.userName)
      profileId <- profileIdOption match {
        case Some(id) => DBIO.successful(id)
        case None => Profiles.insertAndReturnId(ratingAndProfile._2)
      }
    } yield {
      Rating(ratingAndProfile._1.soundOffId, profileId, ratingAndProfile._1.rating, ratingAndProfile._1.date)
    }).transactionally
  }

  private def insertRatings(implicit ec: ExecutionContext, db: Database): Sink[SoundOffId, NotUsed] =
    Flow[SoundOffId]
      .mapAsync(threads) {
        soundOffIdToLines
      }
      .mapConcat(identity)
      .mapAsync(threads) {
        getRatings
      }
      .grouped(batchSize)
      .mapAsync(threads) {
        writeProfilesToDb
      }
      .mapAsync(threads) {
        writeRatingsToDb
      }
      .to(Sink.ignore)

  private def insertArtistMetadata: Sink[SoundOffId, NotUsed] =
    Flow[SoundOffId]
      .map { album =>
        println(s"Inserting artist metadata $album")
        album
      }
      .to(Sink.ignore)

}
