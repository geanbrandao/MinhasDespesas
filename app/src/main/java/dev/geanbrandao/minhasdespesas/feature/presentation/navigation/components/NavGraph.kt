package dev.geanbrandao.minhasdespesas.feature.presentation.navigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.dev.geanbrandao.common.presentation.components.TestPaginationScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Argument
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Key
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.Splashscreen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util.navigateAndRemoveFromBackStack
import dev.geanbrandao.minhasdespesas.localpreferences.presentation.PreferencesScreen
import dev.geanbrandao.minhasdespesas.presentation.addexpenses.AddExpenseScreen
import dev.geanbrandao.minhasdespesas.presentation.categories.CategoriesScreen
import dev.geanbrandao.minhasdespesas.presentation.charts.ChartsScreen
import dev.geanbrandao.minhasdespesas.presentation.filters.FiltersScreen
import dev.geanbrandao.minhasdespesas.presentation.home.HomeScreen
import dev.geanbrandao.minhasdespesas.presentation.profile.ProfileScreen
import dev.geanbrandao.minhasdespesas.splitbill.presentation.SplitBillScreen

@ExperimentalAnimationApi
@Composable
fun NavGraph(navHostController: NavHostController) {

    val navigationEvent = remember {
        mutableStateOf<NavigationEvent>(NavigationEvent.Nothing)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = navigationEvent.value, block = {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            if (navigationEvent.value is NavigationEvent.NavigateBack) {
                navHostController.navigateUp()
            } else if (navigationEvent.value !is NavigationEvent.Nothing) {
                navHostController.navigate(navigationEvent.value.route)
            }
            // reset the event, in case we want call the same event
            // this isn't much intuitive, look for a better way
            navigationEvent.value = NavigationEvent.Nothing
        }
    })

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
                onNavigateToEditExpense = { expenseId: Long ->
                    navHostController.navigate(
                        Screen.Add.route
                            .replace("{expenseId}", "$expenseId"),
                    )
                },
                onNavigateToFilters = {
                    navHostController.navigate(Screen.Filters.route)
                }
            )
        }

        composable(
            route = Screen.Add.route,
            arguments = listOf(
                navArgument(name = Key.EXPENSE_ID) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry: NavBackStackEntry ->
            val selectedCategories = navBackStackEntry.savedStateHandle.get<String?>(Key.SELECTED_CATEGORIES)
            val expenseId = navBackStackEntry.arguments?.getString(Key.EXPENSE_ID) // ta vindo nullo
            AddExpenseScreen(
                argSelectedCategories = selectedCategories,
                argExpenseId = expenseId,
                onNavigateToCategories = { argument: String? ->  // "1,2" esse formato
                    argument?.let {
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
                },
                onNavigateToExpensesRemovingAdd = {
                    navHostController.navigateAndRemoveFromBackStack(
                        destinationRoute = Screen.Expenses.route,
                        currentRoute = Screen.Add.route
                    )
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                navigateTo = {
                    navigationEvent.value = it
                },
            )
        }
        composable(route = Screen.Filters.route) {
            FiltersScreen(navHostController = navHostController)
        }
        composable(route = Screen.Charts.route) {
            ChartsScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.Categories.route,
            arguments = listOf(
                navArgument(Argument.OPTIONAL_SELECTED_CATEGORIES) { // esse nome está incorreto
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) {
//            val selectedCategories = it.arguments?.getString(Key.SELECTED_CATEGORIES)
            CategoriesScreen(
                onNavigateBackToAddExpense = { argument: String? ->
                    // todo call this navigateBackWithResult on navigationEvent
                    navHostController.previousBackStackEntry
                        ?.savedStateHandle?.set(Key.SELECTED_CATEGORIES, argument)
                    navHostController.popBackStack()
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

// todo extract to a new file
sealed class NavigationEvent(val route: String) {
    data object Nothing: NavigationEvent("")
    data object NavigateBack: NavigationEvent("")
    data object NavigateToCategories: NavigationEvent(Screen.Categories.route)
    data object NavigateToPreferences: NavigationEvent(Screen.Preferences.route)
    data object NavigateToFilters: NavigationEvent(Screen.Filters.route)
    data object NavigateToPlayground: NavigationEvent("teste")

}
