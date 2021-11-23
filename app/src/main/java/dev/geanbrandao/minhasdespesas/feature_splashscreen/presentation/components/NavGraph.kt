package dev.geanbrandao.minhasdespesas.feature_splashscreen.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.feature_splashscreen.presentation.splashscreen.Splashscreen
import dev.geanbrandao.minhasdespesas.feature_splashscreen.util.Screen

@Composable
fun NavGraph() {
    NavHost(
        navController = rememberNavController(),
        startDestination = Screen.Splashscreen.route
    ) {
        composable(route = Screen.Splashscreen.route) {
            Splashscreen()
        }
    }
}