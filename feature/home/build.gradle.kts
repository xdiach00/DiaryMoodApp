plugins {
    id(Plugins.Android.Library.id)
    id(Plugins.Jetbrains.Kotlin.Android.id)
    id(Plugins.DaggerHilt.Plugin.id)
    id(Plugins.Kotlin.Kapt.id)
    id(Plugins.Realm.id)
}

android {
    namespace = "com.xdiach.home"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

    implementation(libs.activity.compose)
    implementation(libs.material3.compose)
    implementation(libs.navigation.compose)
    implementation(libs.compose.tooling.preview)

    implementation(libs.date.time.picker)
    implementation(libs.date.dialog)

    implementation(libs.coroutines.core)
    implementation(libs.realm.sync)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)

    implementation(project(Modules.Core.translations))
    implementation(project(Modules.Core.ui))
    implementation(project(Modules.Core.util))
    implementation(project(Modules.Data.mongo))
}
