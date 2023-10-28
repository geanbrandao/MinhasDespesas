package dev.geanbrandao.minhasdespesas.feature.presentation.filters.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.texts.TextDefault
//import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.model.ActiveFiltersSimpleModel
//import dev.geanbrandao.minhasdespesas.feature.presentation.categories.components.CategoryItem
//import dev.geanbrandao.minhasdespesas.feature.presentation.filters.states.FiltersState

@Composable
fun ListSelectedFilters(
    dataList: List<ActiveFiltersSimpleModel>,
    onRemoveFilter: (atIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (dataList.isEmpty()) {
            TextDefault(
                text = stringResource(id = R.string.warning_message_no_filter_selected),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(alignment = Alignment.Center),
            )
        } else {
            Row {
                LazyRow {
                    itemsIndexed(dataList) { index: Int, item: ActiveFiltersSimpleModel ->
                        ItemFilter(item = item) {
                            onRemoveFilter(index)
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun ListCategoryOptions(
//    state: FiltersState,
//    onCheckedChangeListener: (isChecked: Boolean, categoryDb: CategoryDb) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    val dataList = state.dataList
//    Column(
//        modifier = modifier.padding(horizontal = PaddingDefault)
//    ) {
//        dataList.forEachIndexed { index, item ->
//            CategoryItem(
//                item = item,
//                isLastItem = dataList.isLastItem(index = index)
//            ) { isChecked: Boolean ->
//                onCheckedChangeListener.invoke(isChecked, item)
//            }
//        }
//    }
//}
