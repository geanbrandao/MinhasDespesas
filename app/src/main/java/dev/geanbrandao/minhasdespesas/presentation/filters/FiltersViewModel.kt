package dev.geanbrandao.minhasdespesas.presentation.filters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import dev.geanbrandao.minhasdespesas.domain.usecase.PreferencesUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_CATEGORIES = "category"
private const val KEY_FILTER_DATE = "filterDate"
private const val KEY_SELECTED_FILTERS = "selectedFilters"

@KoinViewModel
class FiltersViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
    private val preferencesUseCases: PreferencesUseCases,
) : ViewModel() {

    val categories =
        state.getStateFlow<List<Category>>(key = KEY_CATEGORIES, initialValue = emptyList())
    private val filterDate =
        state.getStateFlow<FilterDate?>(key = KEY_FILTER_DATE, initialValue = null)


    private val _onFiltersApply = MutableStateFlow(false)
    val onFiltersApply = _onFiltersApply.asStateFlow() // change this to channel flow, maybe is a better approach

    val selectedFilters: Flow<List<SelectedFilter>> = combine(categories, filterDate) { arg1: List<Category>, arg2: FilterDate? ->
        val filtersWithOnlyCategories = arg1.filter { it.isChecked }.map {
            SelectedFilter(category = it)
        }
        val filterWithOnlyDate = arg2?.let { SelectedFilter(date = it) }
        filterWithOnlyDate?.let {
            listOf(it).plus(filtersWithOnlyCategories)
        } ?: filtersWithOnlyCategories
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList<SelectedFilter>())

    init {
        getCategories()

    }

    private fun getCategories() {
        viewModelScope.launch {
            useCases.getCategories()
                .catch {
                    throw Exception(it)
                }.collect {
//                    val idList = argSelectedCategories.value?.split(",")?.map { id -> id.toLong() }.orEmpty()
//                    val result = it.map { category: Category ->
//                        if (category.categoryId in idList) {
//                            category.copy(isChecked = true)
//                        } else {
//                            category
//                        }
//                    }
                    state[KEY_CATEGORIES] = it
                    getSelectedFilters()
                }
        }
    }

    fun updateFilterDate(filterDate: FilterDate?) {
        state[KEY_FILTER_DATE] = filterDate
    }

    fun setSelectedFilters(argument: List<SelectedFilter>) {
        viewModelScope.launch {
            preferencesUseCases.setSelectedFiltersUseCase(argument)
            _onFiltersApply.value = true

        }
    }

    fun setSelectedFilters() {
        viewModelScope.launch {
            preferencesUseCases.setSelectedFiltersUseCase(emptyList())
            updateFilterDate(
                filterDate = FilterDate(
                    startDate = null,
                    endDate = null,
                    label = null,
                    type = FilterByDateEnum.ALL
                )
            )
            removeSelectedCategories()
        }
    }

    private fun getSelectedFilters() {
        viewModelScope.launch {
            preferencesUseCases.getSelectedFiltersUseCase()
                .catch {
                    throw Exception(it)
                }.collect { result: List<SelectedFilter> ->
                    result.forEach { selectedFilter: SelectedFilter ->
                        selectedFilter.category?.let {
                            updateSelectedCategories(it.categoryId, true)
                        }
                        selectedFilter.date?.let {
                            updateFilterDate(it)
                        }
                    }
                }

        }
    }

    fun updateSelectedCategories(categoryId: Long, isChecked: Boolean) {
        state[KEY_CATEGORIES] = categories.value.map { category ->
            if (categoryId == category.categoryId) {
                category.copy(isChecked = isChecked)
            } else {
                category
            }
        }
    }

    fun removeSelectedCategories() {
        state[KEY_CATEGORIES] = categories.value.map { category ->
            category.copy(isChecked = false)
        }
    }

}