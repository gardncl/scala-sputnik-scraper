package migrations

import gardncl.Migration
import slick.migration.api.{PostgresDialect, TableMigration}
import io.SlickProfile.api._
import models.ProfileId
import org.joda.time.LocalDate

object CreateProfiles extends Migration {
  implicit val dialect = new PostgresDialect

  override val name: String = "CreateProfiles"
  override val migration = TableMigration(TableQuery[Profiles]).create
    .addColumns(_.id, _.userName, _.joinedDate)
    .addIndexes(_.userNameIndex)

  class Profiles(tag: Tag) extends Table[Unit](tag, "profiles") {
    def id = column[ProfileId]("id", O.PrimaryKey, O.AutoInc)

    def userName = column[String]("user_name", O.Unique)

    def joinedDate = column[Option[LocalDate]]("joined_date")

    def userNameIndex = index("profiles_user_name_index", userName)

    override def * = ()
  }
}
