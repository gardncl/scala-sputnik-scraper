package models

import org.joda.time.LocalDate

case class Rating(soundOffId: SoundOffId, profileId: ProfileId, rating: Double, date: Option[LocalDate])
