package dev.geanbrandao.minhasdespesas.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryWithExpensesDb
import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseWithCategoriesDb

@Dao
interface DatabaseDao {
    @Transaction
    @Query("SELECT * FROM ExpenseDb")
    suspend fun getExpensesWithCategories(): List<ExpenseWithCategoriesDb>

    @Transaction
    @Query("SELECT * FROM CategoryDb")
    suspend fun getCategoriesWithExpenses(): List<CategoryWithExpensesDb>
}