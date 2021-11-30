package dev.geanbrandao.minhasdespesas.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components.ItemFilter
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun ListFilters(
    data: SnapshotStateList<String>,
    onClickItemFilter: (index: Int) -> Unit,
) {

    val listFilters = remember {
        mutableStateOf(data)
    }
    if (data.isNotEmpty()) {
        Box(
            modifier = Modifier.padding(
                all = PaddingDefault,
            )
        ) {
            LazyRow {
                itemsIndexed(
                    items = listFilters.value,
                    key = { _: Int, item: String ->
                        item.hashCode()
                    },
                ) { _, item ->
                    ItemFilter(item = item) {
                        onClickItemFilter(1)
                        data.remove(item)
                    }
                }
            }
        }
    }
}
