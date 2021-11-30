package dev.geanbrandao.minhasdespesas.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemDefaultDivider(color: Color, shouldShow: Boolean, modifier: Modifier = Modifier) {
    if (shouldShow) {
        Spacer(
            modifier = modifier
                .height(height = 1.dp)
                .fillMaxWidth()
                .background(color = color)
                .alpha(alpha = 0.6f)
        )
    }
}
