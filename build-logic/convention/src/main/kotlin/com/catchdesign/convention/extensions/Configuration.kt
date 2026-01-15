package com.catchdesign.convention.extensions

import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.DefaultConfig
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

internal fun DefaultConfig.applyDefaultConfiguration(libs: LibrariesForLibs) {
    minSdk = libs.versions.minSdk.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

internal fun CompileOptions.applyCompileOptions(libs: LibrariesForLibs) {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
}

internal fun KotlinJvmCompilerOptions.applyKotlinJvmCompilerOptions(libs: LibrariesForLibs) {
    jvmTarget.set(JvmTarget.fromTarget(libs.versions.javaVersion.get()))
}
