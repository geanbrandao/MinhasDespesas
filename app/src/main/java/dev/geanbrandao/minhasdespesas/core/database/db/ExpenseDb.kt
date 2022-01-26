package dev.geanbrandao.minhasdespesas.core.database.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity
data class ExpenseDb(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long,
    val amount: Float,
    val name: String,
    val selectedDate: OffsetDateTime,
    val description: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)
