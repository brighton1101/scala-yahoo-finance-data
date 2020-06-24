import sbt._

object Dependencies {
  lazy val main = Seq(
  	"org.jsoup" % "jsoup" % "1.13.1",
  	"com.typesafe" % "config" % "1.4.0",
  	/*
  	"com.fasterxml.jackson.core" % "jackson-databind" % "2.2.2",
  	"com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.2.2",
  	*/
  	"org.scalatest" %% "scalatest" % "3.1.1"
  )
}
