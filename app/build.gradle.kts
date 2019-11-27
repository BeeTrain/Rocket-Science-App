plugins {
    id(Plugins.application)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinAndroidExtensions)
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
apply(plugin = Plugins.googleServices)