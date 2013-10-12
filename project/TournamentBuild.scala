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
      scalaVersion := "2.10.3",
      libraryDependencies ++= Seq(
	       "org.scalatest" % "scalatest_2.10" % "2.0.RC1" % "test",
        "com.github.nscala-time" %% "nscala-time" % "0.6.0"
      )
    )
  )
}
