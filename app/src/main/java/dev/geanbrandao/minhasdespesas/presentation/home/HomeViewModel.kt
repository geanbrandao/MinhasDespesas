package dev.geanbrandao.minhasdespesas.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_EXPENSE = "expenses"

@KoinViewModel
class HomeViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
//    @Named("XPTO") private val dependency: String,
): ViewModel() {

    val expenses = state.getStateFlow<List<Expense>>(key = KEY_EXPENSE, initialValue = emptyList())

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
}