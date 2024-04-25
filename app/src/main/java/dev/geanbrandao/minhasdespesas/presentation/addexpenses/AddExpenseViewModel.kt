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
import dev.geanbrandao.minhasdespesas.domain.usecase.preferences.PreferencesUseCases
import dev.geanbrandao.minhasdespesas.navigation.data.AppNavigator
import dev.geanbrandao.minhasdespesas.navigation.domain.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.android.annotation.KoinViewModel

private const val STATE_UI_STATE = "addExpenseScreenUiState"
private const val STATE_CATEGORIES = "categoriesState"

@KoinViewModel
class AddExpenseViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
    private val preferencesUseCases: PreferencesUseCases,
    private val appNavigator: AppNavigator,
) : ViewModel() {

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

    private val expenseId = state.get<String>(Destination.Expense.EXPENSE_ID_KEY).also {
        getExpense(expenseId = it)
        println("DEBUG123 expenseId $it")
    }



    private val _insertedOrUpdated = Channel<Boolean>() // todo throw a navigationEvent
    val insertedOrUpdated = _insertedOrUpdated.receiveAsFlow()

    private val _navigateToCategories = Channel<Boolean>()
    val navigateToCategories = _navigateToCategories.receiveAsFlow()

    fun addExpense() {
        viewModelScope.launch {
            useCases.addExpense(createExpenseModel())
                .catch {
                    throw Exception(it)
                }.collect {
                    println("#### THIS IS MY EXPENSE ID $it")
                    navigateBack()
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
                    navigateBack()
                }
        }
    }

    fun getExpense(expenseId: String?) {
        expenseId
            // prevent expense from being reloaded when already get loaded
            // todo check if still need this logic
//            ?.takeIf { uiState.value.expenseId == 0L }
            ?.let {
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

    fun getSelectedCategoriesIds() {
        viewModelScope.launch {
            preferencesUseCases.getSelectedCategoriesIdsUseCase()
                .catch {
                    throw Exception(it)
                }.collect { result: List<Long> ->
                    updateSelectedCategories(result)
                }
        }
    }

    fun cleanSelectedCategoriesIds() {
        viewModelScope.launch {
            preferencesUseCases.setSelectedCategoriesIdsUseCase(emptyList())
                .catch {
                    throw Exception(it)
                }.collect {

                }
        }
    }

    private fun updateSelectedCategories(list: List<Long>) {
        viewModelScope.launch {
            useCases.getCategoriesUsingId(list)
                .catch {
                    throw Exception(it)
                }.collect {
                    state[STATE_UI_STATE] = uiState.value.copy(selectedCategories = it)
                }
        }
    }

    fun navigateBack() = appNavigator.tryNavigateBack()

    fun navigateToCategoriesScreen() {
        viewModelScope.launch {
            val list = uiState.value.selectedCategories.map { it.categoryId }
            preferencesUseCases.setSelectedCategoriesIdsUseCase(list)
                .catch {
                    throw Exception(it)
                }.collect {
                    appNavigator.navigateTo(Destination.Categories())
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
