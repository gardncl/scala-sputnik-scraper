package io

import akka.Done
import io.SlickProfile.api._
import models.{Profile, ProfileId}
import org.joda.time.LocalDate

class Profiles(tag: Tag) extends Table[Profile](tag, "profiles") {
  def id = column[ProfileId]("id", O.PrimaryKey, O.AutoInc)

  def userName = column[String]("user_name", O.Unique)

  def joinedDate = column[Option[LocalDate]]("joined_date")

  override def * = (
    id,
    userName,
    joinedDate
  ) <> (Profile.tupled, Profile.unapply)
}

object Profiles {

  val query = TableQuery[Profiles]

  def insertAndReturnId(profile: Profile): DBIO[ProfileId] =
    query.map(p => (p.userName, p.joinedDate))
      .returning(query.map(_.id))
      .+=((profile.userName, profile.joinedDate))

  def insert(profile: Profile): DBIO[Int] =
    query += profile

}