package dev.geanbrandao.minhasdespesas.navigation.bottomnavigationbar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util.navigateForNavBar
import dev.geanbrandao.minhasdespesas.navigation.domain.NavBarItem
import dev.geanbrandao.minhasdespesas.ui.theme.NavBarHeightSize

val items = listOf(
    NavBarItem.Expenses,
    NavBarItem.Charts,
    NavBarItem.Add,
    NavBarItem.SplitBill,
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
                        ),
                        modifier =  if (navBarItem is NavBarItem.Add) {
                            Modifier.size(36.dp)
                        } else Modifier
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
