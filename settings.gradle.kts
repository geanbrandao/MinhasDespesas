dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "My Expenses Compose"
include (":app")
include(":pie-charts")
include(":feature-split-bill")
include(":common")
include(":local-preferences")
