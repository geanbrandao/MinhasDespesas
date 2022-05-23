package dev.geanbrandao.minhasdespesas.feature.presentation.filters.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.geanbrandao.minhasdespesas.common.components.texts.TextDefault
import dev.geanbrandao.minhasdespesas.common.utils.isLastItem
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.model.ActiveFiltersModel
import dev.geanbrandao.minhasdespesas.feature.domain.model.ActiveFiltersSimpleModel
import dev.geanbrandao.minhasdespesas.feature.presentation.categories.components.CategoryItem
import dev.geanbrandao.minhasdespesas.feature.presentation.filters.states.FiltersState
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.MarginOne

@Composable
fun ListSelectedFilters(
    dataList: List<ActiveFiltersSimpleModel>,
    onRemoveFilter: (atIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (dataList.isEmpty()) {
            TextDefault(
                text = "Nenhum filtro selecionado",
                textColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(alignment = Alignment.Center),
            )
        } else {
            Row {
                LazyRow {
                    itemsIndexed(dataList) { index: Int, item ->
                        ItemFilter(item = item.name) {
                            onRemoveFilter(index)
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun ListCategoryOptions(
    state: FiltersState,
    onCheckedChangeListener: (isChecked: Boolean, categoryDb: CategoryDb) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dataList = state.dataList
    Column(
        modifier = modifier.padding(horizontal = MarginOne)
    ) {
        dataList.forEachIndexed { index, item ->
            CategoryItem(
                item = item,
                isLastItem = dataList.isLastItem(index = index)
            ) { isChecked: Boolean ->
                onCheckedChangeListener.invoke(isChecked, item)
            }
        }
    }
}

