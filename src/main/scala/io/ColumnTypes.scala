package io


import io.SlickProfile.api._
import org.joda.time.Years

trait ColumnTypes {

  implicit val yearsColumnType =
    MappedColumnType.base[Years, Int](_.getYears, Years.years)
}

object ColumnTypes extends ColumnTypes
