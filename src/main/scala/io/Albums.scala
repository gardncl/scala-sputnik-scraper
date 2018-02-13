package io

import akka.Done
import io.SlickProfile.api._
import models.{Album, AlbumId, BandId, SoundOffId}
import org.joda.time.Years
import slick.lifted.Tag

import scala.concurrent.ExecutionContext

class Albums(tag: Tag) extends Table[Album](tag, "albums") with ColumnTypes {

  def id = column[Option[AlbumId]]("id")

  def soundOffId = column[SoundOffId]("sound_off_id", O.PrimaryKey)

  def bandId = column[Option[BandId]]("band_id")

  def releaseYear = column[Option[Years]]("release_year")

  def * =
    (
      soundOffId,
      id,
      bandId,
      releaseYear
    ) <> (Album.tupled, Album.unapply)
}

object Albums {

  val query = TableQuery[Albums]

  def insertAlbumBySoundOffId(soundOffId: SoundOffId)
                             (implicit ec: ExecutionContext): DBIO[Done] = {
    (query += Album(soundOffId)).map(_ => Done)
  }

}
