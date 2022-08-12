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
    "eu.timepit" %% "refined-scalacheck" % Versions.Refined % Test,

    "org.typelevel" %% "mouse" % Versions.Mouse,
    "com.github.valskalla" %% "odin-core" % Versions.OdinCore,
    "com.github.pureconfig" %% "pureconfig-core" % Versions.PureConfigCore,
    "ch.qos.logback" % "logback-classic" % Versions.Logback,

    "org.scalameta" %% "munit" % Versions.MUnit % Test,
    "org.scalameta" %% "munit-scalacheck" % Versions.MUnit % Test,
    "org.typelevel" %% "munit-cats-effect-3" % Versions.MUnitCatsEffect % Test
  )
}

private object Versions {
  val CatsCore = "2.8.0"
  val CatsEffect = "3.3.14"
  val CirceCore = "0.14.2"
  val Mouse = "1.1.0"
  val Refined = "0.10.1"
  val OdinCore = "0.13.0"
  val SttpCore = "3.7.4"
  val PureConfigCore = "0.17.1"
  val Decline = "2.3.0"
  val Logback = "1.2.11"
  val MUnit = "0.7.29"
  val MUnitCatsEffect = "1.0.7"
}