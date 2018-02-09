package io


import io.SlickProfile.api._
import models.{AlbumId, BandId, SoundOffId}
import org.joda.time.Years

trait ColumnTypes {

  implicit val yearsColumnType =
    MappedColumnType.base[Years, Int](_.getYears, Years.years)

  implicit val albumIdToInt =
    MappedColumnType.base[AlbumId, Int](_.value, AlbumId.apply)

  implicit val soundOffIdToInt =
    MappedColumnType.base[SoundOffId, Int](_.value, SoundOffId.apply)

  implicit val bandIdToInt =
    MappedColumnType.base[BandId, Int](_.value, BandId.apply)

}

object ColumnTypes extends ColumnTypes
