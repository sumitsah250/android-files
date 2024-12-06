buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath ("com.google.gms:google-services:4.3.3")
    }

    repositories {
        google()
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