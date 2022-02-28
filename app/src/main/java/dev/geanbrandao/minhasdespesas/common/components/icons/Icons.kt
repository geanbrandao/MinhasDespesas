package dev.geanbrandao.minhasdespesas.common.components.icons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.geanbrandao.minhasdespesas.ui.theme.SizeIconLarge

@Composable
fun IconInput(
    @DrawableRes iconId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    iconSize: Dp = SizeIconLarge,
    tint: Color = MaterialTheme.colorScheme.secondary,
) {
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = contentDescription,
        modifier = modifier.size(size = iconSize),
        tint = tint,
    )
}

@Composable
fun IconDefault(
    @DrawableRes iconId: Int,
    tint: Color,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    onIconClick: (() -> Unit)? = {},
) {
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .size(24.dp)
            .run {
                onIconClick?.let {
                    clickable {it.invoke() }
                } ?: this
            }
    )
}