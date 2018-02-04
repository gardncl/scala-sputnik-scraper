package migrations

import gardncl.{Migration, Migrator}

object MigrationsList extends Migrator {
  override val migrations: List[Migration] = List(
    CreateProfiles,
    CreateBands,
    CreateAlbums,
    CreateRatings
  )
}
