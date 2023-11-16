package dev.geanbrandao.minhasdespesas.feature.presentation.navigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.dev.geanbrandao.common.presentation.components.TestPaginationScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.charts.ChartsScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Argument
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Key
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.Splashscreen
import dev.geanbrandao.minhasdespesas.localpreferences.presentation.PreferencesScreen
import dev.geanbrandao.minhasdespesas.presentation.addexpenses.AddExpenseScreen
import dev.geanbrandao.minhasdespesas.presentation.categories.CategoriesScreen
import dev.geanbrandao.minhasdespesas.presentation.filters.FiltersScreen
import dev.geanbrandao.minhasdespesas.presentation.home.HomeScreen
import dev.geanbrandao.minhasdespesas.presentation.profile.ProfileScreen
import dev.geanbrandao.minhasdespesas.splitbill.presentation.SplitBillScreen

@ExperimentalAnimationApi
@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Splashscreen.route
    ) {
        composable(route = Screen.Splashscreen.route) {
            Splashscreen(
                navHostController = navHostController,
                modifier = Modifier,
            )
        }
        composable(route = Screen.Expenses.route) {
            HomeScreen(
                navHostController = navHostController,
                onNavigateToEditExpense = { expenseId: Long ->
                    navHostController.navigate(
                        Screen.Add.route
                            .replace("{expenseId}", "$expenseId"),
                    )
                }
            )
        }
        composable(route = Screen.Add.route) {
            val selectedCategories = it.savedStateHandle.get<String?>(Key.SELECTED_CATEGORIES)
            val expenseId = it.savedStateHandle.get<String?>(Key.EXPENSE_ID)
            AddExpenseScreen(
                navHostController = navHostController,
                selectedCategories = selectedCategories,
                expenseId = expenseId,
                onNavigateToCategories = { selectedCategories: String? ->  // "1,2" esse formato
                    selectedCategories?.let {
                        navHostController.navigate(
                            Screen.Categories.route
                                .replace(
                                    oldValue = "{selectedCategories}",
                                    newValue = it
                                ),
                        )
                    } ?: run {
                        navHostController.navigate(
                            Screen.Categories.route
                        )
                    }
                }
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navHostController = navHostController)
        }
        composable(route = Screen.Filters.route) {
            FiltersScreen(navHostController = navHostController)
//            FiltersScreen(navHostController = navHostController)
        }
        composable(route = Screen.Charts.route) {
            ChartsScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.Categories.route,
            arguments = listOf(
                navArgument(Argument.OPTIONAL_SELECTED_CATEGORIES) {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) {
//            val selectedCategories = it.arguments?.getString(Key.SELECTED_CATEGORIES)
            CategoriesScreen(
                navHostController = navHostController,
                onNavigateBackToAddExpense = { argument: String? ->
                    navHostController.popBackStack()
                    navHostController
                        .currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(Key.SELECTED_CATEGORIES, argument)
                }
            )
        }
        composable(route = Screen.SplitBill.route) {
            SplitBillScreen(navHostController = navHostController)
        }
        composable(route = Screen.Preferences.route) {
            PreferencesScreen(navHostController = navHostController)
        }
        composable(route = "teste") {
            TestPaginationScreen()
        }
    }
}
