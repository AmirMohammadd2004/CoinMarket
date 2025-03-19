plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")


}

android {
    viewBinding {
        enable = true
    }
    namespace = "com.amir.coinmarket"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.amir.coinmarket"
        minSdk = 25
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // server retrofit and json =>
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // add spark library for show chart
    implementation(libs.spark)

    // add swipe refresh layout
    implementation(libs.androidx.swiperefreshlayout)


    //glide_for_image
    implementation(libs.github.glide)


}