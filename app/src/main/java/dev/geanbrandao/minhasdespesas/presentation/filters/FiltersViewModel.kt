package dev.geanbrandao.minhasdespesas.presentation.filters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_CATEGORIES = "category"
private const val KEY_FILTER_DATE = "filterDate"

@KoinViewModel
class FiltersViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
) : ViewModel() {

    val categories = state.getStateFlow<List<Category>>(key = KEY_CATEGORIES, initialValue = emptyList())

    private val filterDate = state.getStateFlow<FilterDate?>(key = KEY_FILTER_DATE, initialValue = null)

    val selectedFilters: Flow<List<SelectedFilter>> =
        combine(categories, filterDate) { arg1: List<Category>, arg2: FilterDate? ->
            val filtersWithOnlyCategories = arg1.filter { it.isChecked }.map {
                SelectedFilter.CategoryType(it.name, it)
            }
            val filterWithOnlyDate = arg2?.let { SelectedFilter.DateType(label = it.label.orEmpty(), date = it) }
            filterWithOnlyDate?.let {
                listOf(it).plus(filtersWithOnlyCategories)
            } ?: filtersWithOnlyCategories
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList<SelectedFilter>())

    init {
        getCategories()
    }

    fun getCategories() {
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
                }
        }
    }

    fun updateFilterDate(filterDate: FilterDate?) {
        state[KEY_FILTER_DATE] = filterDate
    }

    fun updateSelectedCategories(categoryId: Long, isChecked: Boolean) {
        state[KEY_CATEGORIES] = categories.value.map { category ->
            if(categoryId == category.categoryId ) {
                category.copy(isChecked = isChecked)
            } else {
                category
            }
        }
//        state[KEY_FILTER_DATE] = categories.value.filter { it.isChecked }
    }
}