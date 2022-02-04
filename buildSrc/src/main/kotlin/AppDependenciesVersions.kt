const val composeVersion = "1.0.5"
const val kotlinVersion = "1.5.31"
const val junitVersion = "4.13.2"
const val hiltVersion = "2.40.5"
const val calendarCompose = "0.3.0"

object Androidx {
    const val core = "1.7.0"
    const val appcompat = "1.4.0"
    const val lifecycleRuntime = "2.4.0"
    const val activityCompose = "1.4.0"
    const val navigation = "2.4.0-beta02"
    const val room = "2.4.0"
    const val hilt = "1.0.0-alpha03"

    object Test {
        const val junit = "1.1.3"
        const val espresso = "3.4.0"
    }

    object Compose {
        const val ui = composeVersion
        const val uiTest = "1.1.0-beta03"
        const val material = composeVersion
        const val material3 = "1.0.0-alpha01"
        const val toolingPreview = composeVersion
        const val tooling = composeVersion

        object Test {
            const val junit = composeVersion
        }
    }
}

object Google {
    const val material = "1.4.0"
    const val hilt = "2.40.5"
}

object Preferences {
    const val composePreferences = "0.1.1"
}
