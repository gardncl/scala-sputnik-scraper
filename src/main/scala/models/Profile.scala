package models

import org.joda.time.LocalDate

case class Profile(id: ProfileId, userName: String, joinedDate: Option[LocalDate])