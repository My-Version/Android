import com.my.version.app.configureComposeAndroid
import com.my.version.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:domain"))

                add("implementation", libs.findLibrary("timber").get())
            }
        }
    }
}