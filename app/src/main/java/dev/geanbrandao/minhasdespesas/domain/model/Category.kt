package dev.geanbrandao.minhasdespesas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val categoryId: Long,
    val name: String,
    val icon: String,
    val canRemove: Boolean = false,
) : Parcelable
