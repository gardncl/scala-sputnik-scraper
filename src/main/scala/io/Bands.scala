package io

import io.SlickProfile.api._
import models.{Band, BandId}
import slick.lifted.Tag

class Bands(tag: Tag) extends Table[Band](tag, "bands") with ColumnTypes {

  def id = column[BandId]("id", O.PrimaryKey)

  def name = column[String]("name")

  override def * = (
    id,
    name
  ) <> (Band.tupled, Band.unapply)
}
