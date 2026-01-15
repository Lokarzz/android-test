package com.catchdesign.convention.extensions

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions


internal fun Project.libs(): LibrariesForLibs {
    return extensions.getByType<LibrariesForLibs>()
}

internal fun Project.libraryExtension(): LibraryExtension {
    return extensions.getByType<LibraryExtension>()
}

internal fun Project.baseAppModuleExtension(): BaseAppModuleExtension {
    return extensions.getByType<BaseAppModuleExtension>()
}

internal fun Project.configureKotlinAndroidProjectExtension(action: KotlinAndroidProjectExtension.() -> Unit) {
    return extensions.configure<KotlinAndroidProjectExtension>(action)
}

internal fun Project.kotlinOptions(configure: KotlinJvmCompilerOptions.() -> Unit) {
    configureKotlinAndroidProjectExtension {
        compilerOptions(configure)
    }
}

internal fun Project.alias(plugin: Provider<PluginDependency>) {
    apply(plugin = plugin.get().pluginId)
}





