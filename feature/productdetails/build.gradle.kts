plugins {
    alias(libs.plugins.catchdesign.convention.android.feature)
}

android {
    namespace = "com.catchdesign.productdetails"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
}
