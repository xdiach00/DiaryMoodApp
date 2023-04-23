plugins {
    id(Plugins.Android.Library.id)
    id(Plugins.Jetbrains.Kotlin.Android.id)
}

android {
    namespace = "com.xdiach.ui"
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
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {

    implementation(libs.activity.compose)
    implementation(libs.material3.compose)
    implementation(libs.compose.tooling.preview)

    implementation(libs.realm.sync)
    implementation(libs.coroutines.core)

    implementation(libs.junit)
    implementation(libs.junit.ext)

    implementation(project(Modules.Core.translations))
}
