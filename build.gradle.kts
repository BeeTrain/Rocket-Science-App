buildscript {
    repositories {
        google()
        mavenLocal()
        maven(Plugins.Url.pluginsGradle)
        jcenter()

    }
    dependencies {
        classpath(Plugins.ClassPaths.gradle)
        classpath(Plugins.ClassPaths.kotlin)
        classpath(Plugins.ClassPaths.googleServices)
        classpath(Plugins.ClassPaths.timeTracker)
        classpath(Plugins.ClassPaths.detekt)
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}