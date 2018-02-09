package streams

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl._
import models.{AlbumId, Rating}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

object KitchenSink {

  type Seq[+A] = scala.collection.immutable.Seq[A]
  val Seq = scala.collection.immutable.Seq

  private val threads = 10
  private val batchSize = 10
  private val seconds = 10

  def apply()(implicit sys: ActorSystem,
              mat: Materializer,
              ec: ExecutionContext): RunnableGraph[NotUsed] =
    Source(0 to 280)
      .map(AlbumId.apply)
      .grouped(batchSize)
      .mapAsync(threads) {
        writeAlbumToDb
      }
      .mapConcat(identity)
      .to(Sink.combine(insertRatings, insertArtistMetadata)(
        Broadcast[AlbumId](_)))

  private def writeAlbumToDb(albums: Seq[AlbumId])(implicit ec: ExecutionContext): Future[Seq[AlbumId]] =
    Future {
      val random = math.abs(Random.nextLong() % seconds)
      println(s"Sleeping: $random")
      Thread.sleep(random)
      println(s"Writing ${albums.length} to db")
      albums
    }

  private def getRatings(albumId: AlbumId): Future[Seq[Rating]] =
    Future.successful(Seq(Rating(albumId, 1, 5, None)))

  private def writeToDb(rating: Rating)(implicit ec: ExecutionContext): Future[Done] =
    Future {
      Thread.sleep(Random.nextLong() % seconds)
      println(s"Writing $rating to db")
      Done
    }

  private def insertRatings(implicit ec: ExecutionContext): Sink[AlbumId, NotUsed] =
    Flow[AlbumId]
      .mapAsync(threads) {
        getRatings
      }
      .mapConcat(identity)
      .mapAsync(threads) {
        writeToDb
      }
      .to(Sink.ignore)

  private def insertArtistMetadata: Sink[AlbumId, NotUsed] =
    Flow[AlbumId]
      .map { album =>
        println(s"Inserting artist metadata $album")
        album
      }
      .to(Sink.ignore)

}
