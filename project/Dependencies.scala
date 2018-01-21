import sbt._

object Dependencies {

  object v {
    val akka = "2.5.8"
    val scrape = "0.4.0"
  }

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % v.akka
  )

  val scrape = Seq(
    "io.bfil" %% "scalescrape" % v.scrape
  )

  lazy val allDeps =  akka ++ scrape
}
