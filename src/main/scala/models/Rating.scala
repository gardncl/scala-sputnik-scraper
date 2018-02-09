package models

import org.joda.time.LocalDate

case class Rating(albumId: AlbumId = AlbumId(1), profileId: Int, rating: Double, date: Option[LocalDate])
