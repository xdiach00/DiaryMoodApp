plugins {
    id(Plugins.Android.Library.id)
    id(Plugins.Jetbrains.Kotlin.Android.id)
    id(Plugins.Realm.id)
    id(Plugins.Kotlin.Kapt.id)
}

android {
    namespace = "com.xdiach.write"
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

    implementation(libs.coil)

    implementation(libs.accompanist.pager)

    implementation(libs.coroutines.core)
    implementation(libs.realm.sync)

    implementation(libs.date.time.picker)
    implementation(libs.date.dialog)
    implementation(libs.time.dialog)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)

    implementation(libs.junit)
    implementation(libs.junit.ext)

    implementation(project(Modules.Core.translations))
    implementation(project(Modules.Core.ui))
    implementation(project(Modules.Core.util))
    implementation(project(Modules.Data.mongo))
}
