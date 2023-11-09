// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    kotlin("plugin.serialization") version "1.9.0"
}
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//apply(from = "/app/version_control.gradle.kts")
