package dev.geanbrandao.minhasdespesas.navigation.domain

sealed class NavigationEvent(val destinationRoute: String = String()) {
    // without route
    data object Nothing: NavigationEvent()
    data object NavigateBack: NavigationEvent()
    // custom routes
    data object NavigateToPreferences: NavigationEvent(destinationRoute = Screen.Preferences.route)
    data object NavigateToFilters: NavigationEvent(destinationRoute = Screen.Filters.route)
    data object NavigateToPlayground: NavigationEvent(destinationRoute = "teste")
    data class NavigateToCategories(
        val destination: String = Screen.Categories.route
    ): NavigationEvent(destinationRoute = destination)
    data class NavigateToEditExpense(
        val destination: String
    ): NavigationEvent(destinationRoute = destination)

    // custom behavior
    data class NavigateBackWithResult(
        val key: String,
        val argument: String?,
    ): NavigationEvent()

    data class NavigateToHomeAndRemoveAdd(
        val destination: String,
        val currentRoute: String,
    ): NavigationEvent(destinationRoute = destination)
}