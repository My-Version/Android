plugins {
    id("my.version.plugin.application")
    id("my.version.plugin.compose")
    id("my.version.plugin.hilt")
}

android {
    namespace = "com.my.version"

    defaultConfig {
        applicationId = "com.my.version"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:main"))
    implementation(libs.timber)
}