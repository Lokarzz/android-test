package com.catchdesign.convention.extensions

import com.android.build.gradle.LibraryExtension
import com.catchdesign.convention.extensions.applyCompileOptions
import com.catchdesign.convention.extensions.applyDefaultConfiguration
import org.gradle.accessors.dm.LibrariesForLibs

internal fun LibraryExtension.applyCompileOptions(libs: LibrariesForLibs) {
    compileOptions {
        applyCompileOptions(libs)
    }
}

internal fun LibraryExtension.applyDefaultConfiguration(libs: LibrariesForLibs) {
    defaultConfig {
        applyDefaultConfiguration(libs)
    }
}





