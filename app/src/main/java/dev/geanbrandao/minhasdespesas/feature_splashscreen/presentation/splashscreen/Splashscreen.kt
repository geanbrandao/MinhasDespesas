package dev.geanbrandao.minhasdespesas.feature_splashscreen.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.ui.theme.SplashscreenLogoHomeSize

@Composable
fun Splashscreen() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = stringResource(id = R.string.splashscreen_content_description_ic_home),
            modifier = Modifier.size(size = SplashscreenLogoHomeSize)
        )
    }
}

@Preview("Splashscreen")
@Composable
fun Preview() {
    Splashscreen()
}