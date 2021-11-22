const val composeVersion = "1.0.1"
const val kotlinVersion = "1.5.21"
const val junitVersion = "4.13.2"

object Androidx {
    const val core = "1.7.0"
    const val appcompat = "1.3.1"
    const val lifecycleRuntime = "2.4.0"
    const val activityCompose = "1.4.0"

    object Test {
        const val junit = "1.1.3"
        const val espresso = "3.4.0"
    }

    object Compose {
        const val ui = composeVersion
        const val material = composeVersion
        const val toolingPreview = composeVersion
        const val tooling = composeVersion
        object Test {
            const val junit = composeVersion
        }
    }
}

object Google {
    const val material = "1.4.0"
}