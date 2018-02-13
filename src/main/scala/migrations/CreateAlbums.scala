package migrations

import io.SlickProfile.api._
import org.joda.time.Years
import slick.migration.api.{PostgresDialect, TableMigration}
import gardncl._
import io.ColumnTypes
import models.{AlbumId, BandId, SoundOffId}

object CreateAlbums extends Migration {
  implicit val dialect = new PostgresDialect

  override val name: String = "CreateAlbums"
  override val migration = TableMigration(TableQuery[Albums]).create
    .addColumns(_.id, _.soundOffId, _.bandId, _.releaseYear)

  class Albums(tag: Tag) extends Table[Unit](tag, "albums") with ColumnTypes {

    def id = column[Option[AlbumId]]("id")

    def soundOffId = column[SoundOffId]("sound_off_id", O.PrimaryKey)

    def bandId = column[Option[BandId]]("band_id")

    def releaseYear = column[Option[Years]]("release_year")

    override def * = ()
  }

}
