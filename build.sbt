scalaVersion := "2.11.7"

enablePlugins(org.nlogo.build.NetLogoExtension)

javaSource in Compile := baseDirectory.value / "src"

name := "arduino"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-Xlint", "-Xfatal-warnings",
  "-encoding", "us-ascii")

javacOptions ++= Seq("-g", "-deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path",
  "-encoding", "us-ascii")

netLogoZipSources   := false

val netLogoJarOrDependency =
  Option(System.getProperty("netlogo.jar.url"))
    .orElse(Some("http://ccl.northwestern.edu/devel/NetLogo-5.3-LevelSpace-3a6b9b4.jar"))
    .map { url =>
      import java.io.File
      import java.net.URI
      if (url.startsWith("file:"))
        (Seq(new File(new URI(url))), Seq())
      else
        (Seq(), Seq("org.nlogo" % "NetLogo" % "5.3.0-SNAPSHOT" from url))
    }.get

unmanagedJars in Compile ++= netLogoJarOrDependency._1

libraryDependencies      ++= netLogoJarOrDependency._2

netLogoClassManager := "arduino.ArduinoExtension"

netLogoTarget :=
  org.nlogo.build.NetLogoExtension.directoryTarget(baseDirectory.value)
