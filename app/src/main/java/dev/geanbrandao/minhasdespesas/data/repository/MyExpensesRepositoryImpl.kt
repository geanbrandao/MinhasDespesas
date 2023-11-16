package dev.geanbrandao.minhasdespesas.data.repository

import dev.geanbrandao.minhasdespesas.data.dao.MyExpensesDao
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories
import dev.geanbrandao.minhasdespesas.di.QUALIFIER_EXPENSE_DB
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

const val PAGE_SIZE = 40

@Single
class MyExpensesRepositoryImpl(
    @Named(QUALIFIER_EXPENSE_DB) private val dao: MyExpensesDao
): MyExpensesRepository {
    override suspend fun addExpenses(
        expense: ExpenseEntity,
        categories: List<CategoryEntity>
    ): Long {
        return dao.insertExpenseWithCategories(expense = expense, categories = categories)
    }

    override suspend fun getExpenses(): List<ExpensesWithCategories> {
        return dao.getExpenses()
    }

    override suspend fun getExpenses(offset: Int): List<ExpensesWithCategories> {
        return dao.getExpenses(
            limit = PAGE_SIZE,
            offset = offset
        )
    }

    override suspend fun getExpense(expenseId: Long): ExpensesWithCategories {
        return dao.getExpense(id = expenseId)
    }

    override suspend fun updateExpense(expense: ExpenseEntity) {
        dao.updateExpense(expense = expense)
    }

    override suspend fun getCategories(): List<CategoryEntity> {
        return dao.getCategories()
    }

    override suspend fun getCategory(categoryId: Long): CategoryEntity? {
        return dao.getCategory(categoryId)
    }

    override suspend fun addCategories(categories: List<CategoryEntity>) {
        return dao.insertDefaultCategories(categories)
    }

    override suspend fun addCategory(category: CategoryEntity): Long {
        return dao.insertCategory(category)
    }

    override suspend fun removeCategory(categoryId: Long) {
        dao.deleteCategory(id = categoryId)
    }

    override suspend fun removeExpense(expenseId: Long) {
        dao.deleteExpense(id = expenseId)
    }
}