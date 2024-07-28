plugins {
    id("my.version.plugin.library")
    id("my.version.plugin.compose")
    id("my.version.plugin.hilt")
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "feature.auth"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.timber)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
}