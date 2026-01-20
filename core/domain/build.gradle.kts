plugins {
    alias(libs.plugins.catchdesign.convention.android.domain)
}

android {
    namespace = "com.catchdesign.domain"

}

dependencies {
    implementation(projects.core.data)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}