plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.boss.kuraakaani"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.boss.kuraakaani"
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
        viewBinding =true
    }
}

dependencies {
    implementation ("de.hdodenhof:circleimageview:3.1.0") /// for circular image view
    implementation ("com.github.bumptech.glide:glide:4.16.0")   // for picture
    implementation ("com.github.pgreze:android-reactions:1.6")  // for reactions
    implementation ("com.github.3llomi:CircularStatusView:V1.0.3")  //for status tab
    implementation ("com.github.OMARIHAMZA:StoryView:1.0.2-alpha") //to view stories
//    implementation ("com.github.sharish:ShimmerRecyclerView:v1.3")//make recycler view look cool while loading

    //for video chat
    implementation("org.jitsi.react:jitsi-meet-sdk:+") {
        isTransitive = true
    }
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    // Add the dependency for the Cloud Functions library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-functions")





    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}