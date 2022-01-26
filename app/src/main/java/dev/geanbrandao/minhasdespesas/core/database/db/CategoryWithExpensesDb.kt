package dev.geanbrandao.minhasdespesas.core.database.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CategoryWithExpensesDb(
    @Embedded val categoryDb: CategoryDb,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "expenseId",
        associateBy = Junction(ExpenseCategoryCrossRefDb::class)
    )
    val expensesDb: List<ExpenseDb>,
)
