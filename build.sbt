import Dependencies._

val Octogato = "0.1.0"
val Scala = "3.1.0"

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

      Refined,
      Mouse,
      OdinCore,
      PureconfigCore
    ),

    // https://docs.scala-lang.org/scala3/guides/migration/options-lookup.html
    // https://docs.scala-lang.org/scala3/guides/migration/options-new.html
    scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-explain",

      "-language:strictEquality",

      "-Yexplicit-nulls",
      "-Ykind-projector:underscores"
    )
  )
