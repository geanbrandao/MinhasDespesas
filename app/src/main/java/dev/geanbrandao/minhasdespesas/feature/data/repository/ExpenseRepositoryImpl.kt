package dev.geanbrandao.minhasdespesas.feature.data.repository

import dev.geanbrandao.minhasdespesas.common.utils.DatabaseOperationState
import dev.geanbrandao.minhasdespesas.core.database.DatabaseDao
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseWithCategoriesDb
import dev.geanbrandao.minhasdespesas.feature.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(
    private val databaseDao: DatabaseDao
): ExpenseRepository {

    override suspend fun getExpenses(): List<ExpenseWithCategoriesDb> {
        return databaseDao.getExpensesWithCategories()
    }

    override suspend fun postCategoryIfNotExist(data: List<CategoryDb>): DatabaseOperationState {
        data.forEach { category ->
            if (databaseDao.checkIfCategoryNameExist(name = category.name).not()) {
                databaseDao.insertCategory(data = category)
            }
        }
        return DatabaseOperationState.DONE
    }

    override suspend fun getCategories(): List<CategoryDb> {
        return databaseDao.getAllCategories()
    }
}