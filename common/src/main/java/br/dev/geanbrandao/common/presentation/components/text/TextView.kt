package br.dev.geanbrandao.common.presentation.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import br.dev.geanbrandao.common.presentation.resources.AppTypography

@Composable
fun TextSupporting(
    text: String,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        textAlign = textAlign,
        modifier = modifier,
        style = AppTypography.labelSmall,
    )
}

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
fun TextAction(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = AppTypography.bodyMedium,
        color = color,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun TextSmall(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = color,
        style = AppTypography.bodySmall,
        modifier = modifier,
    )
}

@Composable
fun TextLabelInput(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        color = textColor,
        style = AppTypography.bodyMedium,
    )
}

@Composable
fun TextToolbar(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = AppTypography.bodyLarge,
        color = textColor,
        modifier = modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun TextButtonDefault(
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    Text(
        text = text,
        style = AppTypography.bodyMedium,
        color = color,
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
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        style = AppTypography.bodyLarge,
        fontWeight = FontWeight.Normal,
        color = textColor,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        textAlign = textAlign
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

@Composable
fun TextBodyLarge(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = AppTypography.bodyLarge,
        color = color,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun TextBodyMedium(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = AppTypography.bodyMedium,
        color = color,
        modifier = modifier,
    )
}

@Composable
fun TextLabelSmall(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = AppTypography.labelSmall,
        color = color,
        modifier = modifier,
    )
}