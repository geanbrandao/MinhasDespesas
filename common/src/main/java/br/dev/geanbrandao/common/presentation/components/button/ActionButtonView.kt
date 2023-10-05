package br.dev.geanbrandao.common.presentation.components.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo

@Composable
fun ActionButtonView(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
        modifier = modifier.padding(end = PaddingTwo, bottom = PaddingTwo),
    ) {
        IconView(
            icon = icon,
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.size(size = 48.dp)
        )
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    ActionButtonView(
        icon = rememberVectorPainter(image = Icons.Rounded.Add),
        onClick = {}
    )
}

