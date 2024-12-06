plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.boss.class10allguidemanual2081"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.boss.class10allguidemanual2081"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures{
        viewBinding=true;
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")
    implementation (libs.android.pdf.viewer)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
//    implementation("io.github.afreakyelf:Pdf-Viewer:2.1.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    // For Kotlin users also import the Kotlin extensions library for Play In-App Update:
    implementation("com.google.android.play:app-update-ktx:2.1.0")
//    implementation ("com.github.barteksc:AndroidPdfViewer:2.0.2")
//    implementation ("androidx.core:core-splashscreen:1.0.1")

    //rate
    implementation ("com.google.android.play:review:2.0.2")

    // For Kotlin users also add the Kotlin extensions library for Play In-App Review:
    implementation ("com.google.android.play:review-ktx:2.0.2")
    //rate

}
