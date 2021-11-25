package dev.geanbrandao.minhasdespesas.feature.splashscreen.presentation.splashscreen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.splashscreen.util.navigateAndRemoveFromBackStack
import dev.geanbrandao.minhasdespesas.ui.theme.SplashscreenLogoHomeSize
import kotlinx.coroutines.delay

@Composable
fun Splashscreen(
    navHostController: NavHostController,
    modifier: Modifier
) {
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
                .clickable {
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
