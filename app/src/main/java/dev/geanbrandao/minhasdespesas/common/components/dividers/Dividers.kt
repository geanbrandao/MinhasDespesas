package dev.geanbrandao.minhasdespesas.common.components.dividers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val DividerDefaultHeight = 1.dp
val DividerInputHeight = 1.5.dp
val DividerCornerSize = 0.5.dp
const val DividerDefaultAlpha = 0.6f

@Composable
fun ItemDefaultDivider(color: Color, shouldShow: Boolean, modifier: Modifier = Modifier) {
    if (shouldShow) {
        Spacer(
            modifier = modifier
                .height(height = DividerDefaultHeight)
                .fillMaxWidth()
                .background(color = color)
                .alpha(alpha = DividerDefaultAlpha)
        )
    }
}

@Composable
fun DividerInput() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = DividerInputHeight)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(size = DividerCornerSize)
            )
    )
}

@Preview
@Composable
fun DividerInputPreview() {
    DividerInput()
}

@Preview
@Composable
fun ItemDefaultDividerPreview() {
    ItemDefaultDivider(
        color = Color.Black,
        shouldShow = true,
    )
}