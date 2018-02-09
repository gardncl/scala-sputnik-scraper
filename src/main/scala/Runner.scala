import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import io.SlickProfile.api.Database
import migrations.MigrationsList
import streams.KitchenSink

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

    initDb
//    KitchenSink.apply.run()

//    scala.concurrent.Await.ready(KitchenSink.apply.run(), FiniteDuration(1000, "ms"))
  }

  def initDb(implicit ec: ExecutionContext,
             database: Database): Future[Done] = {
    MigrationsList.initdb
    MigrationsList.runAll
  }
}


