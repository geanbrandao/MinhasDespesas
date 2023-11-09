package dev.geanbrandao.minhasdespesas.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseCategoryCrossRefEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories

@Dao
interface MyExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseCategoryList(expenseCategoryList: List<ExpenseCategoryCrossRefEntity>)

    @Transaction
    suspend fun insertExpenseWithCategories(expense: ExpenseEntity, categories: List<CategoryEntity>): Long {
        val expenseId = insertExpense(expense = expense)
        val expenseCategoryList = categories.map {
            ExpenseCategoryCrossRefEntity(expenseId = expenseId, categoryId = it.categoryId)
        }
        insertExpenseCategoryList(expenseCategoryList = expenseCategoryList)
        return expenseId
    }

    @Transaction
    @Query("SELECT * FROM ExpenseEntity")
    suspend fun getExpenses(): List<ExpensesWithCategories>

    @Transaction
    @Query("SELECT * FROM ExpenseEntity WHERE expenseId = :id")
    suspend fun getExpense(id: Long): ExpensesWithCategories

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE categoryId = :id")
    suspend fun getCategory(id: Long): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(data: CategoryEntity): Long

    @Transaction
    suspend fun insertDefaultCategories(categories: List<CategoryEntity>) {
        val defaultCategories = getDefaultCategories()
        categories.filterNot { newCategory ->
            defaultCategories.any { previousCategory ->
                newCategory.name == previousCategory.name &&
                        newCategory.canRemove == previousCategory.canRemove &&
                        newCategory.icon == previousCategory.icon
            }
        }.forEach { insertCategory(it) }

        defaultCategories.filterNot { previousCategory ->
            categories.any { newCategory ->
                newCategory.name == previousCategory.name &&
                        newCategory.canRemove == previousCategory.canRemove &&
                        newCategory.icon == previousCategory.icon
            }
        }.forEach { deleteCategory(it) }
    }

    @Query("SELECT * FROM CategoryEntity WHERE canRemove = 0")
    suspend fun getDefaultCategories(): List<CategoryEntity>

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("DELETE FROM CategoryEntity WHERE categoryId = :id")
    suspend fun deleteCategory(id: Long)

    @Query("DELETE FROM ExpenseEntity WHERE expenseId = :id")
    suspend fun deleteExpense(id: Long)
}