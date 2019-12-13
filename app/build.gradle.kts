plugins {
    id(Plugins.application)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinAndroidExtensions)
    id(Plugins.kotlinKapt)
    id(Plugins.timeTracker)
    id(Plugins.detekt)
}

apply {
     from("../buildSrc/quality/ktlint.gradle")
     from("../buildSrc/quality/checkstyle.gradle")
     from("../buildSrc/quality/jacoco.gradle")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)

    defaultConfig {
        applicationId = AndroidConfig.ID

        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)
        buildToolsVersion(AndroidConfig.buildTools)

        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        multiDexEnabled = AndroidConfig.isMultiDexEnabled

        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }

    sourceSets {
        getByName("main").java.srcDirs(AndroidConfig.srcMain)
        getByName("test").java.srcDirs(AndroidConfig.srcTest)
    }
}

dependencies {
    implementation(Libraries.Kotlin.stdLib)
    implementation(Libraries.Kotlin.coroutinesCore)
    implementation(Libraries.Kotlin.coroutinesAndroid)
    implementation(Libraries.Kotlin.coroutinesTest)

    implementation(Libraries.AndroidX.coreKtx)

    implementation(Libraries.Support.appCompat)
    implementation(Libraries.Support.material)
    implementation(Libraries.Support.constraint)

    implementation(Libraries.ViewModel.extensions)

    implementation(Libraries.Retrofit.okhttp)
    implementation(Libraries.Retrofit.logging)
    implementation(Libraries.Retrofit.retrofit)
    implementation(Libraries.Retrofit.gsonConverter)

    implementation(Libraries.Firebase.auth)
    implementation(Libraries.playServicesAuth)

    implementation(Libraries.ViewModel.extensions)

    implementation(Libraries.Koin.koin)
    implementation(Libraries.Koin.viewModel)

    implementation(Libraries.picasso)

    implementation(Libraries.timber)

    debugImplementation(Libraries.LeakCanary.canaryAndroid)

    testImplementation(Libraries.Tests.junit)
    androidTestImplementation(Libraries.Tests.mockito)
}

detekt {
    toolVersion = Versions.detekt
    input = files("src/main/kotlin")
    config = files("${projectDir}/../buildSrc/quality/detekt.yml")
    reports {
        xml {
            enabled = true
            destination = file("$projectDir/build/reports/detekt/detekt-report.xml")
        }
        html {
            enabled = true
            destination = file("$projectDir/build/reports/detekt/detekt-report.html")
        }

    }
}

buildtimetracker {
    reporters {
        register("csv") {
            options["output"] = "build/times.csv"
            options["append"] = "true"
            options["header"] = "false"
        }

        register("summary") {
            options["ordered"] = "true"
            options["threshold"] = "50"
            options["barstyle"] = "none"
        }

        register("csvSummary") {
            options["csv"] = "build/times.csv"
        }
    }
}

tasks.register("checkBeforePush") {
    group = "verification"
    description = "Inspect your code before push"
    dependsOn("checkstyle", "ktlint", "lintDebug")
}

apply(plugin = Plugins.googleServices)