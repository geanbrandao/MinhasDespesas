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
import androidx.compose.ui.text.style.TextOverflow
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography

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

@Composable
fun TextDateDay(text: String) {
    Text(text = text)
}

@Composable
fun TextItemCategory(categoryName: String, modifier: Modifier, color: Color) {
    Text(
        text = categoryName,
        style = AppTypography.titleMedium,
        fontWeight = FontWeight.Light,
        color = color,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
fun TextDefault(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = AppTypography.bodyLarge,
        fontWeight = FontWeight.Normal,
        color = textColor,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

// item expense
@Composable
fun TextItemExpenseDate(
    text: String,
) {
    Text(
        text = "05",
        style = AppTypography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
fun TextTitleLarge(text: String, color: Color = MaterialTheme.colorScheme.onBackground) {
    Text(
        text = text,
        style = AppTypography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = color,
    )
}

@Composable
fun TextLabelMedium(text: String, color: Color = MaterialTheme.colorScheme.onBackground) {
    Text(
        text = text,
        style = AppTypography.labelMedium,
        fontWeight = FontWeight.Light,
        color = color,
    )
}

@Composable
fun TextBodyMediumSingleLine(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        style = AppTypography.bodyMedium,
        fontWeight = FontWeight.Light,
        color = color,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}