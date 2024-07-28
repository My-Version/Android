plugins {
    id("my.version.plugin.library")
    id("my.version.plugin.compose")
    id("my.version.plugin.hilt")
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.my.version.feature.main"

}

dependencies {
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:cover"))
    implementation(project(":feature:evaluate"))
    implementation(project(":core:designsystem"))


    implementation(libs.timber)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
}
