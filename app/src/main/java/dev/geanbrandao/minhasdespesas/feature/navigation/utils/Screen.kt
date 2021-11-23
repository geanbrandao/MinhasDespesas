package dev.geanbrandao.minhasdespesas.feature.navigation.utils

import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Routes.ROUTE_ADD
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Routes.ROUTE_EXPENSES
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Routes.ROUTE_PROFILE
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Routes.ROUTE_SPLASHSCREEN

sealed class Screen(val route: String) {
    object Splashscreen : Screen(ROUTE_SPLASHSCREEN)
    object Expenses : Screen(ROUTE_EXPENSES)
    object Add : Screen(ROUTE_ADD)
    object Profile : Screen(ROUTE_PROFILE)
}
