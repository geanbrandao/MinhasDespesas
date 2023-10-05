package dev.geanbrandao.minhasdespesas.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import java.time.OffsetDateTime

@Entity
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long,
    val amount: Float,
    val name: String,
    val selectedDate: OffsetDateTime,
    val description: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long,
    val name: String,
    val icon: String,
    val canRemove: Boolean = false,
)

@Entity(
    primaryKeys = ["expenseId", "categoryId"]
)
data class ExpenseCategoryCrossRefEntity(
    val expenseId: Long,
    val categoryId: Long,
)

data class ExpensesWithCategories(
    @Embedded val expense: ExpenseEntity,
    @Relation(
        parentColumn = "expenseId",
        entityColumn = "categoryId",
        associateBy = Junction(ExpenseCategoryCrossRefEntity::class)
    )
    val categories: List<CategoryEntity>
)