package dev.geanbrandao.minhasdespesas.feature.presentation.expenses.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.COMPONENT_SCREEN_WARNING
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun ScreenWarningWithTitleMessage(
    modifier: Modifier,
    emptyWarningTitle: String,
    emptyWarningMessage: String
) {
    Column(
        modifier = modifier
            .testTag(COMPONENT_SCREEN_WARNING)
            .padding(horizontal = PaddingDefault, vertical = PaddingDefault),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = emptyWarningTitle,
            style = AppTypography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        SpacerTwo()
        Text(
            text = emptyWarningMessage,
            style = AppTypography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview("EmptyExpensesMessage")
fun Preview2() {
    ScreenWarningWithTitleMessage(
        Modifier,
        "Teste",
        "uadhduhaduah audhaudhaudha audhaudhaud audhaudha "
    )
}
