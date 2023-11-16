package dev.geanbrandao.minhasdespesas.domain.usecase

import androidx.annotation.VisibleForTesting
import br.dev.geanbrandao.common.domain.getOffsetDateTime
import br.dev.geanbrandao.common.domain.isBetweenDates
import br.dev.geanbrandao.common.domain.isNotNull
import br.dev.geanbrandao.common.domain.isNull
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories
import dev.geanbrandao.minhasdespesas.data.repository.PAGE_SIZE
import dev.geanbrandao.minhasdespesas.domain.expensesWithCategoriesEntityToExpenses
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import java.time.OffsetDateTime
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetExpensesBaseOnFiltersUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke(selectedFilters: List<SelectedFilter>, currentPage: Int) = flow<List<Expense>> {
        println("GetExpensesBaseOnFiltersUseCase selectedFilters ${selectedFilters.size}")
        val (startDate, endDate) = selectedFilters.firstOrNull { it.date.isNotNull() }?.let {
            Pair(it.date?.startDate?.getOffsetDateTime(), it.date?.endDate?.getOffsetDateTime())
        } ?: Pair(null, null)
        val selectedCategoriesIds = selectedFilters.filter { it.date.isNull() }
            .map { it.category!!.categoryId }
            .takeIf { it.isNotEmpty() }.orEmpty()
        println("GetExpensesBaseOnFiltersUseCase selectedCategoriesIds ${selectedCategoriesIds.size}")

        val response = repository.getExpenses(offset = PAGE_SIZE * currentPage)

        val result = response.filterByDate(startDate, endDate).filterByCategory(selectedCategoriesIds)
        println("GetExpensesBaseOnFiltersUseCase ${result.map { it.expense.name }}")
        emit(result.expensesWithCategoriesEntityToExpenses())
    }

    @VisibleForTesting
    private fun List<ExpensesWithCategories>.filterByDate(
        startDate: OffsetDateTime?,
        endDate: OffsetDateTime?,
    ): List<ExpensesWithCategories> {
        return this.takeIf { startDate.isNotNull() and endDate.isNotNull() }?.filter {
                it.expense.selectedDate.isBetweenDates(startDate, endDate)
        } ?: this
    }

    @VisibleForTesting
    private fun List<ExpensesWithCategories>.filterByCategory(
        selectedCategoriesIds: List<Long>
    ): List<ExpensesWithCategories> {
        return this.takeIf { selectedCategoriesIds.isNotEmpty() }?.filter {
            it.categories.existsInSelectedCategoriesIds(selectedCategoriesIds)
        } ?: this
    }

    @VisibleForTesting
    private fun List<CategoryEntity>.existsInSelectedCategoriesIds(selectedCategoriesIds: List<Long>): Boolean {
        return this.map { it.categoryId }.any { it in selectedCategoriesIds }
    }
}