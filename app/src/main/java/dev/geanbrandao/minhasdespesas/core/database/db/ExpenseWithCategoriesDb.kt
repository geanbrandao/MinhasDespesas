package dev.geanbrandao.minhasdespesas.core.database.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ExpenseWithCategoriesDb(
    @Embedded val expenseDb: ExpenseDb,
    @Relation(
        parentColumn = "expenseId",
        entityColumn = "categoryId",
        associateBy = Junction(ExpenseCategoryCrossRefDb::class)
    )
    val categories: List<CategoryDb>
)
