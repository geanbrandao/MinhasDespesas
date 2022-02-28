package dev.geanbrandao.minhasdespesas.feature.domain.repository

import dev.geanbrandao.minhasdespesas.common.utils.DatabaseOperationState
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseWithCategoriesDb

interface ExpenseRepository {
    suspend fun getExpenses(): List<ExpenseWithCategoriesDb>
    suspend fun postCategoryIfNotExist(data: List<CategoryDb>): DatabaseOperationState
    suspend fun getCategories(): List<CategoryDb>
}