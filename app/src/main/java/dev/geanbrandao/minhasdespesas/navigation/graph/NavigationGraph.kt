package dev.geanbrandao.minhasdespesas.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.Splashscreen
import dev.geanbrandao.minhasdespesas.navigation.domain.Destination
import dev.geanbrandao.minhasdespesas.navigation.presentation.NavHost
import dev.geanbrandao.minhasdespesas.navigation.presentation.NavigationEffects
import dev.geanbrandao.minhasdespesas.navigation.presentation.NavigationViewModel
import dev.geanbrandao.minhasdespesas.navigation.presentation.composable
import dev.geanbrandao.minhasdespesas.presentation.addexpenses.AddExpenseScreen
import dev.geanbrandao.minhasdespesas.presentation.categories.CategoriesScreen
import dev.geanbrandao.minhasdespesas.presentation.home.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    viewModel: NavigationViewModel = koinViewModel(),
) {

    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navHostController
    )

    NavHost(
        navController = navHostController,
        startDestination = Destination.Splashscreen,
        builder = {
            composable(destination = Destination.Splashscreen) {
                Splashscreen()
            }
            composable(destination = Destination.Expenses) {
                HomeScreen(openEditExpenseScreen = {}, openFiltersScreen = {})
            }
            composable(destination = Destination.Expense) {
                AddExpenseScreen()
            }

            composable(destination = Destination.Categories) {
                CategoriesScreen(popBackWithResult = {}, goBack = {})
            }
        })
}