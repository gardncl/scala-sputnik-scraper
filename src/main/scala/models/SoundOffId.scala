package models

import slick.lifted.MappedTo

case class SoundOffId(value: Int) extends AnyVal with MappedTo[Int]

object SoundOffId {
  implicit val strongType = StrongType(SoundOffId.apply)
}