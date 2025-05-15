import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "arduino"
version := "3.0.1"
isSnapshot := true

scalaVersion := "3.7.0"
scalacOptions ++= Seq("-deprecation", "-unchecked", "-Xfatal-warnings", "-encoding", "us-ascii", "-release", "11")

javacOptions ++= Seq("-g", "-deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path", "-encoding", "us-ascii", "--release", "11")

netLogoVersion := "7.0.0-beta1-c8d671e"
netLogoClassManager := "arduino.ArduinoExtension"
netLogoPackageExtras += (baseDirectory.value / "lib" / "jssc-2.6.0.jar" -> None)
