plugins {
    id(Plugins.Android.Application.id) version Plugins.Android.Application.version apply false
    id(Plugins.Android.Library.id) version Plugins.Android.Library.version apply false
    id(Plugins.Jetbrains.Kotlin.Android.id) version Plugins.Jetbrains.Kotlin.Android.version apply false
    id(Plugins.Realm.id) version Plugins.Realm.version apply false
    id(Plugins.DaggerHilt.Android.id) version Plugins.DaggerHilt.Android.version apply false
    id(Plugins.Google.Gms.GoogleServices.id) version Plugins.Google.Gms.GoogleServices.version apply false
    id(Plugins.Ktlint.id) version Plugins.Ktlint.version
    id(Plugins.Detekt.id) version Plugins.Detekt.version
}

subprojects {
    apply { from("${rootProject.projectDir}/${ProjectConfig.buildScriptsFolder}/ktlint.gradle") }
    apply { from("${rootProject.projectDir}/${ProjectConfig.buildScriptsFolder}/detekt.gradle") }
    apply { from("${rootProject.projectDir}/${ProjectConfig.buildScriptsFolder}/translations.gradle") }
}
