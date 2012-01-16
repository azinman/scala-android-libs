import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "scala-android-libs",
    version := "0.1",
    scalaVersion := "2.9.0-1",
    platformName in Android := "android-10"
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      useProguard in Android := false
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "scala-android-libs",
    file("."),
    settings = General.fullAndroidSettings
  )
}
