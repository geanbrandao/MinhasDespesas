package dev.geanbrandao.minhasdespesas.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import dev.geanbrandao.minhasdespesas.domain.usecase.PreferencesUseCases
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_EXPENSE = "expenses"
private const val KEY_SELECTED_FILTERS = "selectedFilters"

@KoinViewModel
class HomeViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
    private val preferencesUseCases: PreferencesUseCases,
//    @Named("XPTO") private val dependency: String,
): ViewModel() {

    val expenses = state.getStateFlow<List<Expense>>(key = KEY_EXPENSE, initialValue = emptyList())

    val selectedFilters = state.getStateFlow<List<SelectedFilter>>(key = KEY_SELECTED_FILTERS, initialValue = emptyList())

    init {
        getExpenses()
    }

    fun getExpenses() {
        viewModelScope.launch {
            useCases.getExpenses()
                .catch {
                    throw Exception(it)
                }
                .collect {

                    state[KEY_EXPENSE] = it
                }
        }
    }

    fun getFilterExpenses() {

    }

    fun getSelectedFilters() {
        viewModelScope.launch {
            preferencesUseCases.getSelectedFiltersUseCase()
                .catch {
                    throw Exception(it)
                }.collect { result: List<SelectedFilter> ->
                    state[KEY_SELECTED_FILTERS] = result
                }

        }
    }

    fun setSelectedFilter(argument: List<SelectedFilter>) {
        viewModelScope.launch {
            preferencesUseCases.setSelectedFiltersUseCase(argument)
        }
    }

    fun removeExpense(expenseId: Long) {
        viewModelScope.launch {
            useCases.removeExpense(expenseId = expenseId)
                .catch {
                    throw Exception(it)
                }.collect { isRemoved ->
                    if (isRemoved) {
                        getExpenses()
                    }
                }
        }
    }
}