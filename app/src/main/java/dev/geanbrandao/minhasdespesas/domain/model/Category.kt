package dev.geanbrandao.minhasdespesas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Category(
    val categoryId: Long,
    val name: String,
    val icon: String,
    val canRemove: Boolean = false,
    val isChecked: Boolean = false,
) : Parcelable
