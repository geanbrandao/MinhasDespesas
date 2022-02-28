package dev.geanbrandao.minhasdespesas.feature.domain.use_case

data class ExpenseUseCases(
    val getExpenses: GetExpenses,
    val postCategories: PostCategories,
    val getCategories: GetCategories,
    val postExpense: PostExpense,
)