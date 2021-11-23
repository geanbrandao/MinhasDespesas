package dev.geanbrandao.minhasdespesas.feature.navigation.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.NavBarItem

val items = listOf(
    NavBarItem.Expenses,
    NavBarItem.Add,
    NavBarItem.Profile,
)

@Composable
fun NavBar(
    navHostController: NavHostController,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        items.forEach { navBarItem ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = navBarItem.iconId),
                        contentDescription = stringResource(
                            id = navBarItem.contentDescriptionId
                        )
                    )
                },
                label = { Text(text = stringResource(id = navBarItem.labelId)) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == navBarItem.route
                } == true,
                alwaysShowLabel = false,
                onClick = {
                    navHostController.navigate(navBarItem.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
