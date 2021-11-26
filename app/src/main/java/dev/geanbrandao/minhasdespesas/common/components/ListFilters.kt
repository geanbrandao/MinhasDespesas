package dev.geanbrandao.minhasdespesas.common.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components.ItemFilter
import dev.geanbrandao.minhasdespesas.ui.theme.MarginDefault


@Composable
fun ListFilters(data: List<String>) {

    val listFilters = remember {
        mutableStateOf(data)
    }

    LazyRow(contentPadding = PaddingValues(all = MarginDefault)) {
        items(listFilters.value) { item ->
            ItemFilter(item) {

            }
        }
    }
}
