package br.dev.geanbrandao.common.presentation.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerThree
import br.dev.geanbrandao.common.presentation.components.text.TextToolbar
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarTypeEnum.DEFAULT
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarTypeEnum.NAVIGATION
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo

enum class ToolbarTypeEnum {
    DEFAULT,
    NAVIGATION,
}

sealed class ToolbarType(val toolbarType: ToolbarTypeEnum) {
    data object Default : ToolbarType(DEFAULT)
    data object Navigation : ToolbarType(NAVIGATION)
}

@Composable
fun ToolbarView(
    navHostController: NavHostController,
    toolbarTitle: String,
    modifier: Modifier = Modifier,
    toolbarType: ToolbarType = ToolbarType.Navigation,
) {
    Card(
        shape = RectangleShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondary)
                .padding(all = PaddingTwo),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconView(
                icon = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
            ) {
                navHostController.navigateUp()
            }
            SpacerThree()
            TextToolbar(
                text = toolbarTitle,
                textColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
private fun ToolbarViewPreview() {
    ToolbarView(navHostController = rememberNavController(), toolbarTitle = "Titulo")
}