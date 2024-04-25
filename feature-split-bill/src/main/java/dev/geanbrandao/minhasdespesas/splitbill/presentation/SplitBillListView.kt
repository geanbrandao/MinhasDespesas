package dev.geanbrandao.minhasdespesas.splitbill.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.splitbill.domain.SplitBillItem

@Composable
fun SplitBillListView(
    list: List<SplitBillItem>,
) {
    LazyColumn {
        item {
            Spacer(modifier = Modifier.size(size = PaddingTwo))
        }
        itemsIndexed(list) { index: Int, item: SplitBillItem ->
            SplitBillItemView(item)
            Spacer(modifier = Modifier.size(size = 1.dp))
        }
    }
}

@Preview
@Composable
fun SplitBillListViewPreview() {
    val item = SplitBillItem(
        itemId = 0,
        ownerName = "Fulano",
        name = "Coca-cola",
        value = 10.0f,
    )
    SplitBillListView(listOf(item, item, item))
}