buildscript {
    dependencies {
        classpath(libs.google.services)
    }

    repositories {
        jcenter()
        maven {
            url=(uri("https://maven.google.com"))
        }
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
}