plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("AndroidApplicationPlugin") {
            id = "my.version.plugin.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("AndroidApplicationComposePlugin") {
            id = "my.version.plugin.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("AndroidHiltPlugin") {
            id = "my.version.plugin.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("AndroidLibraryPlugin") {
            id = "my.version.plugin.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("AndroidFeaturePlugin") {
            id = "my.version.plugin.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}