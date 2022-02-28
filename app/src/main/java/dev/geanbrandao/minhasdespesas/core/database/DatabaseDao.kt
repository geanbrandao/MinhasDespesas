package dev.geanbrandao.minhasdespesas.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
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

    @Query("SELECT EXISTS (SELECT * FROM CategoryDb WHERE name = :name)")
    suspend fun checkIfCategoryNameExist(name: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(data: CategoryDb): Long

    @Query("SELECT * FROM CategoryDb ORDER BY name")
    suspend fun getAllCategories(): List<CategoryDb>
}