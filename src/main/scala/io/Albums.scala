package io

import io.SlickProfile.api._
import models.{Album, AlbumId, BandId, SoundOffId}
import org.joda.time.Years
import slick.lifted.Tag

class Albums(tag: Tag) extends Table[Album](tag, "albums") with ColumnTypes {

  def id = column[AlbumId]("id", O.PrimaryKey)

  def soundOffId = column[SoundOffId]("sound_off_id")

  def bandId = column[BandId]("band_id")

  def releaseYear = column[Years]("release_year")

  def * =
    (
      id,
      soundOffId,
      bandId,
      releaseYear
    ) <> (Album.tupled, Album.unapply)
}
