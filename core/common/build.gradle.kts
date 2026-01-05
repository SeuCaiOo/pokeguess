plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "br.com.seucaio.pokeguess.core.common"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    api(libs.androidx.core.ktx)
}
