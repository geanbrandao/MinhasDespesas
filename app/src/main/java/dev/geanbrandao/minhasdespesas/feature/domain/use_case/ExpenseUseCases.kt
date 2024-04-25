package dev.geanbrandao.minhasdespesas.feature.domain.use_case

import org.koin.core.annotation.Single

@Single
data class ExpenseUseCases(
    val getExpenses: GetExpenses,
    val postCategories: PostCategories,
    val getCategories: GetCategories,
    val postExpense: PostExpense,
)