package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.domain.calculateChartData
import dev.geanbrandao.minhasdespesas.domain.filterByCategory
import dev.geanbrandao.minhasdespesas.domain.filterByDate
import dev.geanbrandao.minhasdespesas.domain.getFiltersDates
import dev.geanbrandao.minhasdespesas.domain.getSelectedCategoriesIds
import dev.geanbrandao.minhasdespesas.domain.model.CategoryChartModel
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetCategoriesChartDataUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke(selectedFilters: List<SelectedFilter>) = flow<List<CategoryChartModel>> {
        val (startDate, endDate) = selectedFilters.getFiltersDates()
        val selectedCategoriesIds = selectedFilters.getSelectedCategoriesIds()

        val response = repository.getExpenses()

        val result = response.filterByDate(startDate, endDate).filterByCategory(selectedCategoriesIds)
        emit(result.calculateChartData())
    }
}