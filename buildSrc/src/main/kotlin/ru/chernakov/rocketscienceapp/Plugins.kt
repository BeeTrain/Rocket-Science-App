package ru.chernakov.rocketscienceapp

@Suppress("MaxLineLength")
object Plugins {
    object ClassPaths {
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val timeTracker = "net.rdrei.android.buildtimetracker:gradle-plugin:${Versions.timeTracker}"
        const val jacoco = "com.vanniktech:gradle-android-junit-jacoco-plugin:${Versions.jacoco}"
        const val fabric = "io.fabric.tools:gradle:${Versions.fabric}"
        const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsPlugin}"
        const val firebaseAppDistribution = "com.google.firebase:firebase-appdistribution-gradle:${Versions.firebaseAppDistribution}"
        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
        const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        const val dependenciesVersions = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersions}"
    }

    const val application = "com.android.application"
    const val library = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val firebaseAppDistribution = "com.google.firebase.appdistribution"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"
    const val googleServices = "com.google.gms.google-services"
    const val timeTracker = "build-time-tracker"
    const val fabric = "io.fabric"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val jacoco = "jacoco"
    const val checkstyle = "checkstyle"
    const val dependenciesVersions = "com.github.ben-manes.versions"
    const val navigationSafeArgsKotlin = "androidx.navigation.safeargs.kotlin"
}