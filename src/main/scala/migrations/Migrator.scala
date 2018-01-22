package migrations

object Migrator extends {

  implicit val dialect = new

  val init =
    TableMigration(myTable)
      .create
      .addColumns(_.col1, _.col2)
      .addIndexes(_.index1)
      .renameColumn(_.col03, "col3")
  val seed =
    SqlMigration("insert into myTable (col1, col2) values (10, 20)")

  val migration = init & seed

  db.run(migration())
  private val migrations: List[Migration] = ???
}
