plugins {
    id(Plugins.Android.Application.id) version Plugins.Android.Application.version apply false
    id(Plugins.Android.Library.id) version Plugins.Android.Library.version apply false
    id(Plugins.Jetbrains.Kotlin.Android.id) version Plugins.Jetbrains.Kotlin.Android.version apply false
    id(Plugins.Realm.id) version Plugins.Realm.version apply false
    id(Plugins.Google.Gms.GoogleServices.id) version Plugins.Google.Gms.GoogleServices.version apply false
    id(Plugins.Detekt.id) version Plugins.Detekt.version
}

tasks.register("detektAll", io.gitlab.arturbosch.detekt.Detekt::class) {
    val autoFix = true //project.hasProperty("detektAutoFix")

    description = "Custom DETEKT build for all modules"
    parallel = true
    ignoreFailures = false
    autoCorrect = autoFix
    buildUponDefaultConfig = true
    setSource(file(projectDir))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))
    include("**/*.kt")
    exclude("**/resources/**", "**/build/**")
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
    }
}

tasks.register("detektBaselineAll", io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
    description = "Custom DETEKT baseline for all modules"
    setSource(file(projectDir))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))
    include("**/*.kt")
    exclude("**/resources/**", "**/build/**")
}

dependencies {
    detektPlugins(Plugins.Detekt.formatting)
}

apply { from("${rootProject.projectDir}/${ProjectConfig.buildScriptsFolder}/translations.gradle") }
