package dev.geanbrandao.minhasdespesas.domain.model

import android.os.Parcelable
import java.time.OffsetDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expense(
    val expenseId: Long,
    val amount: Float,
    val name: String,
    val selectedDate: Long,
    val description: String,
    val categories: List<Category>,
    val createdAt: Long,
    val updatedAt: Long,
) : Parcelable
