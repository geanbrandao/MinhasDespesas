package dev.geanbrandao.minhasdespesas.feature.data.repository

import dev.geanbrandao.minhasdespesas.data.dao.MyExpensesDao
import dev.geanbrandao.minhasdespesas.di.QUALIFIER_EXPENSE_DB
import dev.geanbrandao.minhasdespesas.feature.domain.repository.ExpenseRepository
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class ExpenseRepositoryImpl(
    @Named(QUALIFIER_EXPENSE_DB) private val databaseDao: MyExpensesDao
): ExpenseRepository {

//    override suspend fun getExpenses(): List<ExpenseWithCategoriesDb> {
//        return databaseDao.getExpensesWithCategories()
//    }
//
//    override suspend fun postCategoryIfNotExist(data: List<CategoryDb>): DatabaseOperationState {
//        data.forEach { category ->
//            if (databaseDao.checkIfCategoryNameExist(name = category.name).not()) {
//                databaseDao.insertCategory(data = category)
//            }
//        }
//        return DatabaseOperationState.DONE
//    }
//
//    override suspend fun getCategories(): List<CategoryDb> {
//        return databaseDao.getAllCategories()
//    }
}