import sbt._

object Dependencies {

  object v {
    val akka = "2.5.8"
    val migrations = "0.4.2"
    val scrape = "2.1.0"
    val scopt = "3.6.0"
    val shapeless = "2.3.3"
    val slick = "3.2.1"
    val slickpg = "0.15.3"
    val postgresql = "42.1.4"
    val logging = "1.1.3"
  }

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % v.akka,
    "com.typesafe.akka" %% "akka-slf4j" % v.akka,
    "com.typesafe.akka" %% "akka-stream" % v.akka
  )

  val runner = Seq(
    "com.github.scopt" %% "scopt" % v.scopt
  )

  val scrape = Seq(
    "net.ruippeixotog" %% "scala-scraper" % v.scrape
  )

  val shapeless = Seq(
    "com.chuusai" %% "shapeless" % v.shapeless
  )

  val slick = Seq(
    "org.postgresql" % "postgresql" % v.postgresql,
    "io.github.nafg" %% "slick-migration-api" % v.migrations,
    "com.typesafe.slick" %% "slick" % v.slick,
    "com.typesafe.slick" %% "slick-hikaricp" % v.slick,
    "com.github.tminglei" %% "slick-pg" % v.slickpg,
    "com.github.tminglei" %% "slick-pg_joda-time" % v.slickpg
  )

  val logging = Seq(
    "ch.qos.logback" % "logback-classic" % v.logging
  )

  lazy val allDeps = akka ++ runner ++ scrape ++ shapeless ++ slick ++ logging
}
