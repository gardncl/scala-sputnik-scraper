package io

import akka.Done
import io.SlickProfile.api._
import models.{ProfileId, Rating, SoundOffId}
import org.joda.time.LocalDate

import scala.concurrent.ExecutionContext

class Ratings(tag: Tag) extends Table[Rating](tag, "ratings") {
  def profileId = column[ProfileId]("profile_id")

  def soundOffId = column[SoundOffId]("sound_off_id")

  def rating = column[Double]("rating")

  def date = column[Option[LocalDate]]("date")

  override def * = (
    soundOffId,
    profileId,
    rating,
    date,
  ) <> (Rating.tupled, Rating.unapply)
}

object Ratings {

  val query = TableQuery[Ratings]

  def insert(rating: Rating)
            (implicit ec: ExecutionContext): DBIO[Done] = {
    (query += rating).map(_ => Done)
  }
}