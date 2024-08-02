import com.android.build.api.dsl.ApplicationExtension
import com.my.version.app.configureKotlinAndroid
import com.my.version.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            configureKotlinAndroid()

            dependencies {
                add("implementation", libs.findLibrary("material").get())
                add("testImplementation", libs.findLibrary("junit").get())
            }
        }
    }
}