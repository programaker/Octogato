import sbt._

object Dependencies {
  val CatsCore = "org.typelevel" %% "cats-core" % Versions.CatsCore
  val CatsEffect = "org.typelevel" %% "cats-effect" % Versions.CatsEffect

  val CirceCore = "io.circe" %% "circe-core" % Versions.CirceCore
  val CirceGeneric = "io.circe" %% "circe-generic" % Versions.CirceCore
  val CirceParser = "io.circe" %% "circe-parser" % Versions.CirceCore
  val CirceRefined = "io.circe" %% "circe-refined" % Versions.CirceCore

  val SttpCore = "com.softwaremill.sttp.client3" %% "core" % Versions.SttpCore
  val SttpCatsBackend = "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % Versions.SttpCore
  val SttpCirce = "com.softwaremill.sttp.client3" %% "circe" % Versions.SttpCore

  val Refined = "eu.timepit" %% "refined" % Versions.Refined
  val Mouse = "org.typelevel" %% "mouse" % Versions.Mouse
  val OdinCore = "com.github.valskalla" %% "odin-core" % Versions.OdinCore
  val PureconfigCore = "com.github.pureconfig" %% "pureconfig-core" % Versions.PureConfigCore
}

private object Versions {
  val CatsCore = "2.6.1"
  val CatsEffect = "3.2.9"
  val CirceCore = "0.14.1"
  val Mouse = "1.0.7"
  val Refined = "0.9.27"
  val OdinCore = "0.13.0"
  val SttpCore = "3.3.16"
  val PureConfigCore = "0.17.0"
}