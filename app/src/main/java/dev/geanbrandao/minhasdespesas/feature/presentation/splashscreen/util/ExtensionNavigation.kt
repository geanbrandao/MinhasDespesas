package dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util

import androidx.navigation.NavHostController
import dev.geanbrandao.minhasdespesas.navigation.domain.Screen

fun NavHostController.navigateAndRemoveFromBackStack(destinationRoute: String, currentRoute: String) {
    navigate(route = destinationRoute) {
        popUpTo(currentRoute) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateForNavBar(destinationRoute: String) {
    navigate(destinationRoute) {
        popUpTo(Screen.Expenses.route) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

