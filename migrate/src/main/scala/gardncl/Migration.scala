package gardncl

import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._
import slick.migration.api.{SqlMigration, Migration => ApiMigration}

import scala.concurrent.{ExecutionContext, Future}

trait Migration {
  val name: String
  val migration: ApiMigration

  def run(implicit ec: ExecutionContext, db: Database): Future[Boolean] = {
    isPending.flatMap { pending =>
      if (pending) {
        db.run(
          DBIO
            .seq(
              migration(),
              addMigration()
            )
            .transactionally
        )
          .map(_ => true)
      } else {
        Future(pending)
      }
    }
  }

  def addMigration: ApiMigration = {
    SqlMigration(s"INSERT INTO migrations (name) VALUES ('${name}')")
  }

  def isPending(implicit ec: ExecutionContext,
                db: Database): Future[Boolean] = {
    val presentQuery: DBIO[Seq[(Int)]] = sql"""
          SELECT
            1
          FROM
            migrations
          WHERE
            migrations.name = ${name}""".as[Int]
    db.run(presentQuery).map(_.isEmpty)
  }
}
