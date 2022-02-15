package dev.geanbrandao.minhasdespesas.common.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.texts.TextButtonDefault
import dev.geanbrandao.minhasdespesas.ui.theme.CornersDefault

val ShapeButtonDefault = RoundedCornerShape(size = CornersDefault)
val HeightButton = 55.dp

@Composable
fun ButtonDefault(
    @StringRes stringId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = MaterialTheme.colorScheme.primary,
) {
    Button(
        modifier = modifier
            .background(color = buttonColor, shape = ShapeButtonDefault)
            .fillMaxWidth()
            .height(height = HeightButton),
        onClick = {
            onClick()
        }
    ) {
        TextButtonDefault(stringId = stringId)
    }
}

@Preview
@Composable
fun ButtonDefaultPreview() {
    ButtonDefault(stringId = R.string.button_default_text_ok)
}