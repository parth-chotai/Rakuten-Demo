import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

val secretsProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "secrets.properties")))
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = RakutenApp.versionCode
        versionName = RakutenApp.versionName

        testInstrumentationRunner = Android.testInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "BASE_URL", "\"" + secretsProperties["BASE_URL"] + "\"")
        buildConfigField("String", "API_KEY", "\"" + secretsProperties["API_KEY"] + "\"")
    }

    buildTypes {
        getByName("release") {
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

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleRuntimeKtx)
    implementation(AndroidX.constraintLayout)
    testImplementation(Junit.junit4)
    androidTestImplementation(Junit.junitExt)

    // Coil
    implementation(Coil.coil)
    implementation(Coil.coilGif)

    implementation(Google.googleMaterial)

    // Dagger - Hilt
    implementation(DaggerHilt.daggerHilt)
    kapt(DaggerHilt.kaptDaggerHiltCompiler)
    kapt(DaggerHilt.kaptHiltCompiler)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitGsonConverter)
}