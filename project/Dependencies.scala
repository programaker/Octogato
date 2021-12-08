import sbt._

object Dependencies {
  val libraries = Seq(
    "org.typelevel" %% "cats-core" % Versions.CatsCore,
    "org.typelevel" %% "cats-effect" % Versions.CatsEffect,

    "io.circe" %% "circe-core" % Versions.CirceCore,
    "io.circe" %% "circe-generic" % Versions.CirceCore,
    "io.circe" %% "circe-parser" % Versions.CirceCore,
    "io.circe" %% "circe-refined" % Versions.CirceCore,

    "com.softwaremill.sttp.client3" %% "core" % Versions.SttpCore,
    "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % Versions.SttpCore,
    "com.softwaremill.sttp.client3" %% "circe" % Versions.SttpCore,

    "com.monovore" %% "decline" % Versions.Decline,
    "com.monovore" %% "decline-effect" % Versions.Decline,
    "com.monovore" %% "decline-refined" % Versions.Decline,

    "eu.timepit" %% "refined" % Versions.Refined,
    "eu.timepit" %% "refined-cats" % Versions.Refined,

    "org.typelevel" %% "mouse" % Versions.Mouse,
    "com.github.valskalla" %% "odin-core" % Versions.OdinCore,
    "com.github.pureconfig" %% "pureconfig-core" % Versions.PureConfigCore,
    "ch.qos.logback" % "logback-classic" % Versions.Logback
  )
}

private object Versions {
  val CatsCore = "2.7.0"
  val CatsEffect = "3.3.0"
  val CirceCore = "0.14.1"
  val Mouse = "1.0.7"
  val Refined = "0.9.28"
  val OdinCore = "0.13.0"
  val SttpCore = "3.3.18"
  val PureConfigCore = "0.17.1"
  val Decline = "2.2.0"
  val Logback = "1.2.7"
}