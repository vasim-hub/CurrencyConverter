import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream
import java.util.*


@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.kapt)
}

val properties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "config.properties")))
}

android {
    namespace = "com.currencyconverter.core.remote"
    compileSdk = libs.versions.compileSDK.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        aidl = false
        buildConfig = true
        renderScript = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildTypes {
        getByName("debug") {
            // Configure release build type (if needed)
            buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL_FOR_PROD"))
            buildConfigField(
                "String", "OPEN_EXCHANGE_API_KEY",
                properties.getProperty("OPEN_EXCHANGE_API_KEY_PROD")
            )
        }

        getByName("release") {
            // Configure debug build type (if needed)
            buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL_FOR_DEV"))
            buildConfigField(
                "String", "OPEN_EXCHANGE_API_KEY",
                properties.getProperty("OPEN_EXCHANGE_API_KEY_DEV")
            )
        }
    }
}

dependencies {
    implementation(project(":core-shared"))
    testImplementation(project(":core-testing"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.kotlin.codegen)
    implementation(libs.moshi.converter)

    // testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.okhttp.mockwebserver)
}

