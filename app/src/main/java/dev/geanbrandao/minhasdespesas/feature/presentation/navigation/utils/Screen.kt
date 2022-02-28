package dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils

import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_ADD
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_CATEGORIES
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_CHARTS
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_EXPENSES
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_FILTERS
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_PROFILE
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_SPLASHSCREEN

sealed class Screen(val route: String) {
    object Splashscreen : Screen(ROUTE_SPLASHSCREEN)
    object Expenses : Screen(ROUTE_EXPENSES)
    object Add : Screen(ROUTE_ADD)
    object Profile : Screen(ROUTE_PROFILE)
    object Filters : Screen(ROUTE_FILTERS)
    object Charts : Screen(ROUTE_CHARTS)
    object Categories : Screen(ROUTE_CATEGORIES)
}
