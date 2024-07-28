plugins {
    id("my.version.plugin.library")
    id("my.version.plugin.compose")
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.my.version.core.designsystem"

    defaultConfig.consumerProguardFiles("consumer-rules.pro")

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
}