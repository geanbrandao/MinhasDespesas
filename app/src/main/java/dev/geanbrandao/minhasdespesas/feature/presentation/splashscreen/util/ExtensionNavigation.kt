package dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util

import android.os.Parcelable
import androidx.navigation.NavHostController
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen

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

fun NavHostController.navigateWithArgument(
    destinationRoute: String,
    keyArg: String,
    arg: Parcelable
) {
    currentBackStackEntry?.arguments?.apply {
        putParcelable(keyArg, arg)
    }
    navigate(route = destinationRoute) // todo errado
}

