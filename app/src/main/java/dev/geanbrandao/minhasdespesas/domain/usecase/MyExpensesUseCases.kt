package dev.geanbrandao.minhasdespesas.domain.usecase

import org.koin.core.annotation.Single

@Single
data class MyExpensesUseCases(
    val addExpense: AddExpenseUseCase,
    val getExpenses: GetExpensesUseCase,
    val getCategories: GetCategoriesUseCase,
    val addCategories: AddCategoriesUseCase,
    val addCategory: AddCategoryUseCase,
    val removeCategory: RemoveCategoryUseCase,
)
