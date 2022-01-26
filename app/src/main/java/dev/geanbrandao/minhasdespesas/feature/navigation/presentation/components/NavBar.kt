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
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.splashscreen.util.navigateForNavBar
import dev.geanbrandao.minhasdespesas.ui.theme.NavBarHeightSize

val items = listOf(
    NavBarItem.Expenses,
    NavBarItem.Charts,
    NavBarItem.Profile,
)

@Composable
fun NavBar(
    navHostController: NavHostController,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.height(height = NavBarHeightSize)
    ) {
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
                selected = currentDestination?.hierarchy?.any {
                    it.route == navBarItem.route
                } == true,
                label = { Text(text = stringResource(id = navBarItem.labelId)) },
                onClick = {
                    navHostController.navigateForNavBar(navBarItem.route)
                },
            )
        }
    }
}
