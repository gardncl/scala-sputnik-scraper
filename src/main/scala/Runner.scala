import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import io.SlickProfile.api.Database
import migrations.Migrator
import streams.StreamParser

import scala.concurrent.{ExecutionContext, Future}

object Runner extends App {
  main

  def main = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val database: Database =
      Database.forURL(
        "jdbc:postgresql://localhost:5432/sputnik",
        "sputnik",
        "my_password")
    implicit val ec: ExecutionContext = system.dispatcher

    StreamParser.main
  }

  def initDb(implicit ec: ExecutionContext,
             database: Database): Future[Done] = {
    Migrator.initdb
    Migrator.runAll
  }
}


