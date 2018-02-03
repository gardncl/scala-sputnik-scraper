package migrations

import gardncl.{Migration, Migrator}

object Migrator extends Migrator {
  override val migrations: List[Migration] = List(
    CreateProfiles,
    CreateBands,
    CreateAlbums,
    CreateRatings
  )
}
