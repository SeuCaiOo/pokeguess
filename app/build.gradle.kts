import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

val keystorePropertiesFile: File? = rootProject.file("signing.properties")
val keystoreProperties = Properties()
keystorePropertiesFile?.let { keystoreProperties.load(FileInputStream(keystorePropertiesFile)) }

fun getVersionName(): String {
    return System.getenv("VERSION_NAME") ?: "1.0.0-SNAPSHOT"
}

fun getVersionCode(): Int {
    val versionName = getVersionName()
    return System.getenv("VERSION_CODE")?.toIntOrNull()
        ?: versionName.replace(".", "").replace("-SNAPSHOT", "").toIntOrNull()
        ?: 1
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "br.com.seucaio.pokeguess"
    compileSdk = 36

    defaultConfig {
        applicationId = "br.com.seucaio.pokeguess"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val envStoreFile = System.getenv("STORE_FILE_PATH")
            val envStorePassword = System.getenv("STORE_PASSWORD")
            val envKeyAlias = System.getenv("KEY_ALIAS")
            val envKeyPassword = System.getenv("KEY_PASSWORD")

            storeFile = file(envStoreFile ?: keystoreProperties.getProperty("storeFilePath"))
            storePassword = envStorePassword ?: keystoreProperties.getProperty("storePassword")
            keyAlias = envKeyAlias ?: keystoreProperties.getProperty("keyAlias")
            keyPassword = envKeyPassword ?: keystoreProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            resValue("string", "app_name", "PokeGuess Dev")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            resValue("string", "app_name", "PokeGuess")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        applicationVariants.all {
            val variant = this
            val varianteVersionName = variant.versionName
            variant.outputs.forEach { output ->
                val name = "pokeguess-v$varianteVersionName.apk"
                (output as ApkVariantOutputImpl).outputFileName = name
            }
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:design-system"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Koin
    implementation(libs.koin.androidx.compose)

    // Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    detektPlugins(libs.bundles.detekt)
}

detekt {
    config.setFrom(
        files(
            "$rootDir/config/detekt/detekt.yml",
            "$rootDir/config/detekt/detekt-compose.yml"
        )
    )
    toolVersion = libs.versions.detekt.get()
    buildUponDefaultConfig = true
    ignoreFailures = false
    parallel = true
    autoCorrect = false
}

