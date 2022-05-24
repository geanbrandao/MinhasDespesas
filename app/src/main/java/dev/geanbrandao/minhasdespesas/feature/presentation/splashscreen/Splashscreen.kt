package dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.utils.extensions.createDefaultCategories
import dev.geanbrandao.minhasdespesas.common.utils.extensions.clickableRoundedEffect
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util.navigateAndRemoveFromBackStack
import dev.geanbrandao.minhasdespesas.ui.theme.SplashscreenLogoHomeSize

@Composable
fun Splashscreen(
    navHostController: NavHostController,
    modifier: Modifier,
    viewModel: SplashscreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.insertDefaultCategories(context.createDefaultCategories())
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Image(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = stringResource(id = R.string.splashscreen_content_description_ic_home),
            modifier = Modifier.size(size = SplashscreenLogoHomeSize)
                .clickableRoundedEffect {
                    navHostController.navigateAndRemoveFromBackStack(
                        destinationRoute = Screen.Expenses.route,
                        currentRoute = Screen.Splashscreen.route
                    )
                }
        )
    }
}

@Preview("Splashscreen")
@Composable
fun Preview() {
    Splashscreen(rememberNavController(), Modifier)
}
