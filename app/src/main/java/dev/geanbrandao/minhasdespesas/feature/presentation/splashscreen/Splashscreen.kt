package dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import dev.geanbrandao.minhasdespesas.R
//import dev.geanbrandao.minhasdespesas.common.utils.extensions.createDefaultCategories
import dev.geanbrandao.minhasdespesas.common.utils.extensions.clickableRoundedEffect
import dev.geanbrandao.minhasdespesas.common.utils.extensions.createDefaultCategories
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util.navigateAndRemoveFromBackStack
import dev.geanbrandao.minhasdespesas.ui.theme.SplashscreenLogoHomeSize
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun Splashscreen(
    navHostController: NavHostController,
    modifier: Modifier,
    viewModel: SplashscreenViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val timeInMillis = getCurrentTimeInMillis()
    val date = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timeInMillis), ZoneId.systemDefault())
    val dateString = date.format(formatter)

    println("############################ AQUIII - $timeInMillis")
    println("############################ AQUIII - $date")
    println("############################ AQUIII - $dateString")
    println("############################ AQUIII - ${formatter.parse(dateString, OffsetDateTime::from)}")
    println("############################ AQUIII - ${date.toInstant().toEpochMilli()}")
    println("############################ AQUIII - ${OffsetDateTime.ofInstant(Instant.ofEpochMilli(date.toInstant().toEpochMilli()), ZoneId.systemDefault())}")


    LaunchedEffect(Unit) {
        viewModel.insertDefaultCategories(context.createDefaultCategories())
    }

    val isReady = viewModel.isReady.collectAsState()
    if (isReady.value) {
        navHostController.navigateAndRemoveFromBackStack(
            destinationRoute = Screen.Expenses.route,
            currentRoute = Screen.Splashscreen.route
        )
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
            modifier = Modifier
                .size(size = SplashscreenLogoHomeSize)
                .clickableRoundedEffect {
//                    navHostController.navigateAndRemoveFromBackStack(
//                        destinationRoute = Screen.Expenses.route,
//                        currentRoute = Screen.Splashscreen.route
//                    )
                }
        )
    }
}

@Preview("Splashscreen")
@Composable
fun Preview() {
    Splashscreen(rememberNavController(), Modifier)
}
