import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import io.Profiles
import io.SlickProfile.api.Database
import migrations.MigrationsList
import models.{AlbumId, Profile, ProfileId, SoundOffId}
import org.joda.time.LocalDate
import parsers.{ProcessRating, ProcessSoundOff}
import streams.StreamParser
import scala.util.matching.Regex

import scala.concurrent.{ExecutionContext, Future}

object Runner extends App {
  main()

  def main() = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    implicit val database: Database =
      Database.forURL(
        "jdbc:postgresql://localhost:5432/sputnik",
        "sputnik",
        "my_password")

//    initDb
    StreamParser.apply.run()

//   printOutRatings(58)
//    printNumberOfRatings(0 to 100)


  }

  private def printOutRatings(int: Int) = {
    val strings = ProcessSoundOff.getLines(SoundOffId(int)).map(_._1)
    strings.map(println)
  }

  private def printNumberOfRatings(ints: Seq[Int]) =
    for (i <- ints) {
      val soundOffId = SoundOffId(i)
      val strings = ProcessSoundOff.getLines(soundOffId).map(_._1)
      println(s"Processing album #$i it has ${strings.length} ratings")
    }

  private def initDb(implicit ec: ExecutionContext,
             database: Database): Future[Done] = {
    MigrationsList.initdb
    MigrationsList.runAll
  }



}


