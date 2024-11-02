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
    namespace = "com.my.version.feature.cover"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "STREAM_URL",
                properties.getProperty("s3.url")
            )

            buildConfigField(
                "String",
                "COVER_STREAM_URL",
                properties.getProperty("cover.stream.url")
            )

            buildConfigField(
                "String",
                "DOWNLOAD_HOST",
                properties.getProperty("download.host")
            )
        }
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

}