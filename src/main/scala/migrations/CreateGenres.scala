package migrations

import gardncl.Migration
import io.ColumnTypes
import io.SlickProfile.api._
import models.GenreId
import slick.migration.api.{PostgresDialect, TableMigration}

object CreateGenres extends Migration {
  implicit val dialect = new PostgresDialect

  override val name: String = "CreateGenres"
  override val migration = TableMigration(TableQuery[Genres]).create
    .addColumns(_.id, _.name)

  class Genres(tag: Tag) extends Table[Unit](tag, "genres") with ColumnTypes {

    def id = column[GenreId]("id", O.PrimaryKey)

    def name = column[String]("name", O.Unique)

    override def * = ()
  }
}
