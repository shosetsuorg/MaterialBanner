plugins {
	id("com.android.library")
	kotlin("android")
	//`maven-publish`
}

group = "app.shosetsu.lib.material-banner"
version = "2.0.5"


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
		create("release") {
			minifyEnabled(false)
			consumerProguardFiles("proguard-rules.pro")
		}
	}
}
/*
afterEvaluate {
	publishing {
		publications {
			// Creates a Maven publication called "release".

			create<MavenPublication>("release") {
				// Applies the component for the release build variant.
				from(components.getByName("release"))
			}
		}

	}
}

 */

dependencies {
	implementation("androidx.core:core:1.6.0-rc01")
	implementation("com.google.android.material:material:1.4.0-rc01")
}

apply("../gradle-release.gradle")