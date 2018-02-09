package io

import com.github.tminglei.slickpg._
import slick.jdbc.JdbcCapabilities
import slick.ast.BaseTypedType
import slick.jdbc.{JdbcCapabilities, PositionedParameters, SetParameter}
import slick.lifted.{MappedTo, Rep, StringColumnExtensionMethods}
import models.StrongType

trait MappedToExtensionMethodConversions {
  implicit def mappedToStringColumnExtensionMethods[B1 <: MappedTo[String]](
                                                                             c: Rep[B1])(
                                                                             implicit tm: BaseTypedType[B1]): StringColumnExtensionMethods[String] =
    new StringColumnExtensionMethods[String](c.asInstanceOf[Rep[String]])

  implicit def mappedToOptionStringColumnExtensionMethods[
  B1 <: MappedTo[String]](c: Rep[Option[B1]])(
    implicit tm: BaseTypedType[B1])
  : StringColumnExtensionMethods[Option[String]] =
    new StringColumnExtensionMethods[Option[String]](
      c.asInstanceOf[Rep[Option[String]]])
}

trait StrongTypeStatementSupport {

  implicit def setStrongTypeColumn[T, U](
                                          implicit strongType: StrongType.Aux[T, U],
                                          setParameter: SetParameter[U]): SetParameter[T] =
    (v: T, pp: PositionedParameters) =>
      setParameter.apply(strongType.map(v), pp)
}

trait SlickProfile extends ExPostgresProfile with PgDateSupportJoda {
  override val api = PgAPI

  protected override def computeCapabilities =
    super.computeCapabilities + JdbcCapabilities.insertOrUpdate

  object PgAPI extends API with DateTimeImplicits with MappedToExtensionMethodConversions with StrongTypeStatementSupport

}

object SlickProfile extends SlickProfile