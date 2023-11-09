package dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils

import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Argument.OPTIONAL_EXPENSE_ID
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Argument.OPTIONAL_SELECTED_CATEGORIES
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Key.EXPENSE_ID
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Key.SELECTED_CATEGORIES
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_ADD
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_CATEGORIES
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_CHARTS
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_EXPENSES
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_FILTERS
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_PREFERENCES
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_PROFILE
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_SPLASHSCREEN
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Routes.ROUTE_SPLIT_BILL

sealed class Screen(val route: String) {
    data object Splashscreen : Screen(ROUTE_SPLASHSCREEN)
    data object Expenses : Screen(ROUTE_EXPENSES)
    data object Add : Screen(ROUTE_ADD)
    data object Profile : Screen(ROUTE_PROFILE)
    data object Filters : Screen(ROUTE_FILTERS)
    data object Charts : Screen(ROUTE_CHARTS)
    data object Categories : Screen(ROUTE_CATEGORIES)
    data object SplitBill : Screen(ROUTE_SPLIT_BILL)
    data object Preferences : Screen(ROUTE_PREFERENCES)
}

object Routes {
    const val ROUTE_SPLASHSCREEN = "splashscreen"
    const val ROUTE_EXPENSES = "expenses"
    const val ROUTE_ADD = "add$OPTIONAL_EXPENSE_ID"
    const val ROUTE_PROFILE = "profile"
    const val ROUTE_FILTERS = "filters"
    const val ROUTE_CHARTS = "charts"
    const val ROUTE_CATEGORIES = "categories$OPTIONAL_SELECTED_CATEGORIES"
    const val ROUTE_SPLIT_BILL = "splitBill"
    const val ROUTE_PREFERENCES = "preferences"
}

object Argument {
    const val OPTIONAL_SELECTED_CATEGORIES = "?selectedCategories={$SELECTED_CATEGORIES}"
    const val OPTIONAL_EXPENSE_ID = "?expenseId={$EXPENSE_ID}"
}

object Key {
    const val SELECTED_CATEGORIES = "selectedCategories"
    const val EXPENSE_ID = "expenseId"
}
