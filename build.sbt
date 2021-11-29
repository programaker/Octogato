import Dependencies._

val Octogato = "0.1.0"
val Scala = "3.1.0"
val DockerImage = "bellsoft/liberica-openjre-alpine:17.0.1"

lazy val root = project.in(file("."))
  .settings(
    organization := "com.github.programaker",
    name := "Octogato",
    //
    // 0.0.0
    // | | |_ bugfixes, small improvements
    // | |___ non-api changes
    // | ____ api changes
    version := Octogato,
    scalaVersion := Scala,

    libraryDependencies ++= Seq(
      CatsCore,
      CatsEffect,

      CirceCore,
      CirceGeneric,
      CirceParser,
      CirceRefined,

      SttpCore,
      SttpCatsBackend,
      SttpCirce,

      Decline,
      DeclineEffect,
      DeclineRefined,

      Refined,
      Mouse,
      OdinCore,
      PureconfigCore
    ),

    // https://docs.scala-lang.org/scala3/guides/migration/options-lookup.html
    // https://docs.scala-lang.org/scala3/guides/migration/options-new.html
    //
    // Not using "-Yexplicit-nulls" anymore because it's causing more harm than good.
    // It breaks circe codecs and working with Java stuff becomes painful.
    scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-language:strictEquality",
      "-Ykind-projector:underscores"
    ),

    Compile / mainClass := Some("octogato.app.OctogatoCLIApp"),
    dockerBaseImage := DockerImage
  )
  .enablePlugins(
    JavaAppPackaging,
    DockerPlugin,
    AshScriptPlugin
  )
