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
    "com.softwaremill.sttp.client3" %% "armeria-backend-cats" % Versions.SttpCore,
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
  val CatsCore = "2.9.0"
  val CatsEffect = "3.4.0"
  val CirceCore = "0.14.3"
  val Mouse = "1.2.1"
  val Refined = "0.10.1"
  val OdinCore = "0.13.0"
  val SttpCore = "3.8.3"
  val PureConfigCore = "0.17.2"
  val Decline = "2.3.1"
  val Logback = "1.4.4"
  val MUnit = "1.0.0-M6"
  val MUnitCatsEffect = "1.0.7"
}