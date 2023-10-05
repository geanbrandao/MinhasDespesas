package dev.geanbrandao.minhasdespesas.presentation.addexpenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Named


private const val KEY_EXPENSE = "expenses"
private const val KEY_CATEGORY = "category"

@KoinViewModel
class AddExpenseViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
) : ViewModel() {
    val expenses = state.getStateFlow<List<Expense>>(key = KEY_EXPENSE, initialValue = emptyList())
    val categories = state.getStateFlow<List<Category>>(key = KEY_CATEGORY, initialValue = emptyList())
//    fun getExpenses() {
//        viewModelScope.launch {
//            useCases.getExpenses()
//                .catch {
//                    throw Exception(it)
//                }
//                .collect {
//                    state[KEY_EXPENSE] = it
//                }
//        }
//    }

    init {
        getCategories()
    }

    fun getCategories() {

        viewModelScope.launch {
            useCases.getCategories()
                .catch {
                    throw Exception(it)
                }.collect {
                    state[KEY_CATEGORY] = it
                }
        }
    }
}