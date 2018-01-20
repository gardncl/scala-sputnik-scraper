import sbt._

object Dependencies {

  object v {
    val cats = "0.9.0"
    val logback = "1.2.3"
    val scalacheck = "1.13.4"
    val scalalogging = "3.7.2"
    val scalatest = "3.0.1"
    val akka = "2.4.17"
    val scrape = "0.4.0"
    val slick = "3.2.1"
    val slickpg = "0.15.3"
  }

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % v.akka,
    "com.typesafe.akka" %% "akka-slf4j" % v.akka
  )

  val scalatest = Seq(
    "org.scalatest" %% "scalatest" % v.scalatest % "test",
    "org.scalacheck" %% "scalacheck" % v.scalacheck % "test")

  val scrape = Seq(
    "io.bfil" %% "scalescrape" % v.scrape
  )

  val slick = Seq(
    "com.typesafe.slick" %% "slick" % v.slick,
    "com.typesafe.slick" %% "slick-hikaricp" % v.slick,
    "com.github.tminglei" %% "slick-pg" % v.slickpg,
    "com.github.tminglei" %% "slick-pg_joda-time" % v.slickpg,
    "com.github.tminglei" %% "slick-pg_spray-json" % v.slickpg
  )

  val logging = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % v.scalalogging,
    "ch.qos.logback" % "logback-classic" % v.logback
  )

  lazy val allDeps = akka ++ scalatest ++ scrape ++ slick ++ logging
}
