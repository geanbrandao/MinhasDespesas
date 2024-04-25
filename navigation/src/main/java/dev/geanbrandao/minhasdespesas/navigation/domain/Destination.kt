package dev.geanbrandao.minhasdespesas.navigation.domain

const val ROUTE_SPLASHSCREEN = "splashscreen"
const val ROUTE_EXPENSES = "expenses"
const val ROUTE_EXPENSE = "expense"
const val ROUTE_CATEGORIES = "categories"
const val ROUTE_ADD = "addExpense"
const val ROUTE_PROFILE = "profile"
const val ROUTE_FILTERS = "filters"
const val ROUTE_PLAYGROUND = "teste"
const val ROUTE_CHARTS = "charts"
const val ROUTE_SPLIT_BILL = "splitBill"
const val ROUTE_PREFERENCES = "preferences"

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) {
        route
    } else {
        route.buildFullRoute(*params)
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object Splashscreen: NoArgumentsDestination(ROUTE_SPLASHSCREEN)
    data object Expenses: NoArgumentsDestination(ROUTE_EXPENSES)
    data object Preferences : NoArgumentsDestination(ROUTE_PREFERENCES)
    data object Filters: NoArgumentsDestination(ROUTE_FILTERS)
    data object Playground: NoArgumentsDestination(ROUTE_PLAYGROUND)
    data object Categories: NoArgumentsDestination(ROUTE_CATEGORIES)

    data object Expense : Destination(route = ROUTE_EXPENSE, "expenseId") {
        const val EXPENSE_ID_KEY = "expenseId"

        operator fun invoke(expenseId: String): String = route.buildRouteWithParams(
            EXPENSE_ID_KEY to expenseId
        )
    }
}



internal fun String.buildFullRoute(vararg params: String): String {
    return buildString {
        append(this@buildFullRoute)
        append("?")
        params.forEachIndexed { index, item ->
            append("$item={${item}}")
            if (index != params.lastIndex) {
                append("&")
            }
        }
    }
}

internal fun String.buildRouteWithParams(vararg params: Pair<String, String?>): String {
    return buildString {
        append(this@buildRouteWithParams)
        append("?")
        params.forEachIndexed { index, param ->
            param.second?.let {
                append("${param.first}=${it}")
            } ?: run {
                append("${param.first}={${param.first}}")
            }
            if (index != params.lastIndex) {
                append("&")
            }
        }
    }
}