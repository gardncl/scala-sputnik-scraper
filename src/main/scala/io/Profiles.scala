package io

import io.SlickProfile.api._
import models.Profile
import org.joda.time.LocalDate

class Profiles(tag: Tag) extends Table[Profile](tag, "profiles") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def userName = column[String]("user_name")

  def joinedDate = column[LocalDate]("joined_date")

  override def * = (
    id,
    userName,
    joinedDate
  ) <> (Profile.tupled, Profile.unapply)
}
