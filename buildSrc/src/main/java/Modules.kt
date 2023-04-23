object Modules {
    const val app = ":app"

    object Core {
        const val ui = ":core:ui"
        const val util = ":core:util"
        const val translations = ":core:translations"
    }

    object Feature {
        const val auth = ":feature:auth"
        const val home = ":feature:home"
        const val write = ":feature:write"
    }

    object Data {
        const val mongo = ":data:mongo"
    }
}