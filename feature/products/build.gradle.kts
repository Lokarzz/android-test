plugins {
    alias(libs.plugins.catchdesign.convention.android.feature)
}

android {
    namespace = "com.catchdesign.products"

}

dependencies {

    implementation(projects.core.domain)
}