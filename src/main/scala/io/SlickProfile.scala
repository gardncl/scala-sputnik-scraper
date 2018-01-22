package io

import com.github.tminglei.slickpg._
import slick.jdbc.JdbcCapabilities

trait SlickProfile extends ExPostgresProfile with PgDateSupportJoda {
  override val api = PgAPI

  protected override def computeCapabilities =
    super.computeCapabilities + JdbcCapabilities.insertOrUpdate

  object PgAPI extends API with DateTimeImplicits

}

object SlickProfile extends SlickProfile