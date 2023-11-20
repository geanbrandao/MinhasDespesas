package dev.geanbrandao.minhasdespesas.presentation.addexpenses

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.geanbrandao.common.domain.MoneyUtils.toCommaString
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import br.dev.geanbrandao.common.domain.isValidDate
import br.dev.geanbrandao.common.domain.toFloat
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.android.annotation.KoinViewModel

private const val STATE_UI_STATE = "addExpenseScreenUiState"
private const val STATE_CATEGORIES = "categoriesState"

//private const val STATE_ARGUMENT = "argument"

@KoinViewModel
class AddExpenseViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
) : ViewModel() {

    private val _insertedOrUpdated = Channel<Boolean>() // todo throw a navigationEvent
    val insertedOrUpdated = _insertedOrUpdated.receiveAsFlow()

    val uiState = state.getStateFlow(
        key = STATE_UI_STATE,
        initialValue = AddExpenseScreenState(
            amount = "0,00",
            name = "",
            selectedDate = getCurrentTimeInMillis(),
            description = "",
            selectedCategories = emptyList(),
            createdAt = getCurrentTimeInMillis(),
            expenseId = 0,
        )
    )

    fun addExpense() {
        viewModelScope.launch {
            useCases.addExpense(createExpenseModel())
                .catch {
                    throw Exception(it)
                }.collect {
                    println("#### THIS IS MY EXPENSE ID $it")
                    _insertedOrUpdated.send(true)
                }
        }
    }

    fun updateExpense() {
        viewModelScope.launch {
            useCases.updateExpense(createExpenseModel())
                .catch {
                    throw Exception(it)
                }.collect {
                    println("#### THIS IS MY EXPENSE ID $it")
                    _insertedOrUpdated.send(true)
                }
        }
    }

    fun getExpense(expenseId: String?) {
        expenseId?.let {
            viewModelScope.launch {
                useCases.getExpense(it.toLong())
                    .catch {
                        throw Exception(it)
                    }.collect {
                        state[STATE_UI_STATE] = AddExpenseScreenState(
                            amount = it.amount.toCommaString(),
                            name = it.name,
                            selectedDate = it.selectedDate,
                            description = it.description,
                            selectedCategories = it.categories,
                            createdAt = it.createdAt,
                            isEdit = true,
                            expenseId = it.expenseId
                        )
                    }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            useCases.getCategories()
                .catch {
                    throw Exception(it)
                }.collect {
                    state[STATE_CATEGORIES] = it
                }
        }
    }

    fun updateUiState(uiState: AddExpenseScreenState) {
        state[STATE_UI_STATE] = uiState
    }

    fun updateSelectedCategories(argument: String?) {
        viewModelScope.launch {
            val ids = argument?.split(",")?.map { id -> id.toLong() }.orEmpty()
            useCases.getCategoriesUsingId(ids)
                .catch {
                    throw Exception(it)
                }.collect {
                    state[STATE_UI_STATE] = uiState.value.copy(selectedCategories = it)
                }

        }
    }

    private fun createExpenseModel() = Expense(
        expenseId = uiState.value.expenseId,
        name = uiState.value.name,
        amount = uiState.value.amount.toFloat(),
        selectedDate = uiState.value.selectedDate,
        description = uiState.value.description,
        categories = uiState.value.selectedCategories,
        createdAt = uiState.value.createdAt,
        updatedAt = getCurrentTimeInMillis(),
    )
}

@Parcelize
data class AddExpenseScreenState(
    val amount: String,
    val name: String,
    val selectedDate: Long,
    val description: String,
    val selectedCategories: List<Category>,
    val expenseId: Long,
    val createdAt: Long,
    val isEdit: Boolean = false,
) : Parcelable {
    val isNextButtonEnabled : Boolean
        get() = amount != "0,00" && name.isNotEmpty() && selectedDate.isValidDate()
}

// get from navigation
//    private val argSelectedCategories = state.getStateFlow<String?>(key = Key.SELECTED_CATEGORIES, null)
//sealed class OperationState<out T> {
//    data class Loading(val isLoading: Boolean): OperationState<Nothing>()
//    data class Success<T>(val data: T): OperationState<T>()
//    data class Error(val message: String = "", val exception: Throwable): OperationState<Nothing>()
//}