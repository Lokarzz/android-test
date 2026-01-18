plugins {
    alias(libs.plugins.catchdesign.convention.android.application)
}

android {
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            isShrinkResources = false
            isMinifyEnabled = false
        }
    }

}

dependencies {
    implementation(projects.feature.products)
    implementation(projects.feature.productdetails)
}