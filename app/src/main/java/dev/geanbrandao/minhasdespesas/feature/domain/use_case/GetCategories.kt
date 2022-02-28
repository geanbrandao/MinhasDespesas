package dev.geanbrandao.minhasdespesas.feature.domain.use_case

import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategories(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<CategoryDb>> = flow {
        val categories = repository.getCategories()
        emit(categories)
    }
}