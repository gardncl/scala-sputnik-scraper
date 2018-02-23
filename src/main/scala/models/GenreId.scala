package models

import slick.lifted.MappedTo

case class GenreId(value: Int) extends AnyVal with MappedTo[Int]

object GenreId {
  implicit val strongType = StrongType(ProfileId.apply)
}