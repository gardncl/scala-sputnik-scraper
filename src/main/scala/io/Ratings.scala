package io

import io.SlickProfile.api._
import models.{AlbumId, Rating}
import org.joda.time.LocalDate

class Ratings(tag: Tag) extends Table[Rating](tag, "ratings") {
  def profileId = column[Int]("profile_id")

  def albumId = column[AlbumId]("album_id")

  def rating = column[Double]("rating")

  def date = column[Option[LocalDate]]("date")

  override def * = (
    albumId,
    profileId,
    rating,
    date,
  ) <> (Rating.tupled, Rating.unapply)
}
