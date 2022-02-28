package dev.geanbrandao.minhasdespesas.feature.domain.use_case

import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseWithCategoriesDb
import dev.geanbrandao.minhasdespesas.feature.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetExpenses(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<ExpenseWithCategoriesDb>> = flow {
        val expenses = repository.getExpenses()
        emit(expenses)
    }
}