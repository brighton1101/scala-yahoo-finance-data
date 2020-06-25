import sbt._

object Dependencies {
  lazy val main = Seq(
  	"org.jsoup" % "jsoup" % "1.13.1",
  	"com.typesafe" % "config" % "1.4.0",
  	"com.google.cloud.functions" % "functions-framework-api" % "1.0.1",
  	"com.google.code.gson" % "gson" % "2.8.6",
  	"org.scalatest" %% "scalatest" % "3.1.1"
  )
}
