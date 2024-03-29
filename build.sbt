// 0.0.0
// | | |_ bugfixes, small improvements
// | |___ non-api changes
// | ____ api changes
val Octogato = "1.0.11"

val Scala = "3.3.1"
val DockerImage = "eclipse-temurin:19.0.1_10-jre-focal"

lazy val root = project.in(file("."))
  .settings(
    organization := "com.github.programaker",
    name := "Octogato",
    version := Octogato,
    scalaVersion := Scala,
    dockerBaseImage := DockerImage,
    Compile / mainClass := Some("octogato.app.OctogatoCLIApp"),
    libraryDependencies ++= Dependencies.libraries,

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
    )
  )
  .enablePlugins(
    JavaAppPackaging,
    DockerPlugin,
    AshScriptPlugin
  )
