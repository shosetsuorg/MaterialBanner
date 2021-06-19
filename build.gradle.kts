buildscript {
	val kotlinVersion: String by extra("1.5.10")

	repositories {
		google()
		mavenCentral()
		maven( "https://maven.google.com")
	}
	dependencies {
		classpath("com.android.tools.build:gradle:4.2.1")
		classpath(kotlin("gradle-plugin", version = kotlinVersion))
	}
}

allprojects {
	repositories {
		google()
		mavenCentral()
		maven("https://jitpack.io")
	}
}

task("clean", Delete::class) {
	delete(rootProject.buildDir)
}
