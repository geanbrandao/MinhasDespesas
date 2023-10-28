package dev.geanbrandao.minhasdespesas.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.domain.MoneyUtils.formatToBrl
import br.dev.geanbrandao.common.domain.PATTERN_DAY
import br.dev.geanbrandao.common.domain.PATTERN_MONTH_THREE
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import br.dev.geanbrandao.common.domain.getDayNumber
import br.dev.geanbrandao.common.domain.getMonth3LettersName
import br.dev.geanbrandao.common.domain.toBrazilianDateFormat
import br.dev.geanbrandao.common.presentation.resources.MarginTwo
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.common.components.dividers.DividerInput
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import java.util.*


@Composable
fun ItemExpenseView(
    item: Expense,
    isLastItem: Boolean,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(end = PaddingTwo, start = PaddingTwo, top = PaddingOne)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DateExpenseView(item.selectedDate)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = item.name,
                style = AppTypography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = item.amount.formatToBrl(),
                style = AppTypography.headlineMedium,
                fontWeight = FontWeight.Black,
            )
        }
        if (isLastItem.not()) {
            Spacer(modifier = Modifier.size(MarginTwo))
            DividerInput()
        }
    }
}

@Composable
private fun DateExpenseView(selectedDate: Long) {
    Column {
        Text(
            text = selectedDate.toBrazilianDateFormat(PATTERN_DAY),
            style = AppTypography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = selectedDate.toBrazilianDateFormat(PATTERN_MONTH_THREE)
                .replace(".", "")
                .uppercase(Locale.getDefault()),
            style = AppTypography.titleSmall,
            fontWeight = FontWeight.Light,
        )
    }
}

@Preview
@Composable
private fun ItemExpenseView() {
    Column {
        ItemExpenseView(
            item = Expense(
                expenseId = 1,
                amount = 23.5f,
                name = "Despesa 2",
                selectedDate = getCurrentTimeInMillis(),
                description = "descricao",
                categories = listOf(),
                createdAt = getCurrentTimeInMillis(),
                updatedAt = getCurrentTimeInMillis()
            ),
            isLastItem = false
        )
        ItemExpenseView(
            item = Expense(
                expenseId = 1,
                amount = 23.5f,
                name = "Despesa 2",
                selectedDate = getCurrentTimeInMillis(),
                description = "descricao",
                categories = listOf(),
                createdAt = getCurrentTimeInMillis(),
                updatedAt = getCurrentTimeInMillis()
            ),
            isLastItem = true
        )
    }
}