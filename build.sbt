import Dependencies._

enablePlugins(SbtNativePackager)

name := "sputnik-scraper"

version := "0.1"

scalaVersion := "2.12.4"

lazy val migrate = (project in file("migrate"))
  .settings(
    organization := "gardncl",
    name := "migrate",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    libraryDependencies ++= slick ++ akka,
    resolvers += Resolver.jcenterRepo,
  )

lazy val root = (project in file(".")).settings(
  libraryDependencies ++= allDeps,
  resolvers += Resolver.jcenterRepo,
  resolvers += Resolver.bintrayRepo("akka", "maven")
).dependsOn(migrate)
