package dev.geanbrandao.lib.compose.piecharts.domain.model

import androidx.compose.ui.graphics.Color

data class PieChartModel(
    val slices: List<Slice>
) {
    internal val totalSize: Float
        get() = slices.map { it.value }.sum()


    data class Slice(
        val value: Float,
        val color: Color
    )
}
