package dev.geanbrandao.minhasdespesas.feature.presentation.expenses

import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseWithCategoriesDb

data class ExpensesState(
    val expenses: List<ExpenseWithCategoriesDb> = emptyList(),
    val isLoading: Boolean = false,
)