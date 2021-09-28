val scalaVer = "3.0.2"

val zioVersion = "2.0.0-M3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "uol",
    version := "0.1.0",

    scalaVersion := scalaVer,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-test" % zioVersion % "test",
      "dev.zio" %% "zio-test-sbt" % zioVersion % "test",
    )
  )
