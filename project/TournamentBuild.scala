import sbt._
import sbt.Keys._

object TournamentBuild extends Build {

  lazy val tournament = Project(
    id = "tournament",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Tournament",
      organization := "com.github.jedesah",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.2",
      libraryDependencies ++= Seq(
	      "org.scalatest" %% "scalatest" % "2.0.RC2" % "test",
        "com.github.nscala-time" %% "nscala-time" % "0.6.0",
        "com.chuusai" % "shapeless" % "2.0.0-M1" cross CrossVersion.full
      )
    )
  )
}
