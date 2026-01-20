plugins {
    alias(libs.plugins.catchdesign.convention.android.data)
}

android {
    namespace = "com.catchdesign.data"

    buildFeatures {
        buildConfig = true
    }


    defaultConfig {
        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://raw.githubusercontent.com\""
        )
    }

    buildTypes {
        release {

        }
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}