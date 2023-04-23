plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'dagger.hilt.android.plugin'
    id 'io.realm.kotlin'
    id 'com.google.gms.google-services'
    id 'kotlin-kapt'
}

android {
    namespace 'com.xdiach.diarymoodapp'
    compileSdk ProjectConfig.compileSdk

    defaultConfig {
        applicationId "com.xdiach.diarymoodapp"
        minSdk ProjectConfig.minSdk
        targetSdk ProjectConfig.targetSdk
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary true
        }
        multiDexEnabled true
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
        coreLibraryDesugaringEnabled true
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion ProjectConfig.composeExtensionVersion
    }
    packagingOptions {
        resources {
            excludes += '/META-INF/{AL2.0,LGPL2.1}'
        }
    }
}

dependencies {

    // Compose Navigation
    implementation libs.navigation.compose

    // Firebase
    implementation libs.firebase.storage

    // Room components
    implementation libs.room.runtime
    kapt libs.room.compiler
    implementation libs.room.ktx

    // Splash API
    implementation libs.splash.api

    // Mongo DB Realm
    implementation libs.realm.sync

    // Dagger Hilt
    implementation libs.hilt.android
    kapt libs.hilt.compiler

    // Desugar JDK
    coreLibraryDesugaring libs.desugar.jdk

    implementation(project(":core:ui"))
    implementation(project(":core:translations"))
    implementation(project(":core:util"))
    implementation(project(":data:mongo"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:write"))
}