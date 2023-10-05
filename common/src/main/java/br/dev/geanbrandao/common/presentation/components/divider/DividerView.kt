package br.dev.geanbrandao.common.presentation.components.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.presentation.components.divider.DividerTypeEnum.DEFAULT
import br.dev.geanbrandao.common.presentation.components.divider.DividerTypeEnum.INPUT


val DividerDefaultHeight = 1.dp
val DividerInputHeight = 1.5.dp
val DividerCornerSize = 0.5.dp
const val DividerDefaultAlpha = 0.6f

enum class DividerTypeEnum {
    DEFAULT,
    INPUT,
}

sealed class DividerType(val dividerType: DividerTypeEnum) {
    data object Default : DividerType(DEFAULT)
    data object Input: DividerType(INPUT)
}

@Composable
fun DividerView(
    dividerType: DividerType = DividerType.Default,
) {
    when (dividerType) {
        DividerType.Default -> TODO()
        DividerType.Input -> DividerInput()
    }
}

@Composable
private fun DividerInput() {
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
private fun DividerViewPreview() {
    DividerView()
}