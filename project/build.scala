import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Scala Installer",
    version := "0.1",
    scalaVersion := "2.9.1",
    platformName in Android := "android-8",
    javacOptions in compile ++= Seq("-target", "1.6", "-source", "1.6")
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "ScalaInstaller",
    file("."),
    settings = General.fullAndroidSettings
  )
}
