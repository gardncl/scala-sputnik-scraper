package models

import slick.lifted.MappedTo

case class AlbumId(value: Int) extends AnyVal with MappedTo[Int]

object AlbumId {
  implicit val strongType = StrongType(AlbumId.apply)
}