// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.ktlint.plugin) apply false
    alias(libs.plugins.arturbosch.detekt) apply false
    kotlin("android") version libs.versions.kotlin apply false
}

buildscript {
    dependencies {
        classpath(libs.tools.build.gradle)
        classpath(libs.jacoco.core)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.ktlint.gradle)
    }
}

allprojects {
    group = "com.prompt"

    apply(plugin = rootProject.libs.plugins.ktlint.plugin.get().pluginId)
    apply(plugin = rootProject.libs.plugins.arturbosch.detekt.get().pluginId)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(true)
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
        jvmTarget = "1.8"
    }
}
