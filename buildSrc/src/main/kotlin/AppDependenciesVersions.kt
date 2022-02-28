const val composeVersion = "1.1.1"
const val kotlinVersion = "1.6.10"
const val junitVersion = "4.13.2"
const val hiltVersion = "2.41"
const val calendarCompose = "0.3.0"

object Androidx {
    const val core = "1.7.0"
    const val appcompat = "1.4.1"
    const val lifecycleRuntime = "2.4.1"
    const val activityCompose = "1.4.0"
    const val navigationCompose = "2.4.1"
    const val room = "2.4.2"
    const val hilt = "1.0.0"

    object Test {
        const val junit = "1.1.3"
        const val espresso = "3.4.0"
        const val core = "1.4.0"
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
    const val material = "1.5.0"
    const val hilt = "2.40.5"
}

object Preferences {
    const val composePreferences = "0.1.1"
}

object Jetbrains {
    const val kotlinxCoroutines = "1.6.0-native-mt"
}