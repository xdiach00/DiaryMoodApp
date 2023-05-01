plugins {
    id(Plugins.Android.Library.id)
    id(Plugins.Jetbrains.Kotlin.Android.id)
    id(Plugins.Realm.id)
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
    implementation(libs.one.tap.compose)
    implementation(libs.message.bar.compose)

    implementation(libs.coroutines.core)
    implementation(libs.realm.sync)

    implementation(libs.firebase.auth)

    implementation(libs.junit)
    implementation(libs.junit.ext)

    implementation(project(Modules.Core.translations))
    implementation(project(Modules.Core.ui))
    implementation(project(Modules.Core.util))
}
