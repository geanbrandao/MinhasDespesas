package dev.geanbrandao.lib.compose.piecharts.presentation.renderer

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import dev.geanbrandao.lib.compose.piecharts.domain.model.PieChartModel

class SliceDrawer(private val sliceThickness: Float = DEFAULT_SLICE_THICKNESS) {

    init {
        require(sliceThickness in MIN_SLICE_THICKNESS..MAX_SLICE_THICKNESS) {
            "Thickness of $sliceThickness must be between $MIN_SLICE_THICKNESS-$MAX_SLICE_THICKNESS"
        }
    }

    private val sectionPaint = Paint().apply {
        isAntiAlias = true
        style = PaintingStyle.Stroke
    }

    fun drawSlice(
        canvas: Canvas,
        area: Size,
        startAngle: Float,
        sweepAngle: Float,
        slice: PieChartModel.Slice
    ) {
        val sliceThickness = calculateSectorThickness(area = area)
        val drawableArea = calculateDrawableArea(area = area)

        canvas.drawArc(
            rect = drawableArea,
            paint = sectionPaint.apply {
                color = slice.color
                strokeWidth = sliceThickness
            },
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false
        )
    }

    private fun calculateSectorThickness(area: Size): Float {
        val minSize = minOf(area.width, area.height)

        return minSize * (sliceThickness / 200f)
    }

    private fun calculateDrawableArea(area: Size): Rect {
        val sliceThicknessOffset = calculateSectorThickness(area = area) / 2f
        val offsetHorizontally = (area.width - area.height) / 2f

        return Rect(
            left = sliceThicknessOffset + offsetHorizontally,
            top = sliceThicknessOffset,
            right = area.width - sliceThicknessOffset - offsetHorizontally,
            bottom = area.height - sliceThicknessOffset
        )
    }

    companion object {
        private const val DEFAULT_SLICE_THICKNESS = 25f
        private const val MAX_SLICE_THICKNESS = 100f
        private const val MIN_SLICE_THICKNESS = 10f
    }
}

//class SliceDrawer(
//    private val sliceThickness: Float = DEFAULT_SLICE_THICKNESS,
//    private val sliceSpacing: Float = DEFAULT_SLICE_SPACING
//) {
//
//    init {
//        require(sliceThickness in MIN_SLICE_THICKNESS..MAX_SLICE_THICKNESS) {
//            "Thickness of $sliceThickness must be between $MIN_SLICE_THICKNESS-$MAX_SLICE_THICKNESS"
//        }
//    }
//
//    private val sectionPaint = Paint().apply {
//        isAntiAlias = true
//        style = PaintingStyle.Stroke
//    }
//
//    fun drawSlice(
//        canvas: Canvas,
//        area: Size,
//        startAngle: Float,
//        sweepAngle: Float,
//        slice: PieChartModel.Slice
//    ) {
//        val sliceThickness = calculateSectorThickness(area = area)
//        val drawableArea = calculateDrawableArea(area = area)
//
//        canvas.drawArc(
//            rect = drawableArea,
//            paint = sectionPaint.apply {
//                color = slice.color
//                strokeWidth = sliceThickness
//            },
//            startAngle = startAngle,
//            sweepAngle = sweepAngle - sliceSpacing, // Adjusting sweepAngle to include spacing
//            useCenter = false
//        )
//    }
//
//    private fun calculateSectorThickness(area: Size): Float {
//        val minSize = minOf(area.width, area.height)
//
//        return minSize * (sliceThickness / 200f)
//    }
//
//    private fun calculateDrawableArea(area: Size): Rect {
//        val sliceThicknessOffset = calculateSectorThickness(area = area) / 2f
//        val offsetHorizontally = (area.width - area.height) / 2f
//
//        val spacing = calculateSectorThickness(area = area) * (sliceSpacing / 100f)
//        val sliceSpacingOffset = spacing / 2f
//
//        return Rect(
//            left = sliceThicknessOffset + offsetHorizontally,
//            top = sliceThicknessOffset + sliceSpacingOffset,
//            right = area.width - sliceThicknessOffset - offsetHorizontally,
//            bottom = area.height - sliceThicknessOffset - sliceSpacingOffset
//        )
//    }
//
//    companion object {
//        private const val DEFAULT_SLICE_THICKNESS = 25f
//        private const val MAX_SLICE_THICKNESS = 100f
//        private const val MIN_SLICE_THICKNESS = 10f
//        private const val DEFAULT_SLICE_SPACING = 5f
//    }
//}