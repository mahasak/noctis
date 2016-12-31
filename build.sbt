import sbt.Keys._

organization := "io.bigbears"
name := "noctis"
version := "1.0.0-b"

sources in(Compile, doc) := Seq.empty
publishArtifact in(Compile, packageDoc) := false

scalaVersion := "2.11.8"
scalacOptions := Seq("-feature", "-deprecation")
//scalacOptions := Seq("-unchecked", "-optimise", "-encoding", "utf8", "-Yno-adapted-args", "-target:jvm-1.8")

lazy val root = (project in file(".")).enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-core" % "1.1.8"
  , "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.4"
  , "com.typesafe" % "config" % "1.3.1"
  , "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  , "org.liquibase" % "liquibase-core" % "3.5.3"
  , "org.seleniumhq.selenium" % "selenium-api" % "3.0.1"
  , "org.seleniumhq.selenium" % "selenium-support" % "3.0.1"
  , "org.seleniumhq.selenium" % "selenium-firefox-driver" % "3.0.1"
  , "org.xerial" % "sqlite-jdbc" % "3.15.1"
  , "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
