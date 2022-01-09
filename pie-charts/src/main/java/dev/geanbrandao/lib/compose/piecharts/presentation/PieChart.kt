package dev.geanbrandao.lib.compose.piecharts.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.lib.compose.piecharts.domain.model.PieChartModel
import dev.geanbrandao.lib.compose.piecharts.presentation.renderer.SliceDrawer

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieChartModel: PieChartModel,
    sliceDrawer: SliceDrawer = SliceDrawer()
) {
    val transitionProgress = remember(pieChartModel.slices) {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(pieChartModel.slices) {
        transitionProgress.animateTo(1f, animationSpec = TweenSpec<Float>(durationMillis = 500))
    }

    DrawChart(
        pieChartModel = pieChartModel,
        modifier = modifier,
        progress = transitionProgress.value,
        sliceDrawer = sliceDrawer,
    )
}

@Composable
fun DrawChart(
    pieChartModel: PieChartModel,
    modifier: Modifier,
    progress: Float,
    sliceDrawer: SliceDrawer,
) {
    val slices = pieChartModel.slices

    Canvas(modifier = modifier) {
        drawIntoCanvas {
            var startArc = 0f
            slices.forEach { slice ->
                val arc = calculateAngle(
                    sliceLength = slice.value,
                    totalLength = pieChartModel.totalSize,
                    progress = progress
                )

                sliceDrawer.drawSlice(
                    canvas = drawContext.canvas,
                    area = size,
                    startAngle = startArc,
                    sweepAngle = arc,
                    slice = slice,
                )

                startArc += arc
            }
        }
    }
}

private fun calculateAngle(
    sliceLength: Float,
    totalLength: Float,
    progress: Float
): Float {
    return 360.0f * (sliceLength * progress) / totalLength
}

@Preview
@Composable
fun PieChartPreview() = PieChart(
    pieChartModel = PieChartModel(
        slices = listOf(
            PieChartModel.Slice(25f, Color.Red),
            PieChartModel.Slice(42f, Color.Blue),
            PieChartModel.Slice(23f, Color.Green)
        )
    )
)