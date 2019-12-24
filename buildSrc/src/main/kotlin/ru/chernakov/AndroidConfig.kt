package ru.chernakov

object AndroidConfig {
    const val NAME = "RocketScienceApp"
    const val ID = "ru.chernakov.rocketscienceapp"

    const val compileSdk = 28

    const val minSdk = 21
    const val targetSdk = 28
    const val buildTools = "29.0.0"
    const val versionCode = 1
    const val versionName = "1.0"

    const val testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    const val srcMain ="src/main/kotlin"
    const val srcTest = "src/test/kotlin"

    const val isMultiDexEnabled = true
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
}
