package dev.geanbrandao.minhasdespesas.core.database.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CategoryDb(
    @PrimaryKey(autoGenerate = false)
    val categoryId: Long,
    val name: String,
    val icon: String,
    val canRemove: Boolean = false,
) : Parcelable
