import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.brighton1101"
ThisBuild / organizationName := "brighton1101"

lazy val root = (project in file("."))
  .settings(
    name := "stockdata",
    libraryDependencies ++= main
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
