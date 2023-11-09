package dev.geanbrandao.minhasdespesas.domain.repository

import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories

interface MyExpensesRepository {
    suspend fun addExpenses(expense: ExpenseEntity, categories: List<CategoryEntity>): Long
    suspend fun getExpenses(): List<ExpensesWithCategories>
    suspend fun getExpense(expenseId: Long): ExpensesWithCategories
    suspend fun updateExpense(expense: ExpenseEntity)
    suspend fun getCategories(): List<CategoryEntity>
    suspend fun getCategory(categoryId: Long): CategoryEntity?
    suspend fun addCategories(categories: List<CategoryEntity>)
    suspend fun addCategory(category: CategoryEntity) : Long
    suspend fun removeCategory(categoryId: Long)
    suspend fun removeExpense(expenseId: Long)
}