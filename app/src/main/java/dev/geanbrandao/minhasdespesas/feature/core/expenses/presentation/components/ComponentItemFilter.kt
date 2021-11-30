package dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.geanbrandao.minhasdespesas.feature.core.expenses.util.TestTags.ITEM_FILTER_ICON_CLOSE
import dev.geanbrandao.minhasdespesas.feature.core.expenses.util.TestTags.ITEM_FILTER_ROOT
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.ItemFilterCorners
import dev.geanbrandao.minhasdespesas.ui.theme.MarginDefault
import dev.geanbrandao.minhasdespesas.ui.theme.MarginHalf
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingHalf

@Composable
fun ItemFilter(
    item: String,
    onClickItemFilter: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(size = ItemFilterCorners),
        modifier = Modifier
            .testTag(ITEM_FILTER_ROOT)
            .padding(end = MarginDefault),
        backgroundColor = MaterialTheme.colorScheme.secondary,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = PaddingHalf,
                    end = MarginHalf,
                    top = MarginHalf,
                    bottom = MarginHalf
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item,
                style = AppTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            Spacer(modifier = Modifier.size(size = 4.dp))
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "Close icon",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .testTag(ITEM_FILTER_ICON_CLOSE)
                    .align(alignment = Alignment.CenterVertically)
                    .clickable {
                        onClickItemFilter()
                    }
            )
        }
    }
}

@Preview("Item Filter")
@Composable
fun Preview() {
    ItemFilter("Mês atual") {

    }
}
