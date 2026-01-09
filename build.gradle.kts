import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
        reports {
            html.required.set(true)
            xml.required.set(true)
            sarif.required.set(true)
            txt.required.set(false)
        }
    }
}

tasks.register<Delete>("clean") {
    description = "Deletes build directories for the root project and all subprojects."
    // Add the root build directory to the set of deletable files for the clean task.
    delete(layout.buildDirectory)
}