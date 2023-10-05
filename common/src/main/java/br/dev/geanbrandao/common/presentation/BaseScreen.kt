package br.dev.geanbrandao.common.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarView

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable ColumnScope.() -> Unit = {},
    isLoading: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface {
        Column(modifier = modifier) {
            header()
            if (isLoading) {
                LoadingView()
            } else {
                content()
            }
            footer()
        }
    }
}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
    }
}

@Preview
@Composable
private fun BaseScreenPreview() {
    BaseScreen(
        header = {
            ToolbarView(navHostController = rememberNavController(), toolbarTitle = "Base screen title")
        },
        isLoading = true,
    ) {
        Text(
            text = "BaseScreenPreview",
            Modifier.fillMaxSize()
        )
    }
}