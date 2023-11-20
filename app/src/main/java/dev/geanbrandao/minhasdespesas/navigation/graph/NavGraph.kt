package dev.geanbrandao.minhasdespesas.navigation.graph

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
import br.dev.geanbrandao.common.domain.completeRoute
import br.dev.geanbrandao.common.presentation.components.TestPaginationScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.Splashscreen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util.navigateAndRemoveFromBackStack
import dev.geanbrandao.minhasdespesas.localpreferences.presentation.PreferencesScreen
import dev.geanbrandao.minhasdespesas.navigation.domain.Key
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateBack
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateBackWithResult
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateToCategories
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateToEditExpense
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateToFilters
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateToHomeAndRemoveAdd
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateToPlayground
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.NavigateToPreferences
import dev.geanbrandao.minhasdespesas.navigation.domain.NavigationEvent.Nothing
import dev.geanbrandao.minhasdespesas.navigation.domain.Screen
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
        mutableStateOf<NavigationEvent>(Nothing)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = navigationEvent.value, key2 = lifecycleOwner) { // todo mudei aqui
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            when (val navEvent = navigationEvent.value) {
                is NavigateBack -> {
                    navHostController.navigateUp()
                }

                is NavigateBackWithResult -> {
                    // save the argument in the previous screen
                    // then pop back to the previous screen
                    navHostController.previousBackStackEntry
                        ?.savedStateHandle?.set(key = navEvent.key, value = navEvent.argument)
                    navHostController.popBackStack()
                }

                is NavigateToHomeAndRemoveAdd -> {
                    navHostController.navigateAndRemoveFromBackStack(
                        destinationRoute = navEvent.destinationRoute,
                        currentRoute = navEvent.currentRoute
                    )
                }

                !is Nothing -> {
                    navHostController.navigate(navigationEvent.value.destinationRoute)
                }

                else -> Unit
            }
            // reset the event, in case we want call the same event
            // this isn't much intuitive, look for a better way
            navigationEvent.value = Nothing
        }
    }

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
                openEditExpenseScreen = { expenseId: Long ->
                    navigationEvent.value = NavigateToEditExpense(
                        destination = Screen.Add.route.completeRoute(Key.EXPENSE_ID, expenseId.toString())
                    )
                },
                openFiltersScreen = { navigationEvent.value = NavigateToFilters }
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
            val expenseId = navBackStackEntry.arguments?.getString(Key.EXPENSE_ID)
            AddExpenseScreen(
                argSelectedCategories = selectedCategories,
                argExpenseId = expenseId,
                openCategories = { argument: String? ->  // "1,2" esse formato
                    val route = argument?.let {
                        Screen.Categories.route.completeRoute(Key.SELECTED_CATEGORIES, it)
                    } ?: Screen.Categories.route
                    navigationEvent.value = NavigateToCategories(destination = route)
                },
                goBack = { navigationEvent.value = NavigateBack }
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                openCategoriesScreen = { navigationEvent.value = NavigateToCategories() },
                openPreferenceScreen = { navigationEvent.value = NavigateToPreferences },
                openFiltersScreen = { navigationEvent.value = NavigateToFilters },
                openPlaygroundScreen = { navigationEvent.value = NavigateToPlayground },
            )
        }

        composable(route = Screen.Filters.route) {
            FiltersScreen(
                goBack = { navigationEvent.value = NavigateBack }
            )
        }

        composable(route = Screen.Charts.route) {
            ChartsScreen()
        }

        composable(
            route = Screen.Categories.route,
            arguments = listOf(
                navArgument(Key.SELECTED_CATEGORIES) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry: NavBackStackEntry ->
            // not being use because already get on the screen view model
            val selectedCategories = navBackStackEntry.arguments?.getString(Key.SELECTED_CATEGORIES)
            CategoriesScreen(
                popBackWithResult = { argument: String? ->
                    navigationEvent.value = NavigateBackWithResult(
                        key = Key.SELECTED_CATEGORIES,
                        argument = argument,
                    )
                }
            )
        }
        composable(route = Screen.SplitBill.route) {
            SplitBillScreen()
        }
        composable(route = Screen.Preferences.route) {
            PreferencesScreen(
                goBack = { navigationEvent.value = NavigateBack }
            )
        }
        composable(route = "teste") {
            TestPaginationScreen()
        }
    }
}
