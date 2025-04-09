plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

dependencies {
    ksp(libs.room.compiler)
    ksp(libs.koin.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

compose.resources {
    packageOfResClass = "com.example.weathertracker.shared.resources"
    publicResClass = false
    generateResClass = auto
}

kotlin {

    androidLibrary {
        namespace = "com.example.weathertracker.shared"
        compileSdk = 35
        minSdk = 32
    }

    val xcfName = "sharedKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(libs.precompose)

                implementation(libs.kotlinx.collectionsImmutable)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.datetime)

                implementation(libs.koin.core)
                api(libs.koin.annotations)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewModel)

                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.datastore.preferences)

                implementation(libs.ktor)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.contentNegotiation)
                implementation(libs.ktor.kotlinxSerialization)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.engine.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.engine.darwin)
            }
        }
    }
}
