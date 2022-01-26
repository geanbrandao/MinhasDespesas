package dev.geanbrandao.minhasdespesas.core.database.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDb(
    @PrimaryKey(autoGenerate = false)
    val categoryId: Long,
    val name: String,
    val icon: String,
    val canRemove: Boolean = false,
)
