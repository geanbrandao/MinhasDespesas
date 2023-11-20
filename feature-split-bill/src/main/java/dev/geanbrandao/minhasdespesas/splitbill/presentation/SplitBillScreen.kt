package dev.geanbrandao.minhasdespesas.splitbill.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.components.button.ActionButtonView
import br.dev.geanbrandao.common.presentation.components.button.ButtonView
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerThree
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarView
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.splitbill.domain.SplitBillItem

@Composable
fun SplitBillScreen(
    navHostController: NavHostController,
//    viewModel
) {
    SplitBillScreenView(
        navHostController = navHostController,
        addBill = {

        }
    )
}

@Composable
fun SplitBillScreenView(
    navHostController: NavHostController,
    addBill: () -> Unit
) {

    val item = SplitBillItem(
        itemId = 0,
        ownerName = "Fulano",
        name = "Coca-cola",
        value = 10.0f,
    )

    BaseScreen(
        header = {
            ToolbarView(
                onBackButtonClicked = {}, // todo adicionar voltar
                toolbarTitle = "Dividir a conta"
            )
        }
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                SpacerThree()
                ButtonView(
                    text = "Fechar conta",
                    modifier = Modifier.padding(horizontal = PaddingTwo)
                ) {

                }
                SplitBillListView(listOf(item, item, item, item, item, item, item, item, item, item, item, item))
            }
            ActionButtonView(
                icon = rememberVectorPainter(image = Icons.Rounded.Add),
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {

                }
            )
        }
    }
}


@Preview
@Composable
private fun SplitBillScreenPrev() {
    SplitBillScreen(navHostController = rememberNavController())
}