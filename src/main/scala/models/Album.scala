package models

import org.joda.time.Years

case class Album(soundOffId: SoundOffId, id: Option[AlbumId] = None, bandId: Option[BandId] = None, releaseYear: Option[Years] = None)
