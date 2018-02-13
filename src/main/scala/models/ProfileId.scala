package models

import slick.lifted.MappedTo

case class ProfileId(value: Int) extends AnyVal with MappedTo[Int]

object ProfileId {
  implicit val strongType = StrongType(ProfileId.apply)
}