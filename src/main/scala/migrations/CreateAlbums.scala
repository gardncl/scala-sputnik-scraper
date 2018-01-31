package migrations

import io.SlickProfile.api._
import org.joda.time.Years
import slick.migration.api.{PostgresDialect, TableMigration}

object CreateAlbums extends Migration {
  implicit val dialect = new PostgresDialect

  override val name: String = "CreateAlbums"
  override val migration = TableMigration(TableQuery[Albums]).create
    .addColumns(_.id, _.bandId, _.releaseYear)
    .addForeignKeys(_.bandsForeignKey)

  class Albums(tag: Tag) extends Table[Unit](tag, "albums") {
    import io.ColumnTypes._

    def id = column[Long]("id", O.PrimaryKey)

    def bandId = column[Long]("band_id")

    def releaseYear = column[Years]("release_year")

    def bandsForeignKey =
      foreignKey("albums_bands_fkey",
        id,
        TableQuery[Albums])(_.id,
        onUpdate = ForeignKeyAction.Cascade,
        onDelete = ForeignKeyAction.Cascade)

    override def * = ()
  }

}
