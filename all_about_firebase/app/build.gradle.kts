import java.util.regex.Pattern.compile

plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.all_about_firebase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.all_about_firebase"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.ml.vision)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //
    implementation ("com.google.mlkit:face-detection:16.1.6")
    implementation ("com.google.android.gms:play-services-mlkit-face-detection:17.1.0")
    compile("com.github.sujithkanna:smileyrating:2.0.0")
    implementation("com.github.sujithkanna:smileyrating:2.0.0")
    implementation("org.greenrobot:eventbus:3.3.1")
    ///

}