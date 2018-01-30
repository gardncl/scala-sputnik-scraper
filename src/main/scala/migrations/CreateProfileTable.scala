package migrations

import slick.migration.api.{PostgresDialect, TableMigration}
import io.SlickProfile.api._
import org.joda.time.LocalDate

object CreateProfileTable extends Migration {
  implicit val dialect = new PostgresDialect
  override val name: String = "CreateProfileTable"
  override val migration = TableMigration(TableQuery[Profiles]).create
    .addColumns(_.id, _.username, _.joinedDate)

  class Profiles(tag: Tag) extends Table[Unit](tag, "profiles") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("user_name")
    def joinedDate = column[LocalDate]("joined_date")

    override def * = ()
  }
}
