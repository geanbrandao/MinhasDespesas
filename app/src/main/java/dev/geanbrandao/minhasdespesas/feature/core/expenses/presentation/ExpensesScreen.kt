package dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components.ScreenWarningWithTitleMessage
import dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components.ItemExpenseWithSwipe
import dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components.ItemFilter
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.splashscreen.util.navigateForNavBar
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.CardElevation
import dev.geanbrandao.minhasdespesas.ui.theme.MarginDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesScreen(
    modifier: Modifier,
    navHostController: NavHostController,
) {
    val itemFilterList = remember {
        mutableStateListOf("Filtro 1", "Filtro 2", "Filtro 3", "Filtro 4", "Filtro 5")
    }
    val itemExpenseList = remember {
        mutableStateListOf("Expense 1", "Expense 2", "Expense 3")
    }

    val amountExpense = remember {
        mutableStateOf(25.97f)
    }

    val countExpense = remember {
        mutableStateOf(4)
    }

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        ExpensesScreenHeader(amountExpense, countExpense)
        Column {
            ListSelectedFilters(itemFilterList)
            Box(modifier = Modifier.fillMaxSize()) {
                ListExpenses(itemExpenseList, navHostController)
                if (itemExpenseList.isEmpty() and itemFilterList.isEmpty()) {
                    ScreenWarningWithTitleMessage(
                        modifier = Modifier.align(alignment = Alignment.BottomCenter),
                        emptyWarningTitle = stringResource(
                            id = R.string.fragment_expenses_warning_title_no_expenses),
                        emptyWarningMessage = stringResource(
                            id = R.string.fragment_expenses_warning_message_no_expenses
                        )
                    )
                }
                if (itemExpenseList.isEmpty() and itemFilterList.isNotEmpty()) {
                    ScreenWarningWithTitleMessage(
                        modifier = Modifier.align(alignment = Alignment.BottomCenter),
                        emptyWarningTitle = stringResource(
                            id = R.string.fragment_expenses_warning_title_no_expenses_when_filter_selected
                        ),
                        emptyWarningMessage = stringResource(
                            id = R.string.fragment_expenses_warning_message_no_expenses_when_filter_selected
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ListExpenses(
    itemExpenseList: SnapshotStateList<String>,
    navHostController: NavHostController
) {
    LazyColumn {
        itemsIndexed(
            items = itemExpenseList,
            key = { _: Int, item: String ->
                item.hashCode()
            }
        ) { index, item ->
            ItemExpenseWithSwipe(
                index = index,
                itemExpenseList = itemExpenseList,
                onSwipeToDelete = {
                    itemExpenseList.remove(item)
                },
                onSwipeToEdit = {
                    navHostController.navigateForNavBar(Screen.Add.route)
                    itemExpenseList.remove(item)
                    itemExpenseList.add(index, item)
                },
            )
        }
    }
}

@Composable
private fun ListSelectedFilters(itemFilterList: SnapshotStateList<String>) {
    LazyRow(contentPadding = PaddingValues(all = MarginDefault)) {
        items(itemFilterList) { item ->
            ItemFilter(item) {
                itemFilterList.remove(item)
            }
        }
    }
}

@Composable
private fun ExpensesScreenHeader(
    amountExpense: MutableState<Float>,
    countExpense: MutableState<Int>
) {
    Card(elevation = CardElevation) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(all = PaddingDefault),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.fragment_expenses_text_title_how_much_i_spent),
                style = AppTypography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = stringResource(id = R.string.text_default_value_cipher, amountExpense.value),
                style = AppTypography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = stringResource(id = R.string.fragment_expenses_text_label, countExpense.value),
                style = AppTypography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.size(size = MarginDefault))
        }
    }
}

@Composable
@Preview("ExpenseScreen")
fun Preview() {
    ExpensesScreen(Modifier, rememberNavController())
}
