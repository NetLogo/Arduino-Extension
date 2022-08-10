import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "arduino"
version := "3.0.1"
isSnapshot := true

scalaVersion := "2.12.12"
scalacOptions ++= Seq("-deprecation", "-unchecked", "-Xlint", "-Xfatal-warnings", "-encoding", "us-ascii", "-release", "11")

javacOptions ++= Seq("-g", "-deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path", "-encoding", "us-ascii", "--release", "11")

netLogoVersion := "6.2.2"
netLogoClassManager := "arduino.ArduinoExtension"
netLogoPackageExtras += (baseDirectory.value / "lib" / "jssc-2.6.0.jar" -> None)
