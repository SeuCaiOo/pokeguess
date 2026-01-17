plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "br.com.seucaio.pokeguess.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(project(":core:common"))

    // Serialization
    api(libs.kotlinx.serialization.json)

    detektPlugins(libs.detekt.formatting)
}

detekt {
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    toolVersion = libs.versions.detekt.get()
    buildUponDefaultConfig = true
    ignoreFailures = false
}
