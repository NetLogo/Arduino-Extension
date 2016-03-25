scalaVersion := "2.11.7"

enablePlugins(org.nlogo.build.NetLogoExtension)

javaSource in Compile := baseDirectory.value / "src"

name := "arduino"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-Xlint", "-Xfatal-warnings",
  "-encoding", "us-ascii")

javacOptions ++= Seq("-g", "-deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path",
  "-encoding", "us-ascii")

netLogoZipSources   := false

netLogoClassManager := "arduino.ArduinoExtension"

netLogoTarget :=
  org.nlogo.build.NetLogoExtension.directoryTarget(baseDirectory.value)

netLogoVersion := "6.0.0-M2"
