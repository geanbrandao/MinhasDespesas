package dev.geanbrandao.minhasdespesas.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
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
    primaryKeys = ["expenseId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = ExpenseEntity::class,
            parentColumns = ["expenseId"],
            childColumns = ["expenseId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
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