package dev.geanbrandao.minhasdespesas.feature.presentation.navigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.geanbrandao.minhasdespesas.feature.presentation.add.AddScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.categories.CategoriesScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.charts.ChartsScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.ExpensesScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.filters.FiltersScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.Splashscreen
import dev.geanbrandao.minhasdespesas.ui.theme.NavBarHeightSize

@ExperimentalAnimationApi
@ExperimentalMaterialApi
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
            ExpensesScreen(
                modifier = Modifier.padding(bottom = NavBarHeightSize),
                navHostController = navHostController
            )
        }
        composable(route = Screen.Add.route) {
            AddScreen(navHostController = navHostController)
        }
        composable(route = Screen.Profile.route) {
            Text(text = "PROFILE")
        }
        composable(route = Screen.Filters.route) {
            FiltersScreen(navHostController = navHostController)
        }
        composable(route = Screen.Charts.route) {
            ChartsScreen(navHostController = navHostController)
        }
        composable(route = Screen.Categories.route) {
            CategoriesScreen(navHostController = navHostController)
        }
    }
}
