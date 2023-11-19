package dev.geanbrandao.minhasdespesas.domain

import br.dev.geanbrandao.common.domain.getOffsetDateTime
import br.dev.geanbrandao.common.domain.isBetweenDates
import br.dev.geanbrandao.common.domain.isNotNull
import br.dev.geanbrandao.common.domain.isNull
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories
import dev.geanbrandao.minhasdespesas.domain.model.CategoryChartModel
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import java.time.OffsetDateTime

fun List<ExpensesWithCategories>.filterByDate(
    startDate: OffsetDateTime?,
    endDate: OffsetDateTime?,
): List<ExpensesWithCategories> {
    return this.takeIf { startDate.isNotNull() and endDate.isNotNull() }?.filter {
        it.expense.selectedDate.isBetweenDates(startDate, endDate)
    } ?: this
}

fun List<ExpensesWithCategories>.filterByCategory(
    selectedCategoriesIds: List<Long>
): List<ExpensesWithCategories> {
    return this.takeIf { selectedCategoriesIds.isNotEmpty() }?.filter {
        it.categories.existsInSelectedCategoriesIds(selectedCategoriesIds)
    } ?: this
}

fun List<CategoryEntity>.existsInSelectedCategoriesIds(selectedCategoriesIds: List<Long>): Boolean {
    return this.map { it.categoryId }.any { it in selectedCategoriesIds }
}

typealias FilterDates = Pair<OffsetDateTime?, OffsetDateTime?>

fun List<SelectedFilter>.getFiltersDates() =
    this.firstOrNull { it.date.isNotNull() }?.let {
        Pair(it.date?.startDate?.getOffsetDateTime(), it.date?.endDate?.getOffsetDateTime())
    } ?: Pair(null, null)

fun List<SelectedFilter>.getSelectedCategoriesIds() =
    this.filter { it.date.isNull() }
        .map { it.category!!.categoryId }
        .takeIf { it.isNotEmpty() }.orEmpty()

fun List<ExpensesWithCategories>.calculateChartData() : List<CategoryChartModel> {
    val response: MutableMap<Long, CategoryChartModel> = mutableMapOf()

    for (expense in this) {
        val categories = expense.categories
        for (category in categories) {
            val categoryId = category.categoryId
            val categoryChartModel = response.getOrPut(
                key = categoryId
            ) {
                CategoryChartModel(
                    categoryId = categoryId,
                    icon = category.icon,
                    label = category.name,
                    amount = 0f,
                )
            }
            response[categoryId] = categoryChartModel.copy(amount = categoryChartModel.amount + expense.expense.amount)
        }
    }

    return response.map { it.value }.sortedByDescending { it.amount }
}