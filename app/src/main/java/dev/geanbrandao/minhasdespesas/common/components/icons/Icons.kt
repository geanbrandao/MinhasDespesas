package dev.geanbrandao.minhasdespesas.common.components.icons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.geanbrandao.minhasdespesas.R

@Composable
fun IconInput(
    @DrawableRes iconId: Int,
    iconSize: Dp,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = contentDescription,
        modifier = modifier.size(size = iconSize),
        tint = MaterialTheme.colorScheme.secondary,
    )
}

@Composable
fun IconDefault(
    onColor: Color,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    androidx.compose.material.Icon(
        painter = painterResource(id = R.drawable.ic_arrow_back),
        contentDescription = "Icone voltar",
        tint = onColor,
        modifier = modifier
            .size(24.dp)
            .clickable {
                navHostController.navigateUp()
            }
    )
}