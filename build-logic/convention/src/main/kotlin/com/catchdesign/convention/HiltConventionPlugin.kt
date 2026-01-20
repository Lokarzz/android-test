package com.catchdesign.convention


import com.catchdesign.convention.extensions.alias
import com.catchdesign.convention.extensions.implementation
import com.catchdesign.convention.extensions.ksp
import com.catchdesign.convention.extensions.libs
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val libs = libs()
            applyPlugins(libs)

            dependencies {
                applyDependencies(libs)
            }
        }
    }

    private fun Project.applyPlugins(libs: LibrariesForLibs) {
        alias(plugin = libs.plugins.devtools.ksp)
        alias(plugin = libs.plugins.hilt.android)
    }

    private fun DependencyHandlerScope.applyDependencies(libs: LibrariesForLibs) {
        implementation(libs.hilt.android)
        implementation(libs.androidx.hilt.navigation.compose)
        ksp(libs.hilt.android.compiler)
  }
}