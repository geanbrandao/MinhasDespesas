package dev.geanbrandao.minhasdespesas.presentation.home

import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import br.dev.geanbrandao.common.domain.MoneyUtils.formatToBrl
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.components.button.ActionButtonView
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.BgSwipeToDelete
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.BgSwipeToEdit
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.DragAnchors
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.RevealState
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.SwipeBothSides
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.SwipeLeft
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.SwipeRight
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.SwipeToReveal
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.localpreferences.domain.SWIPE_BOTH
import dev.geanbrandao.minhasdespesas.localpreferences.domain.SWIPE_LEFT
import dev.geanbrandao.minhasdespesas.localpreferences.domain.SWIPE_RIGHT
import dev.geanbrandao.minhasdespesas.presentation.filters.FilterByDateEnum
import dev.geanbrandao.minhasdespesas.presentation.filters.FilterDate
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFiltersView
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.MarginFour
import org.koin.androidx.compose.koinViewModel

// todo fazer todas as telas que carregam dados usarem lauchedEffect com lifecycle
// todo BUG - Não está atualizando as categorias ao tentar editar uma despesa
//todo BUG na hora de editar as categorias selecionadas da despesa
// todo ver como estão sendo ordenas as despeas na home
// todo depois de criar categoria não ta fecha o teclado
// todo por algum motivo ta colocando caps na primera letra de todas as palavras na descrição e no nome da despesa
// todo procurar se seria possivel o viewModel contralar os eventos de navegação
/**
 *
 * podia ter uma viewModel base e dentro dele ficar a lógica de navegação
 * então a cada ação do usuário chamaria um metodo dispatch event com algum identificador
 * para informar ao viewModel base, como construir a rota de navegação e devolver o evento em um channel
 */
@Composable
fun HomeScreen(
    openEditExpenseScreen: (expenseId: Long) -> Unit,
    openFiltersScreen: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val expenses = viewModel.expenses.collectAsState()
    val selectedFilters = viewModel.selectedFilters.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val swipeDirection = viewModel.swipeDirection.collectAsState()

    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getSelectedFilters()
            viewModel.getSwipeDirection()
        }
    }
    LaunchedEffect(selectedFilters.value, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getExpenses()
        }
    }
    // todo transform in state
    HomeScreenView(
        selectedFilters = selectedFilters.value,
        expenses = expenses.value,
        swipeDirection = swipeDirection.value,
        onCleanFilters = {
            viewModel.setSelectedFilter(emptyList())
        },
        onDeleteClicked = { expenseId: Long ->
            viewModel.removeExpense(expenseId)
        },
        onEditClicked = openEditExpenseScreen,
        onFilterButtonClicked = openFiltersScreen,
    )
}

@Composable
private fun HomeScreenView(
    selectedFilters: List<SelectedFilter>,
    expenses: List<Expense>,
    swipeDirection: String,
    onCleanFilters: () -> Unit,
    onDeleteClicked: (id: Long) -> Unit,
    onEditClicked: (id: Long) -> Unit,
    onFilterButtonClicked: () -> Unit,
) {
    Box {
        BaseScreen(
            modifier = Modifier.fillMaxSize(),
            header = {
                HeaderExpenses(
                    itemsQuantity = expenses.size,
                    itemsAmount = expenses.map { it.amount }.sum()
                )
            },
            content = {
                if (selectedFilters.isNotEmpty()) {
                    Spacer(modifier = Modifier.size(size = PaddingTwo))
                    SelectedFiltersView(
                        list = selectedFilters,
                        onCleanFilters = onCleanFilters,
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = MarginFour),
                ) {
                    item {
                        Spacer(modifier = Modifier.size(size = PaddingOne))
                    }
                    itemsIndexed(
                        items = expenses,
                        key = { _, item ->
                            item.expenseId
                        }
                    ) { index, item ->
                        HandleSwipe(
                            swipeDirection = swipeDirection,
                            item = item,
                            isLastItem = index == expenses.lastIndex,
                            onDeleteClicked = onDeleteClicked,
                            onEditClicked = onEditClicked,
                        )
                    }
                }
            }
        )
        ActionButtonView(
            icon = painterResource(id = R.drawable.ic_filters),
            onClick = onFilterButtonClicked,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        )
    }
}

