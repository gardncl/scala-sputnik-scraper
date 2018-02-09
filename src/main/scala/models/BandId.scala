package models

import slick.lifted.MappedTo

case class BandId(value: Int) extends AnyVal with MappedTo[Int]

object BandId {
  implicit val strongType = StrongType(BandId.apply)
}