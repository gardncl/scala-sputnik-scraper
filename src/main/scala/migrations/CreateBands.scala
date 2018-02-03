package migrations

import gardncl.Migration
import slick.migration.api.{PostgresDialect, TableMigration}
import io.SlickProfile.api._

object CreateBands extends Migration {
  implicit val dialect = new PostgresDialect

  override val name: String = "CreateBands"
  override val migration = TableMigration(TableQuery[Bands]).create
    .addColumns(_.id, _.name)
    .addIndexes(_.nameIndex)

  class Bands(tag: Tag) extends Table[Unit](tag, "bands") {
    def id = column[Long]("id", O.PrimaryKey)

    def name = column[String]("name")

    def nameIndex = index("bands_name_index", name)

    override def * = ()
  }

}
