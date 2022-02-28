package dev.geanbrandao.minhasdespesas.feature.domain.use_case

import dev.geanbrandao.minhasdespesas.common.utils.DatabaseOperationState
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostCategories(
    private val repository: ExpenseRepository
) {
    operator fun invoke(data: List<CategoryDb>): Flow<DatabaseOperationState> = flow {
        emit(DatabaseOperationState.WORKING)
        val value = repository.postCategoryIfNotExist(data = data)
        emit(value)
    }
}