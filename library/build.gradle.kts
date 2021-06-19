plugins {
	id("com.android.library")
	kotlin("android")
}

android {
	compileSdkVersion(30)
	buildToolsVersion = "30.0.3"
	defaultConfig {
		minSdkVersion(14)
		targetSdkVersion(30)
		versionCode = 3
		versionName = "1.2.0"

		resValue("string", "materialbanner_lib_version", "${versionName}")
	}
	buildTypes {
		named("release") {
			minifyEnabled(false)
			consumerProguardFiles("proguard-rules.pro")
		}
	}
}

dependencies {
	implementation("androidx.core:core:1.6.0-rc01")
	implementation("com.google.android.material:material:1.4.0-rc01")
}

apply("../gradle-release.gradle")