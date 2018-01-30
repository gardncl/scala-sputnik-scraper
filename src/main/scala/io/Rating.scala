package io

import org.joda.time.LocalDate

case class Rating(rating: Double, username: String, date: Option[LocalDate])

