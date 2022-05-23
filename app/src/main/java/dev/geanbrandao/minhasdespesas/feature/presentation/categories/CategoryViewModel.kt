package dev.geanbrandao.minhasdespesas.feature.presentation.categories

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
) : ViewModel() {
    private val _state = mutableStateOf(CategoryState())
    private val _stateSelectedCategories = mutableStateOf(emptyList<CategoryDb>())

    val state: State<CategoryState> = _state

    private var getCategoriesJob: Job? = null

    init {
        getCategories()
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
        getCategoriesJob = expenseUseCases.getCategories()
            .onEach { list ->
                _state.value = _state.value.copy(dataList = list)
            }.catch {
                // error
                Log.d("HELOO", "")
            }.launchIn(viewModelScope)
    }

    fun onCategoryCheckChange(isChecked: Boolean, categoryDb: CategoryDb) {
        if (isChecked) {
            _stateSelectedCategories.value = _stateSelectedCategories.value.plus(element = categoryDb)
        } else {
            _stateSelectedCategories.value = _stateSelectedCategories.value.filter {
                it.categoryId != categoryDb.categoryId
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCategoriesJob?.cancel()
    }
}