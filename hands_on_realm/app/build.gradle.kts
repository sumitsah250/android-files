plugins {
    alias(libs.plugins.androidApplication)
}
apply(plugin ="realm-android")

android {
    namespace = "com.example.hands_on_realm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hands_on_realm"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        viewBinding=true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.2.0")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata:$2.2.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}