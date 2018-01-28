import Dependencies._

enablePlugins(SbtNativePackager)

name := "sputnik-scraper"

version := "0.1"

scalaVersion := "2.12.4"

lazy val root = (project in file(".")).settings(
  libraryDependencies ++= allDeps,
  resolvers += Resolver.jcenterRepo,
  resolvers += Resolver.bintrayRepo("akka", "maven")
)