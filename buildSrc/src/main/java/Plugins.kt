object Plugins {

    object Android {
        object Application {
            const val id = "com.android.application"
            const val version = "7.4.2"
        }

        object Library {
            const val id = "com.android.library"
            const val version = "7.4.2"
        }
    }

    object Jetbrains {
        object Kotlin {
            object Android {
                const val id = "org.jetbrains.kotlin.android"
                const val version = "1.8.0"
            }
        }
    }

    object Realm {
        const val id = "io.realm.kotlin"
        const val version = "1.7.0"
    }

    object Google {
        object Gms {
            object GoogleServices {
                const val id = "com.google.gms.google-services"
                const val version = "4.3.15"
            }
        }
    }

    object Kotlin {
        object Kapt {
            const val id = "kotlin-kapt"
        }
    }

    object Detekt {
        const val id = "io.gitlab.arturbosch.detekt"
        const val version = "1.23.0-RC1"
        const val formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$version"
    }
}
