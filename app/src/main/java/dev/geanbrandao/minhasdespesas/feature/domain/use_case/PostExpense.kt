package dev.geanbrandao.minhasdespesas.feature.domain.use_case

import dev.geanbrandao.minhasdespesas.feature.domain.repository.ExpenseRepository

class PostExpense(
    private val repository: ExpenseRepository
) {
}