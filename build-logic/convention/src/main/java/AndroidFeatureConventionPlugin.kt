import com.my.version.app.configureComposeAndroid
import com.my.version.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("androidx.navigation.safeargs.kotlin")

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:domain"))

                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }
}