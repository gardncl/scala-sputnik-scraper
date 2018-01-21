import sbt._

object Dependencies {

  object v {
    val cats = "0.9.0"
    val logback = "1.2.3"
    val scalacheck = "1.13.4"
    val scalalogging = "3.7.2"
    val scalatest = "3.0.1"
    val akka = "2.5.8"
    val scrape = "0.4.0"
    val slick = "3.2.1"
    val slickpg = "0.15.3"
  }

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % v.akka,
    "com.typesafe.akka" %% "akka-slf4j" % v.akka,
    "com.typesafe.akka" %% "akka-stream" % v.akka
  )

  val scrape = Seq(
    "io.bfil" %% "scalescrape" % v.scrape
  )
  lazy val allDeps = akka ++ scrape
}
