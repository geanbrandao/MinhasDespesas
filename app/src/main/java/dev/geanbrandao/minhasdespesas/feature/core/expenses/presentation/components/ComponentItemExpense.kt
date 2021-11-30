package dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.ItemDefaultDivider
import dev.geanbrandao.minhasdespesas.feature.core.expenses.util.TestTags.ITEM_EXPENSE_ROOT
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.MarginDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingHalf

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemExpenseWithSwipe(
    isLastItem: Boolean,
    onSwipeToDelete: () -> Unit,
    onSwipeToEdit: () -> Unit,
) {
    val swipeState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd) {
                onSwipeToDelete()
            } else if (it == DismissValue.DismissedToStart) {
                onSwipeToEdit()
            }
            true
        }
    )
    SwipeToDismiss(
        state = swipeState,
        background = {
            if (swipeState.dismissDirection == DismissDirection.StartToEnd) {
                BgSwipeToDelete()
            }
            if (swipeState.dismissDirection == DismissDirection.EndToStart) {
                BgSwipeToEdit()
            }
        },
        dismissThresholds = {
            FractionalThreshold(0.8f)
        },
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            ItemExpense()
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .background(color = Color.Black)
            )
            ItemDefaultDivider(
                color = MaterialTheme.colorScheme.onBackground,
                shouldShow = isLastItem.not(),
                modifier = Modifier.padding(horizontal = PaddingDefault)
            )
        }
    }
}

@Preview("Item expense")
@Composable
private fun ItemExpense() {
    Column(
        modifier = Modifier
            .testTag(tag = ITEM_EXPENSE_ROOT)
//            .background(color = Color.Red)
            .padding(
                start = PaddingDefault,
                end = PaddingDefault,
                top = PaddingHalf,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "05",
                    style = AppTypography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "OUT".uppercase(),
                    style = AppTypography.labelMedium,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(modifier = Modifier.size(size = MarginDefault))
            Text(
                text = "Aquele lanche maroto ahdaudhaudhaudhaudhaud",
                style = AppTypography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.size(size = MarginDefault))
            Text(
                text = stringResource(id = R.string.text_default_value_cipher, 25.6f),
                style = AppTypography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview("ItemExpenseWithSwipe preview")
@Composable
fun ItemExpensePreview() {
    ItemExpenseWithSwipe(
        isLastItem = false,
        onSwipeToDelete = { },
        onSwipeToEdit = { },
    )
}
