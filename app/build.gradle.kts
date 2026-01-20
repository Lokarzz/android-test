import java.util.Properties

plugins {
    alias(libs.plugins.catchdesign.convention.android.application)
}
val keystoreProperties = Properties()
val keystorePropertiesFile = project.file("keystore.properties")

if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(keystorePropertiesFile.inputStream())
}

fun signingProp(name: String): String? =
    System.getenv(name) ?: keystoreProperties.getProperty(name)

android {
    signingConfigs {
        create("release") {
            val storePath = signingProp("KEYSTORE_PATH")
            if (storePath != null) {
                storeFile = file(storePath)
                storePassword = signingProp("KEYSTORE_PASSWORD")
                keyAlias = signingProp("KEY_ALIAS")
                keyPassword = signingProp("KEY_PASSWORD")
            }
        }
    }

    namespace = "com.catchdesign.androidtest"

    defaultConfig {
        applicationId = "com.catchdesign.androidtest"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")

        }
        debug {
            applicationIdSuffix = ".debug"
            isShrinkResources = false
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

}

dependencies {
    implementation(projects.feature.products)
    implementation(projects.feature.productdetails)
}