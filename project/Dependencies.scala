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

  val Decline = "com.monovore" %% "decline" % Versions.Decline
  val DeclineEffect = "com.monovore" %% "decline-effect" % Versions.Decline
  val DeclineRefined = "com.monovore" %% "decline-refined" % Versions.Decline

  val Refined = "eu.timepit" %% "refined" % Versions.Refined
  val Mouse = "org.typelevel" %% "mouse" % Versions.Mouse
  val OdinCore = "com.github.valskalla" %% "odin-core" % Versions.OdinCore
  val PureconfigCore = "com.github.pureconfig" %% "pureconfig-core" % Versions.PureConfigCore
  val LogbackClassic = "ch.qos.logback" % "logback-classic" % Versions.Logback
}

private object Versions {
  val CatsCore = "2.7.0"
  val CatsEffect = "3.3.0"
  val CirceCore = "0.14.1"
  val Mouse = "1.0.7"
  val Refined = "0.9.27"
  val OdinCore = "0.13.0"
  val SttpCore = "3.3.17"
  val PureConfigCore = "0.17.1"
  val Decline = "2.2.0"
  val Logback = "1.2.7"
}