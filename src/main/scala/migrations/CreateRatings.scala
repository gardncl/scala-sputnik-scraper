package migrations


import gardncl.Migration
import io.SlickProfile.api._
import org.joda.time.LocalDate
import slick.migration.api.{PostgresDialect, TableMigration}

object CreateRatings extends Migration {

  implicit val dialect = new PostgresDialect

  override val name: String = "CreateRatings"
  override val migration = TableMigration(TableQuery[Ratings])
    .create
    .addColumns(_.profileId, _.albumId, _.rating, _.date)
    .addForeignKeys(_.profilesForeignKey, _.albumsForeignKey)

  class Ratings(tag: Tag) extends Table[Unit](tag, "ratings") {
    def profileId = column[Int]("profile_id")

    def albumId = column[Int]("album_id")

    def rating = column[Double]("rating")

    def date = column[LocalDate]("date")

    def profilesForeignKey =
      foreignKey("ratings_profiles_fkey",
        profileId,
        TableQuery[CreateProfiles.Profiles])(_.id,
        onUpdate = ForeignKeyAction.Cascade,
        onDelete = ForeignKeyAction.Cascade)

    def albumsForeignKey =
      foreignKey("ratings_albums_fkey",
        albumId,
        TableQuery[CreateAlbums.Albums])(_.id,
        onUpdate = ForeignKeyAction.Cascade,
        onDelete = ForeignKeyAction.Cascade)

    override def * = ()
  }

}
