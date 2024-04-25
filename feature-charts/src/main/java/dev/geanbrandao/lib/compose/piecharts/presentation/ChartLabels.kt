package dev.geanbrandao.lib.compose.piecharts.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.domain.MoneyUtils.formatToBrl
import br.dev.geanbrandao.common.presentation.components.CategoryIconView
import br.dev.geanbrandao.common.presentation.components.text.TextLabelMedium
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import dev.geanbrandao.lib.compose.piecharts.domain.model.PieChartModel

@Composable
fun ChartLabels(
    pieChartModel: PieChartModel,
) {
    ChartLabelsView(pieChartModel = pieChartModel)
}

@Composable
private fun ChartLabelsView(
    pieChartModel: PieChartModel,
) {
    val slices = pieChartModel.slices
    Column {
        slices.forEachIndexed { index, slice ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val iconColor = if (slice.color.luminance() > 0.5) Color.Black else Color.White
                CategoryIconView(
                    iconName = slice.iconName,
                    backgroundColor = slice.color,
                    iconColor = iconColor,
                )
                val label = buildString {
                    append(pieChartModel.getPercentage(index))
                    append(" - ")
                    append(slice.value.formatToBrl())
                    append(" - ")
                    append(slice.label)
                }
                slice.value.formatToBrl().plus(" - ").plus(slice.label)
                TextLabelMedium(
                    text = label,
                    modifier = Modifier.padding(PaddingOne),
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.size(PaddingOne))
        }
    }
}

@Preview
@Composable
fun ChartLabelsViewPreview() {
    val slices = listOf(
        PieChartModel.Slice(25f, Color.Red, "One"),
        PieChartModel.Slice(42f, Color.Blue, "Two"),
        PieChartModel.Slice(23f, Color.Green, "three")
    ).sortedByDescending { it.value }
    Column(
        Modifier
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        ChartLabelsView(PieChartModel(slices))
    }
}