package dev.geanbrandao.minhasdespesas.feature.navigation.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.feature.splashscreen.presentation.splashscreen.Splashscreen
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Screen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Splashscreen.route
    ) {
        composable(route = Screen.Splashscreen.route) {
            Splashscreen(navHostController = navHostController)
        }
        composable(route = Screen.Expenses.route) {
            Text(text = "EXPENSES")
        }
        composable(route = Screen.Add.route) {
            Text(text = "ADD")
        }
        composable(route = Screen.Profile.route) {
            Text(text = "PROFILE")
        }
    }
}
