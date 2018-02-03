import akka.Done
import akka.actor.ActorSystem
import io.SlickProfile.api.Database
import migrations.Migrator

import scala.concurrent.{ExecutionContext, Future}

object Runner extends App {

  val config = scala.util.Properties.envOrNone("")
  implicit val system: ActorSystem = ActorSystem()
  implicit val database: Database =
    Database.forURL(
      "jdbc:postgresql://localhost:5432/sputnik",
      "sputnik",
      "my_password")
  implicit val ec: ExecutionContext = system.dispatcher

  def initDb(implicit ec: ExecutionContext,
             database: Database): Future[Done] = {
    Migrator.initdb
    Migrator.runAll
  }
}


