package models

import org.joda.time.Years

case class Album(id: AlbumId, soundOffId: SoundOffId, bandId: BandId, releaseYear: Years)
