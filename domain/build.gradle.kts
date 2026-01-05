plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
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

dependencies {}
