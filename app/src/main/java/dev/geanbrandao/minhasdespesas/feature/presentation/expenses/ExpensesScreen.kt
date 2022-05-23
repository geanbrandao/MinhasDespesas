package dev.geanbrandao.minhasdespesas.feature.presentation.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.FiltersButton
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseWithCategoriesDb
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.components.ScreenWarningWithTitleMessage
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.components.ItemExpenseWithSwipe
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util.navigateForNavBar
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.CardElevation
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    viewModel: ExpensesViewModel = hiltViewModel(),
) {
    val stateExpenses = viewModel.stateExpenses.value
    val listFilters = remember {
//        mutableStateListOf("Filtro 1", "Filtro 2", "Filtro 3", "Filtro 4", "Filtro 5")
        mutableStateListOf<String>()
    }
//    val itemExpenseList = remember {
//        mutableStateListOf("Expense 1", "Expense 2", "Expense 3")
//    }

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
            Box(modifier = Modifier.fillMaxWidth()) {
                FiltersButton(
                    activeFiltersSize = listFilters.size,
                    modifier = Modifier.align(alignment = Alignment.CenterEnd)
                ) { navHostController.navigate(Screen.Filters.route) }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                ListExpenses(
                    itemExpenseList = stateExpenses.expenses,
                    navHostController = navHostController,
                    onSwipeToDelete = { expenseId ->

                    },
                    onSwipeToEdit = { expenseId ->

                    }
                )
                HandleDisplayWarningMessage(
                    modifier =  Modifier.align(alignment = Alignment.Center),
                    listExpensesIsEmpty = stateExpenses.expenses.isEmpty(),
                    listFiltersIsEmpty = listFilters.isEmpty()
                )
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    onClick = {
                        navHostController.navigate(Screen.Add.route)
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(end = PaddingDefault, bottom = PaddingDefault)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun HandleDisplayWarningMessage(
    listExpensesIsEmpty: Boolean,
    listFiltersIsEmpty: Boolean,
    modifier: Modifier = Modifier,
) {
    if (listExpensesIsEmpty and listFiltersIsEmpty) {
        ScreenWarningWithTitleMessage(
            modifier = modifier,
            emptyWarningTitle = stringResource(
                id = R.string.fragment_expenses_warning_title_no_expenses
            ),
            emptyWarningMessage = stringResource(
                id = R.string.fragment_expenses_warning_message_no_expenses
            )
        )
    }
    if (listExpensesIsEmpty and listFiltersIsEmpty.not()) {
        ScreenWarningWithTitleMessage(
            modifier = modifier,
            emptyWarningTitle = stringResource(
                id = R.string.fragment_expenses_warning_title_no_expenses_when_filter_selected
            ),
            emptyWarningMessage = stringResource(
                id = R.string.fragment_expenses_warning_message_no_expenses_when_filter_selected
            )
        )
    }
}

@Composable
private fun ListExpenses(
    itemExpenseList: List<ExpenseWithCategoriesDb>,
    navHostController: NavHostController,
    onSwipeToDelete: (id: Long) -> Unit,
    onSwipeToEdit: (id: Long) -> Unit,
) {
    LazyColumn {
        itemsIndexed(
            items = itemExpenseList,
            key = { _: Int, item: ExpenseWithCategoriesDb ->
                item.hashCode()
            }
        ) { index, item ->
            ItemExpenseWithSwipe(
                expenseDb = item.expenseDb,
                isLastItem = itemExpenseList.lastIndex == index,
                onSwipeToDelete = onSwipeToDelete,
                onSwipeToEdit = onSwipeToEdit,
            )
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
                text = stringResource(
                    id = R.string.fragment_expenses_text_label,
                    countExpense.value
                ),
                style = AppTypography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            SpacerTwo()
        }
    }
}

@Composable
@Preview("ExpenseScreen")
fun Preview() {
    ExpensesScreen(Modifier, rememberNavController())
}
