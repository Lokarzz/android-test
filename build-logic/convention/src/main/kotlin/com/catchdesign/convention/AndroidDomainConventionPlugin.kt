package com.catchdesign.convention


import com.android.build.gradle.LibraryExtension
import com.catchdesign.convention.extensions.alias
import com.catchdesign.convention.extensions.applyCompileOptions
import com.catchdesign.convention.extensions.applyDefaultConfiguration
import com.catchdesign.convention.extensions.applyKotlinJvmCompilerOptions
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

class AndroidDomainConventionPlugin : Plugin<Project> {
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
    }

    private fun DependencyHandlerScope.applyDependencies(libs: LibrariesForLibs) {
        implementation(libs.kotlinx.coroutines.android)
        testImplementation(libs.junit)
    }
    private fun Project.android(block: LibraryExtension.() -> Unit) {
        libraryExtension().apply(block)
    }


}