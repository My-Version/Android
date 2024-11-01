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
    namespace = "com.my.version.feature.main"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                properties.getProperty("base.url")
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
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:cover"))
    implementation(project(":feature:evaluate"))

    implementation(libs.google.accompanist)
}
