import sbt._

object Dependencies {

  object v {
    val akka = "2.5.8"
    val migrations = "0.4.2"
    val scrape = "2.1.0"
    val slick = "3.2.1"
    val slickpg = "0.15.3"
    val postgresql = "42.1.4"
  }

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % v.akka,
    "com.typesafe.akka" %% "akka-slf4j" % v.akka,
    "com.typesafe.akka" %% "akka-stream" % v.akka
  )

  val scrape = Seq(
    "net.ruippeixotog" %% "scala-scraper" % v.scrape
  )

  val slick = Seq(
    "org.postgresql" % "postgresql" % v.postgresql,
    "io.github.nafg" %% "slick-migration-api" % v.migrations,
    "com.typesafe.slick" %% "slick" % v.slick,
    "com.typesafe.slick" %% "slick-hikaricp" % v.slick,
    "com.github.tminglei" %% "slick-pg" % v.slickpg,
    "com.github.tminglei" %% "slick-pg_joda-time" % v.slickpg
  )

  lazy val allDeps = akka ++ scrape ++ slick
}
