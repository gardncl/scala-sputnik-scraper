package models

import shapeless.{::, Generic, HNil}


trait StrongType[T] {
  type Underlying

  def map(wrapped: T): Underlying

  def comap(underlying: Underlying): T
}

object StrongType {
  type Aux[T,U] = StrongType[T] { type Underlying = U}

  def apply[T, U, G <: U :: HNil](f: U => T)(
    implicit gen: Generic.Aux[T, G]
  ): Aux[T, U] =
    new StrongType[T] {
      type Underlying = U

      def map(wrapped: T): Underlying = gen.to(wrapped).head

      def comap(underlying: Underlying) = f(underlying)
    }
}
