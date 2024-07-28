pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyVersion"
include(":app")
include(":feature:auth")
include(":feature:home")
include(":feature:cover")
include(":feature:evaluate")
include(":feature:main")
include(":core:designsystem")
include(":core:domain")
include(":core:data")
include(":core:common")
include(":core:network")
