package dev.geanbrandao.minhasdespesas.common.components.texts

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import java.util.*

@Composable
fun TextLabelScreen(
    stringId: Int,
    textColor: Color = MaterialTheme.colorScheme.secondary
) {
    Text(
        text = stringResource(id = stringId),
        color = textColor,
        style = AppTypography.bodyLarge,
    )
}

@Composable
fun TextLabelInput(
    stringId: Int,
    textColor: Color = MaterialTheme.colorScheme.secondary
) {
    Text(
        text = stringResource(id = stringId),
        color = textColor,
        style = AppTypography.bodyMedium,
    )
}

@Composable
fun TextToolbar(
    @StringRes stringId: Int,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = stringId),
        style = AppTypography.bodyLarge,
        color = textColor,
        modifier = modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun TextButtonDefault(
    @StringRes stringId: Int,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Text(
        text = stringResource(id = stringId).uppercase(),
        style = AppTypography.bodyMedium,
        color = textColor,
    )
}