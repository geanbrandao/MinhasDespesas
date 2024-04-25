package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.data.repository.PAGE_SIZE
import dev.geanbrandao.minhasdespesas.domain.expensesWithCategoriesEntityToExpenses
import dev.geanbrandao.minhasdespesas.domain.filterByCategory
import dev.geanbrandao.minhasdespesas.domain.filterByDate
import dev.geanbrandao.minhasdespesas.domain.getFiltersDates
import dev.geanbrandao.minhasdespesas.domain.getSelectedCategoriesIds
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetExpensesBaseOnFiltersUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke(selectedFilters: List<SelectedFilter>, currentPage: Int) = flow<List<Expense>> {
        val (startDate, endDate) = selectedFilters.getFiltersDates()
        val selectedCategoriesIds = selectedFilters.getSelectedCategoriesIds()

        val response = repository.getExpenses(offset = PAGE_SIZE * currentPage)

        val result = response.filterByDate(startDate, endDate).filterByCategory(selectedCategoriesIds)
        println("GetExpensesBaseOnFiltersUseCase ${result.map { it.expense.name }}")
        emit(result.expensesWithCategoriesEntityToExpenses())
    }
}