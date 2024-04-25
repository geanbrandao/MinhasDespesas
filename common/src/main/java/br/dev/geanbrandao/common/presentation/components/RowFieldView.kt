package br.dev.geanbrandao.common.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.dev.geanbrandao.common.domain.clickableNoRippleEffect

@Composable
fun RowFieldView(
    modifier: Modifier = Modifier,
    start: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit = {},
    end: @Composable RowScope.() -> Unit = {},
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickableNoRippleEffect {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        start()
        content()
        end()
    }
}

@Preview
@Composable
private fun RowFieldViewPreview() {
    RowFieldView(
        start = {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
        },
        content = {
            Text(text = "XPTO", modifier = Modifier.weight(1f))
        },
        end = {
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
        }
    )
}