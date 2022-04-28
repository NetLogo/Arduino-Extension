enablePlugins(org.nlogo.build.NetLogoExtension, org.nlogo.build.ExtensionDocumentationPlugin)

name := "arduino"
version := "3.0.1"
isSnapshot := true

scalaVersion := "2.12.12"
scalacOptions ++= Seq("-deprecation", "-unchecked", "-Xlint", "-Xfatal-warnings", "-encoding", "us-ascii")

javacOptions  ++= Seq("-g", "-deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path", "-encoding", "us-ascii")

netLogoVersion := "6.2.2"
netLogoZipSources   := false
netLogoClassManager := "arduino.ArduinoExtension"
