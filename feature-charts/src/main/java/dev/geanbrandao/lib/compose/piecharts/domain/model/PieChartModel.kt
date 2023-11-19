package dev.geanbrandao.lib.compose.piecharts.domain.model

import androidx.compose.ui.graphics.Color

data class PieChartModel(
    val slices: List<Slice>,
) {
    internal val totalSize: Float
        get() = slices.map { it.value }.sum()

    internal fun getPercentage(position: Int): String {
        val total = slices.map { it.value }.sum()
        val slice = slices[position].value

        val percentage = (slice / total) * 100.0f
        return String.format("%.2f%%", percentage)
    }

    data class Slice(
        val value: Float,
        val color: Color,
        val label: String = "",
        val iconName: String = "",
    )
}
