import org.gradle.kotlin.dsl.commonMainImplementation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

private val namespace = "com.example.weathertracker.shared"

room {
    schemaDirectory("$projectDir/schemas")
}

compose.resources {
    packageOfResClass = "$namespace.resources"
    publicResClass = false
    generateResClass = auto
}

kotlin {
    androidLibrary {
        namespace = namespace
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

dependencies {
    ksp(libs.room.compiler)

    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.foundation)
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.precompose)

    commonMainImplementation(libs.kotlinx.collectionsImmutable)
    commonMainImplementation(libs.kotlinx.serialization)
    commonMainImplementation(libs.kotlinx.coroutines)
    commonMainImplementation(libs.kotlinx.datetime)

    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.koin.compose)
    commonMainImplementation(libs.koin.compose.viewModel)

    commonMainImplementation(libs.room.runtime)
    commonMainImplementation(libs.sqlite.bundled)
    commonMainImplementation(libs.datastore.preferences)

    commonMainImplementation(libs.ktor)
    commonMainImplementation(libs.ktor.logging)
    commonMainImplementation(libs.ktor.contentNegotiation)
    commonMainImplementation(libs.ktor.kotlinxSerialization)
}
