package dev.geanbrandao.minhasdespesas.feature.presentation.expenses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseWithCategoriesDb
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expensesUseCases: ExpenseUseCases,
): ViewModel() {

    private val _state = mutableStateOf(ExpensesState())
    val state: State<ExpensesState> = _state

    private var getExpensesJob: Job? = null

    init {
        getExpenses()
    }

    private fun getExpenses() {
        getExpensesJob?.cancel()
        getExpensesJob = expensesUseCases.getExpenses()
            .onEach { list: List<ExpenseWithCategoriesDb> ->
                _state.value = state.value.copy(expenses = list)
            }.catch {
                // do something
            }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        getExpensesJob?.cancel()
    }
}