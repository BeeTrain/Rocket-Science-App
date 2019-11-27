buildscript {
    repositories {
        google()
        mavenLocal()
        jcenter()

    }
    dependencies {
        classpath(Plugins.ClassPaths.gradle)
        classpath(Plugins.ClassPaths.kotlin)
        classpath(Plugins.ClassPaths.googleServices)
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