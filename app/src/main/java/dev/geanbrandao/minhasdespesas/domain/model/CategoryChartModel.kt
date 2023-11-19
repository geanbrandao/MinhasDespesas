package dev.geanbrandao.minhasdespesas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryChartModel(
    val categoryId: Long,
    val icon: String,
    val label: String,
    val amount: Float,
) : Parcelable