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

      Mouse,
      Refined,
      OdinCore
    ),

    // https://scalacenter.github.io/scala-3-migration-guide/docs/compiler-options/compiler-options-table.html
    // https://scalacenter.github.io/scala-3-migration-guide/docs/compiler-options/new-compiler-options.html
    scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-deprecation",
      "-explain",

      "-language:strictEquality",

      "-Yexplicit-nulls",
      "-Ykind-projector:underscores"
    )
  )
