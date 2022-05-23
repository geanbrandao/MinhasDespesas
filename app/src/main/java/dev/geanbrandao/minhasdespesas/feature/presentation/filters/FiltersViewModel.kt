package dev.geanbrandao.minhasdespesas.feature.presentation.filters

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.model.ActiveFiltersModel
import dev.geanbrandao.minhasdespesas.feature.domain.model.ActiveFiltersSimpleModel
import dev.geanbrandao.minhasdespesas.feature.domain.model.TypeFilterDate
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import dev.geanbrandao.minhasdespesas.feature.presentation.filters.states.FiltersState
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(FiltersState())
//    private val _stateSelectedCategories = mutableStateOf(ActiveFiltersModel())
    private val _stateSelectedCategories = mutableStateOf(emptyList<CategoryDb>())
    private val _stateSelectedDateType = mutableStateOf(TypeFilterDate.NONE)
    private val _stateActiveFilters = mutableStateOf(emptyList<ActiveFiltersSimpleModel>())

    val state: State<FiltersState> = _state
    val stateActiveFilters: State<List<ActiveFiltersSimpleModel>> = _stateActiveFilters

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
            _stateSelectedCategories.value = _stateSelectedCategories.value.plus(categoryDb)
//            _stateSelectedCategories.value = _stateSelectedCategories.value.copy(
//                selectedCategories = _stateSelectedCategories.value.selectedCategories
//                    .plus(element = categoryDb)
//            )
            _stateActiveFilters.value = _stateActiveFilters.value.plus(
                ActiveFiltersSimpleModel(categoryDb.name, categoryDb.categoryId)
            )
        } else {
            _stateSelectedCategories.value = _stateSelectedCategories.value.filter {
                it.categoryId != categoryDb.categoryId
            }
            _stateActiveFilters.value = _stateActiveFilters.value.filter {
                it.id != categoryDb.categoryId
            }
//            _stateSelectedCategories.value =
//                ActiveFiltersModel(
//                    typeFilterDate = _stateSelectedCategories.value.typeFilterDate,
//                    selectedCategories = _stateSelectedCategories.value.selectedCategories
//                        .filter { it.categoryId != categoryDb.categoryId }
//                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCategoriesJob?.cancel()
    }
}
