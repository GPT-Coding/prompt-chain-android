@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.ktlint.plugin)
    kotlin("android")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.buildToolsVersion.get()

    defaultConfig {
        applicationId = "com.prompts"
        namespace = "com.prompts"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        named("debug") {
            isTestCoverageEnabled = true
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        // Note: can only be enabled through compiler arguments [KT-37652](https://youtrack.jetbrains.com/issue/KT-37652)
        freeCompilerArgs = freeCompilerArgs
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ktlint {
    android.set(true)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.android.material)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.compose.activity)
    implementation(libs.bundles.jetpack.compose)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.constraint.layout)
    implementation(libs.okhttp3)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.dagger)
    implementation(libs.google.gson)
    implementation(libs.compose.coil)
    implementation(libs.compose.swiperefresh)
    implementation(libs.handy.uri.template)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    kapt(libs.dagger.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.ext.junit)
    testImplementation(libs.core.testing)
    testImplementation(libs.mockk)
    testImplementation(libs.compose.test)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
