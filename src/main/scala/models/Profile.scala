package models

import org.joda.time.LocalDate

case class Profile(id: Int, userName: String, joinedDate: LocalDate)