plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"
    defaultConfig {
        applicationId = "com.sergivonavi.materialbanner.app"
        minSdkVersion(14)
        targetSdkVersion(30)
        versionCode = 3
        versionName = "1.1.1"
        vectorDrawables.useSupportLibrary = true

        resValue("string", "materialbanner_app_version", "${versionName}")
    }
    buildTypes {
        named("release") {
            minifyEnabled( false)
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    lintOptions {
   //     abortOnError(false)
    }
}

dependencies {
    implementation(project(":library"))
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.google.android.material:material:1.4.0-rc01")
    implementation("com.squareup.picasso:picasso:2.71828")
}
