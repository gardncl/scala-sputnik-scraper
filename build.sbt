import Dependencies._

name := "sputnik-scraper"

version := "0.1"

scalaVersion := "2.12.4"

lazy val root = (project in file(".")).settings(
  libraryDependencies ++= allDeps
)