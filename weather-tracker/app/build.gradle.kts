plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation.safeargs)
}

kotlin {
    sourceSets.all {
        languageSettings.enableLanguageFeature("ExplicitBackingFields")
    }
}

android {
    namespace = "com.example.weathertracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weathertracker"
        minSdk = 32
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "WEATHER_API_KEY", "\"KEY\"")
        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Retrofit + OkHttp
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    
    // DataStore
    implementation(libs.datastore.preferences)
    
    // WorkManager
    implementation(libs.workmanager)
    
    // Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
    
    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    
    // Location
    implementation(libs.play.services.location)

    // SwipeRefreshLayout
    implementation(libs.androidx.swiperefreshlayout)

    // Hilt WorkManager
    implementation(libs.hilt.work)
    ksp(libs.hilt.work.compiler)

    // Play Services Tasks
    implementation(libs.play.services.tasks)

    // JavaPoet (для Hilt)
    implementation(libs.javapoet)
}