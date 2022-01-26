package dev.geanbrandao.minhasdespesas.core.database.db

import androidx.room.Entity

@Entity(
    primaryKeys = ["expenseId", "categoryId"],
)
data class ExpenseCategoryCrossRefDb(
    val expenseId: Long,
    val categoryId: Long,
)