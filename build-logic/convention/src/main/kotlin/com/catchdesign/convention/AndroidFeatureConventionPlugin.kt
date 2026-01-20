package com.catchdesign.convention


import com.android.build.gradle.LibraryExtension
import com.catchdesign.convention.extensions.alias
import com.catchdesign.convention.extensions.applyCompileOptions
import com.catchdesign.convention.extensions.applyDefaultConfiguration
import com.catchdesign.convention.extensions.applyKotlinJvmCompilerOptions
import com.catchdesign.convention.extensions.debugImplementation
import com.catchdesign.convention.extensions.implementation
import com.catchdesign.convention.extensions.kotlinOptions
import com.catchdesign.convention.extensions.libraryExtension
import com.catchdesign.convention.extensions.libs
import com.catchdesign.convention.extensions.testImplementation
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val libs = libs()
            applyPlugins(libs)

            android {
                compileSdk = libs.versions.compileSdk.get().toInt()
                applyCompileOptions(libs)
                applyDefaultConfiguration(libs)

                kotlinOptions {
                    applyKotlinJvmCompilerOptions(libs)
                }

            }
            dependencies {
                applyDependencies(libs)
            }
        }
    }


    private fun Project.applyPlugins(libs: LibrariesForLibs) {
        alias(libs.plugins.android.library)
        alias(libs.plugins.kotlin.android)
        alias(libs.plugins.kotlin.compose)
        alias(libs.plugins.kotlin.serialization)
        alias(libs.plugins.catchdesign.convention.android.hilt)
    }

    private fun DependencyHandlerScope.applyDependencies(libs: LibrariesForLibs) {
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation(libs.kotlinx.coroutines.android)

        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.compose.material.icons.core)

        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)

        testImplementation(libs.junit)
        testImplementation(libs.kotlinx.coroutines.test)

    }

    private fun Project.android(block: LibraryExtension.() -> Unit) {
        libraryExtension().apply(block)
    }


}