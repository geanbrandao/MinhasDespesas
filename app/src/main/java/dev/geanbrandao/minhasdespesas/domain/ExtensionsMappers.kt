package dev.geanbrandao.minhasdespesas.domain

import br.dev.geanbrandao.common.domain.getLongTimeMillis
import br.dev.geanbrandao.common.domain.getOffsetDateTime
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.model.Expense

fun Expense.convertTo() = ExpenseEntity(
    expenseId = expenseId,
    amount = amount,
    name = name,
    selectedDate = selectedDate.getOffsetDateTime(),
    description = description,
    createdAt = createdAt.getOffsetDateTime(),
    updatedAt = updatedAt.getOffsetDateTime(),
)

fun Category.convertTo() = CategoryEntity(
    categoryId = categoryId,
    name = name,
    icon = icon,
    canRemove = canRemove,
)

fun List<Category>.convertTo() = this.map {
    it.convertTo()
}

fun CategoryEntity.convertTo() = Category(
    categoryId = categoryId,
    name = name,
    icon = icon,
    canRemove = canRemove,
)

fun ExpensesWithCategories.convertTo(categories: List<Category>) = Expense(
    expenseId = expense.expenseId,
    amount = expense.amount,
    name = expense.name,
    selectedDate = expense.selectedDate.getLongTimeMillis(),
    description = expense.description,
    categories = categories,
    createdAt = expense.createdAt.getLongTimeMillis(),
    updatedAt = expense.updatedAt.getLongTimeMillis(),
)
