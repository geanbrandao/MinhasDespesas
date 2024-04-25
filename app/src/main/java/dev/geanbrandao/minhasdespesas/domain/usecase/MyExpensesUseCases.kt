package dev.geanbrandao.minhasdespesas.domain.usecase

import org.koin.core.annotation.Single

@Single
data class MyExpensesUseCases(
    val addExpense: AddExpenseUseCase,
    val getExpenses: GetExpensesUseCase,
    val getExpensesBaseOnFilters: GetExpensesBaseOnFiltersUseCase,
    val getExpense: GetExpenseUseCase,
    val updateExpense: UpdateExpenseUseCase,
    val removeExpense: RemoveExpenseUseCase,
    val getCategories: GetCategoriesUseCase,
    val getCategoriesUsingId: GetCategoriesUsingIdUseCase,
    val addCategories: AddCategoriesUseCase,
    val addCategory: AddCategoryUseCase,
    val removeCategory: RemoveCategoryUseCase,
    val getCategoriesChartData: GetCategoriesChartDataUseCase,
)
