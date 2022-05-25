package dev.geanbrandao.minhasdespesas.feature.presentation.filters

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.geanbrandao.minhasdespesas.common.utils.extensions.endOfDay
import dev.geanbrandao.minhasdespesas.common.utils.extensions.endOfMonth
import dev.geanbrandao.minhasdespesas.common.utils.extensions.startOfDay
import dev.geanbrandao.minhasdespesas.common.utils.extensions.startOfMonth
import dev.geanbrandao.minhasdespesas.common.utils.extensions.toStringFilter
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.ALL
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.CURRENT_MONTH
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.MONTH
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.NONE
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.PICKED_DATE
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.RANGE_DATE
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.WEEK
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.YEAR
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterEnum.CATEGORY
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterEnum.DATE
import dev.geanbrandao.minhasdespesas.feature.domain.model.ActiveFiltersSimpleModel
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import dev.geanbrandao.minhasdespesas.feature.presentation.filters.states.FiltersState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(FiltersState())

    //    private val _stateSelectedCategories = mutableStateOf(ActiveFiltersModel())
    private val _stateSelectedCategories = mutableStateOf(emptyList<CategoryDb>())
    private val _stateSelectedDateType = mutableStateOf(CURRENT_MONTH)
    private val _stateActiveFilters = mutableStateOf(emptyList<ActiveFiltersSimpleModel>())
    private val _pickedDate = mutableStateOf(OffsetDateTime.now())
    private val _pickedInitialDate = mutableStateOf(OffsetDateTime.now())
    private val _pickedFinalDate = mutableStateOf(OffsetDateTime.now())

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
                ActiveFiltersSimpleModel(CATEGORY, null, categoryDb)
            )
        } else {
            _stateSelectedCategories.value = _stateSelectedCategories.value.filter {
                it.categoryId != categoryDb.categoryId
            }
            _stateActiveFilters.value = _stateActiveFilters.value.filter {
                it.categoryDb != categoryDb
            }
//            _stateSelectedCategories.value =
//                ActiveFiltersModel(
//                    typeFilterDate = _stateSelectedCategories.value.typeFilterDate,
//                    selectedCategories = _stateSelectedCategories.value.selectedCategories
//                        .filter { it.categoryId != categoryDb.categoryId }
//                )
        }
    }

    private fun getDateIntervalBasedOnFilter(): Pair<OffsetDateTime?, OffsetDateTime?> {
        return when (_stateSelectedDateType.value) {
            ALL -> {
                val sameDate = OffsetDateTime.now()
                Pair(
                    first = sameDate,
                    second = sameDate,
                )
            }
            WEEK -> {
                Pair(
                    first = OffsetDateTime.now().minusDays(7).startOfDay(),
                    second = OffsetDateTime.now().endOfDay(),
                )
            }
            MONTH -> {
                Pair(
                    first = OffsetDateTime.now().minusDays(30).startOfDay(),
                    second = OffsetDateTime.now().endOfDay(),
                )
            }
            YEAR -> {
                Pair(
                    first = OffsetDateTime.now().minusMonths(12).startOfDay(),
                    second = OffsetDateTime.now().endOfDay(),
                )
            }
            PICKED_DATE -> {
                Pair(
                    first = _pickedDate.value.startOfDay(),
                    second = _pickedDate.value.endOfDay(),
                )
            }
            RANGE_DATE -> {
                Pair(
                    first = _pickedInitialDate.value.startOfDay(),
                    second = _pickedFinalDate.value.endOfDay()
                )
            }
            CURRENT_MONTH -> {
                Pair(
                    first = OffsetDateTime.now().startOfMonth().startOfDay(),
                    second = OffsetDateTime.now().endOfMonth().endOfDay(),
                )
            }
            NONE -> {
                Pair(
                    first = null,
                    second = null,
                )
            }
        }
    }

    fun setActiveFilterByDate(typeFilterDate: TypeFilterDateEnum) {
        _stateSelectedDateType.value = NONE
        _stateSelectedDateType.value = typeFilterDate
        _stateActiveFilters.value = _stateActiveFilters.value.filter {
            it.typeFilter != DATE
        }
        _stateActiveFilters.value = _stateActiveFilters.value.plus(
            ActiveFiltersSimpleModel(DATE, typeFilterDate.toStringFilter(), null)
        )
    }

    override fun onCleared() {
        super.onCleared()
        getCategoriesJob?.cancel()
    }
}
