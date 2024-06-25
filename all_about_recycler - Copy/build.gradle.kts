// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
}
buildscript {
    repositories {
        mavenCentral()
        google()


            maven { url=uri("https://jitpack.io")  }
    }
    dependencies {
        classpath ("io.realm:realm-gradle-plugin:10.15.1")
    }
}