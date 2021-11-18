import sbt._

object Dependencies {
  val CatsCore = "org.typelevel" %% "cats-core" % Versions.CatsCore
  val CatsEffect = "org.typelevel" %% "cats-effect" % Versions.CatsEffect

  val CirceCore = "io.circe" %% "circe-core" % Versions.CirceCore
  val CirceGeneric = "io.circe" %% "circe-generic" % Versions.CirceCore
  val CirceParser = "io.circe" %% "circe-parser" % Versions.CirceCore
  val CirceRefined = "io.circe" %% "circe-refined" % Versions.CirceCore

  val Mouse = "org.typelevel" %% "mouse" % Versions.Mouse
  val Refined = "eu.timepit" %% "refined" % Versions.Refined
  val OdinCore = "com.github.valskalla" %% "odin-core" % Versions.OdinCore
}

private object Versions {
  val CatsCore = "2.6.1"
  val CatsEffect = "3.2.9"
  val CirceCore = "0.14.1"
  val Mouse = "1.0.7"
  val Refined = "0.9.27"
  val OdinCore = "0.13.0"
}