package dev.geanbrandao.minhasdespesas.presentation.charts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.CategoryChartModel
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_CHART_DATA = "chartData"
private const val KEY_SELECTED_FILTERS = "selectedFilters"

@KoinViewModel
class ChartsViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
) : ViewModel() {

    val chartData = state.getStateFlow<List<CategoryChartModel>>(key = KEY_CHART_DATA, initialValue = emptyList())
    val selectedFilters = state.getStateFlow<List<SelectedFilter>>(key = KEY_SELECTED_FILTERS, initialValue = emptyList())

    init {
        getChartData()
    }

    fun getChartData() {
        viewModelScope.launch {
            useCases.getCategoriesChartData(selectedFilters = emptyList())
                .catch {
                    throw Exception(it)
                }
                .collect {
                    state[KEY_CHART_DATA] = it
                }
        }
    }
}