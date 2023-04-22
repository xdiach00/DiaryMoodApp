plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("io.realm.kotlin")
}

android {
    namespace = "com.xdiach.auth"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.extensionVersion
    }
    packagingOptions {
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {

    implementation(libs.activity.compose)
    implementation(libs.material3.compose)
    implementation(libs.navigation.compose)
    implementation(libs.one.tap.compose)
    implementation(libs.message.bar.compose)

    implementation(libs.coroutines.core)
    implementation(libs.realm.sync)

    implementation(libs.firebase.auth)

    implementation(project(":core:translations"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
}