package migrations

import akka.Done
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

object Migrator extends {
  def run(migrationName: String)(implicit ec: ExecutionContext,
                                 db: Database): Future[Done] =
    migrations
      .find(_.name == migrationName)
      .getOrElse(throw new IllegalArgumentException(
        s"migration with name $migrationName not found"))
      .run
      .andThen {
        case Success(false) =>
          println(s"$migrationName already applied; skipping")
      }
      .map { _ =>
        Done
      }

  def runAll(implicit ec: ExecutionContext, db: Database): Future[Done] = {
    def runMigrationWithPrint(migrationName: String,
                              status: Future[Boolean]): Future[Done] =
      status
        .andThen {
          case Success(true) =>
            println(s"Running migration $migrationName")
        }
        .map { _ =>
          Done
        }
    migrations.foldLeft(Future(Done): Future[Done])(
      (acc: Future[Done], m: Migration) =>
        acc.flatMap(_ => runMigrationWithPrint(m.name, m.run)))
  }

  def initdb(implicit db: Database): Future[Any] =
    db.run(
      sqlu"CREATE TABLE migrations (name VARCHAR(255))"
    )

  private val migrations: List[Migration] = ???
}
