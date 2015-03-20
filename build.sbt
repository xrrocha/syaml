name := "syaml"

organization := "net.xrrocha"

version := "0.1"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3",
  "io.spray" %%  "spray-json" % "1.3.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.lihaoyi" %% "ammonite-repl" % "0.2.7" % "test"
)

initialCommands in console := "ammonite.repl.Repl.main(null)"
