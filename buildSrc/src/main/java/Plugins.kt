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

    object DaggerHilt {
        object Android {
            const val id = "com.google.dagger.hilt.android"
            const val version = "2.44"
        }

        object Plugin {
            const val id = "dagger.hilt.android.plugin"
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

    object Ktlint {
        const val id = "org.jlleitschuh.gradle.ktlint"
        const val version = "11.3.1"
    }

    object Detekt {
        const val id = "io.gitlab.arturbosch.detekt"
        const val version = "1.23.0-RC1"
    }
}