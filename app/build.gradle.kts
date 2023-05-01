plugins {
    id(Plugins.Android.Application.id)
    id(Plugins.Jetbrains.Kotlin.Android.id)
    id(Plugins.DaggerHilt.Plugin.id)
    id(Plugins.Realm.id)
    id(Plugins.Google.Gms.GoogleServices.id)
    id(Plugins.Kotlin.Kapt.id)
}

android {
    namespace = "com.xdiach.diarymoodapp"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.composeExtensionVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Compose Navigation
    implementation(libs.navigation.compose)

    // Firebase
    implementation(libs.firebase.storage)

    // Room components
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Splash API
    implementation(libs.splash.api)

    // Mongo DB Realm
    implementation(libs.realm.sync)

    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Desugar JDK
    coreLibraryDesugaring(libs.desugar.jdk)

    // Testing
    implementation(libs.junit)
    implementation(libs.junit.ext)

    implementation(project(Modules.Core.ui))
    implementation(project(Modules.Core.translations))
    implementation(project(Modules.Core.util))
    implementation(project(Modules.Data.mongo))
    implementation(project(Modules.Feature.auth))
    implementation(project(Modules.Feature.home))
    implementation(project(Modules.Feature.write))
}