@Composable
private fun HandleSwipe(
    swipeDirection: String,
    isLastItem: Boolean,
    onDeleteClicked: (id: Long) -> Unit,
    onEditClicked: (id: Long) -> Unit,
    item: Expense,
) {
    when(swipeDirection) {
        SWIPE_BOTH -> {
            SwipeBothSides(
                onDeleteClicked = {
                  onDeleteClicked(item.expenseId)
                },
                onEditClicked = {
                    onEditClicked(item.expenseId)
                },
                content = {
                    ItemExpenseView(item = item, isLastItem = isLastItem)
                }
            )
        }
        SWIPE_RIGHT -> {
            SwipeRight(
                onDeleteClicked = {
                    onDeleteClicked(item.expenseId)
                },
                onEditClicked = {
                    onEditClicked(item.expenseId)
                },
                content = {
                    ItemExpenseView(item = item, isLastItem = isLastItem)
                }
            )
        }
        SWIPE_LEFT -> {
            SwipeLeft(
                onDeleteClicked = {
                    onDeleteClicked(item.expenseId)
                },
                onEditClicked = {
                    onEditClicked(item.expenseId)
                },
                content = {
                    ItemExpenseView(item = item, isLastItem = isLastItem)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeTo(
    item: Expense,
    isLastItem: Boolean,
    onDeleteClicked: (id: Long) -> Unit,
    onEditClicked: (id: Long) -> Unit,
) {
    val density = LocalDensity.current
    val maxSwipeWidth = with(density) { 56.dp.toPx() }
    val state = remember {
        RevealState(
            initialValue = DragAnchors.Default,
            positionalThreshold = { distance: Float -> distance * 0.8f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    SwipeToReveal(
        state = state,
        maxSwipeWidth = maxSwipeWidth,
        background = {
            if (state.dismissDirection == DragAnchors.Start) {
                BgSwipeToEdit {
                    onEditClicked(item.expenseId)
                }
            } else if (state.dismissDirection == DragAnchors.End) {
                BgSwipeToDelete {
                    onDeleteClicked(item.expenseId)
                }
            }
        },
        dismissContent = {
            ItemExpenseView(item = item, isLastItem = isLastItem)
        },
    )
}

@Composable
private fun HeaderExpenses(
    itemsQuantity: Int,
    itemsAmount: Float,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(all = PaddingTwo),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.fragment_expenses_text_title_how_much_i_spent),
            style = AppTypography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = itemsAmount.formatToBrl(),
            style = AppTypography.headlineLarge,
            fontWeight = FontWeight.Black,
        )
        Text(
            text = stringResource(id = R.string.fragment_expenses_text_label, itemsQuantity),
            style = AppTypography.bodySmall,
            fontWeight = FontWeight.Light,
        )
    }

}

@Preview
@Composable
private fun HomeScreenViewPreview() {
    val list = remember {
        mutableStateListOf(
            Expense(
                expenseId = 0,
                amount = 23.5f,
                name = "Despesa 1",
                selectedDate = getCurrentTimeInMillis(),
                description = "descricao",
                categories = listOf(),
                createdAt = getCurrentTimeInMillis(),
                updatedAt = getCurrentTimeInMillis()
            ),
            Expense(
                expenseId = 1,
                amount = 23.5f,
                name = "Despesa 2",
                selectedDate = getCurrentTimeInMillis(),
                description = "descricao",
                categories = listOf(),
                createdAt = getCurrentTimeInMillis(),
                updatedAt = getCurrentTimeInMillis()
            ),
            Expense(
                expenseId = 2,
                amount = 23.5f,
                name = "Despesa 3",
                selectedDate = getCurrentTimeInMillis(),
                description = "descricao",
                categories = listOf(),
                createdAt = getCurrentTimeInMillis(),
                updatedAt = getCurrentTimeInMillis()
            ),
            Expense(
                expenseId = 3,
                amount = 23.5f,
                name = "Despesa 4",
                selectedDate = getCurrentTimeInMillis(),
                description = "descricao",
                categories = listOf(),
                createdAt = getCurrentTimeInMillis(),
                updatedAt = getCurrentTimeInMillis()
            ),
            Expense(
                expenseId = 4,
                amount = 23.5f,
                name = "Despesa 5",
                selectedDate = getCurrentTimeInMillis(),
                description = "descricao",
                categories = listOf(),
                createdAt = getCurrentTimeInMillis(),
                updatedAt = getCurrentTimeInMillis()
            ),
        )
    }
    val selectedFilters = listOf(
        SelectedFilter(
            category = null,
            date = FilterDate(0L, null, "", FilterByDateEnum.YEAR),
        ),
        SelectedFilter(
            date = null,
            category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
        )
    )
    HomeScreenView(
        selectedFilters = selectedFilters,
        expenses = list,
        swipeDirection = "both",
        onCleanFilters = {},
        onDeleteClicked = { id ->
            list.removeIf { it.expenseId == id }
        },
        onEditClicked = {

        },
        onFilterButtonClicked = {},
    )
}