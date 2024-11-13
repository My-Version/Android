import java.util.Properties

plugins {
    id("my.version.plugin.library")
    id("my.version.plugin.compose")
    id("my.version.plugin.hilt")
    id("my.version.plugin.feature")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.my.version.feature.evaluate"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "COVER_STREAM_URL",
            properties.getProperty("cover.stream.url")
        )

        buildConfigField(
            "String",
            "MUSIC_STREAM_URL",
            properties.getProperty("music.stream.url")
        )
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

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.coil)
}