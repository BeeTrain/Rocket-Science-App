package ru.chernakov.rocketscienceapp

object AndroidConfig {
    const val NAME = "RocketScienceApp"
    const val ID = "ru.chernakov.rocketscienceapp"
    const val VERSIONS_FILE_PATH = "../changelog.txt"

    const val GOOGLE_APP_ID = "GOOGLE_APP_ID"
    const val GROUP_QA = "testers"

    const val THE_MOVIEDB_API_KEY = "THE_MOVIEDB_API_KEY"

    const val compileSdk = 28
    const val minSdk = 21
    const val targetSdk = 28
    const val buildTools = "29.0.0"
    const val versionCode = 1
    const val versionName = "1.0"

    const val testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    const val srcMain = "src/main/kotlin"
    const val srcTest = "src/test/kotlin"

    const val isMultiDexEnabled = true
}