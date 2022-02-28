val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "PiggCrapp",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "com.github.sbt" % "junit-interface" % "0.13.3" % Test,
      "com.github.nscala-time" %% "nscala-time" % "2.30.0"
    )
  )