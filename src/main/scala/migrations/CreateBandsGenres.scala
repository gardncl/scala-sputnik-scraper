package migrations

import io.SlickProfile.api._
import gardncl.Migration
import io.ColumnTypes
import models.{BandId, GenreId}
import slick.migration.api.{PostgresDialect, TableMigration}

object CreateBandsGenres extends Migration {
  implicit val dialect = new PostgresDialect

  override val  name: String = "CreateBandsGenres"
  override val migration = TableMigration(TableQuery[BandsGenres]).create
    .addColumns(_.bandId, _.genreId)

  class BandsGenres(tag: Tag) extends Table[Unit](tag, "bands_genres") with ColumnTypes {

    def bandId = column[BandId]("band_id")

    def genreId = column[GenreId]("genre_id")

    override def * = ()
  }
}
