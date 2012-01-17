The Scala Library Installer Package for Android
===============================================

This is a distribution of the scala-library.jar for Android. It is meant for developers who want to use Scala in their
Android projects but don't want to redex and repackage Scala all the time while developing.

This installer package, instead, comes with the Scala library already pre-dex'd and splitted into Android-digestable hunks and
installs all parts of the library as a well-known Android framework library which you can use like e.g. the Google Maps library
which is pre-installed with Android.

Disclaimer
----------

Beware: The installer will change your system image and execute commands as root. I never managed to brick my phone this
way (which is highly unlikely) but don't say I didn't warn you. You use this program at your own risk.

Installation
------------

Just [download][apk] the apk and install it on your rooted phone and follow the instructions. In the process
the program will try to execute several commands with 'su' so this has to be enabled for your Android distribution.

You have to restart your phone afterwards or at least kill the `servicemanager` process. This will dexopt and cache the
newly added libraries.

Usage
-----

To use the Scala library in one of your projects, add the following lines inside of the `<application>`-tag in the
`AndroidManifest.xml`:

        <uses-library android:name="scala_actors-2.9.1"/>
        <uses-library android:name="scala_collection-2.9.1"/>
        <uses-library android:name="scala_immutable-2.9.1"/>
        <uses-library android:name="scala_library-2.9.1"/>
        <uses-library android:name="scala_mutable-2.9.1"/>

If you use sbt with the [sbt-android plugin][sbt-android] plugin you will now want to disable the ProGuard step. This
will speed up the build time and also prevent the plugin from packaging the Scala library with your application.

In your build.scala file, you'll want something like this (you should already have a Project defined, just edit it)

    lazy val main = Project (
      "My Android App",
      file("."),
      settings = General.fullAndroidSettings ++ Seq(
        useProguard in Android := false
      )
    )

You can re-enable the ProGuard step whenever you're ready to build an APK for publishing.

What this is not
----------------
A way to distribute the Scala library to the users of your app. They will neither have this package installed nor will
they be able to because this works only on rooted phones. Remember to remove the `uses-library` from your AndroidManifest.xml
for production releases.


How does it work
----------------

Well-known libraries are declared in `/system/etc/permissions`. This installer extracts the Scala library onto your phone
and creates [descriptors][desc] in `/system/etc/permissions` which point to the installation location.

Known issues
------------

Unfortunately, this method doesn't work right now on an emulator for several reasons:

  - The emulator image doesn't give su-rights to any app
  - The emulator /system image is basically read-only and is rebuild on every restart of the emulator


Android Market
-------------

You can find this application on the Android Market [here](https://market.android.com/details?id=com.mobilemagic.scalainstaller)


If it doesn't work
------------------

[Please file a bug][issues] or tell me in [github][gh-mail] or at johannes.rudolph@gmail.com.

License
-------

The library is licensed under the FreeBSD License.

Credits
-------

[Stephane Micheloud][micheloud] already did much work on how to prepare an emulator image or a rooted phone to have Scala installed.
The process involved creating dex'd versions of the Scala library, a new image and adapting shell scripts.

@macarse updated the libraries to Scala 2.9.1 and fixed some bugs with the command line tools.

@jbrechtel converted the complete app to Scala and made it ready to be published on the Android market.

  [apk]:         https://github.com/downloads/jrudolph/scala-android-libs/scala-android-libs_2.9.0-1-2.9.0-1.v1.apk
  [desc]:        http://github.com/jrudolph/scala-android-libs/blob/master/src/main/res/raw/scala_collection_desc.xml
  [issues]:      http://github.com/jrudolph/scala-android-libs/issues
  [gh-mail]:     https://github.com/inbox/new/jrudolph
  [sbt-android]: http://github.com/jberkel/android-plugin
  [micheloud]:   http://lamp.epfl.ch/~michelou/android/index.html
