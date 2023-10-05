package dev.geanbrandao.minhasdespesas.splitbill.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.domain.MoneyUtils.formatToBrl
import br.dev.geanbrandao.common.presentation.components.text.TextBodyLarge
import br.dev.geanbrandao.common.presentation.components.text.TextBodyMedium
import br.dev.geanbrandao.common.presentation.components.text.TextLabelSmall
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.splitbill.domain.SplitBillItem


@Composable
fun SplitBillItemView(
    item: SplitBillItem,
) {
    val color1 = MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .padding(all = PaddingTwo),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextBodyLarge(
                    text = item.name,
                    color = color1,
                    modifier = Modifier.fillMaxWidth()
                )
                TextBodyMedium(
                    text = item.ownerName,
                    color = color1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.size(size = PaddingOne))
            TextLabelSmall(
                text = item.value.formatToBrl(),
                color = color1,
                modifier = Modifier,
            )

        }
    }
}

@Preview
@Composable
private fun BillItemViewPreview() {
    val item = SplitBillItem(
        itemId = 0,
        ownerName = "Fulano",
        name = "Coca-cola",
        value = 10.0f,
    )
    Column {
        SplitBillItemView(item)
        Spacer(modifier = Modifier.size(1.dp))
        SplitBillItemView(item)

    }
}