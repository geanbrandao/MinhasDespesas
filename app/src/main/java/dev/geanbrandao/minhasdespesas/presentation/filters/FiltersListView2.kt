package dev.geanbrandao.minhasdespesas.presentation.filters

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.domain.clickableNoRippleEffect
import br.dev.geanbrandao.common.presentation.components.RowFieldView
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.texts.TextDefault
import dev.geanbrandao.minhasdespesas.common.components.texts.TextLabelStrong
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.presentation.categories.ItemCategoryDefaultView

private const val ROTATION_ONE = 0f
private const val ROTATION_TWO = -180f

// this has performance, but dosent have animated motion
@Composable
fun FiltersListView2(
    categories: List<Category>,
    onSelectedFilterDate: (FilterByDateEnum) -> Unit,
    onCheckChangeListener: (Boolean, Category) -> Unit,
) {

    val rotationDate = remember { mutableFloatStateOf(ROTATION_ONE) }
    val rotationDateAnimation = animateFloatAsState(
        targetValue = rotationDate.floatValue,
        label = "rotationAnimation"
    )  // todo ver se precisa ser um rememberSavable

    val rotationCategories = remember { mutableFloatStateOf(ROTATION_ONE) }
    val rotationCategoriesAnimation = animateFloatAsState(
        targetValue = rotationCategories.floatValue,
        label = "rotationAnimation"
    )  // todo ver se precisa ser um rememberSavable

    val filterByDateList = listOf(
        FilterByDateItem(
            label = stringResource(id = R.string.bottom_sheet_dialog_filter_option_2),
            type = FilterByDateEnum.WEEK
        ),
        FilterByDateItem(
            label = stringResource(id = R.string.bottom_sheet_dialog_filter_option_3),
            type = FilterByDateEnum.MONTH
        ),
        FilterByDateItem(
            label = stringResource(id = R.string.bottom_sheet_dialog_filter_option_8),
            type = FilterByDateEnum.CURRENT_MONTH
        ),
        FilterByDateItem(
            label = stringResource(id = R.string.bottom_sheet_dialog_filter_option_4),
            type = FilterByDateEnum.YEAR
        ),
        FilterByDateItem(
            label = stringResource(id = R.string.bottom_sheet_dialog_filter_option_5),
            type = FilterByDateEnum.PICK_DATE
        ),
        FilterByDateItem(
            label = stringResource(id = R.string.bottom_sheet_dialog_filter_option_6),
            type = FilterByDateEnum.BETWEEN_DATES
        ),
    )

    LazyColumn {
        item {
            FilterByDateHeader(
                rotation = rotationDateAnimation.value,
                onClick = { currentRotation: Float ->
                    rotationDate.floatValue =
                        if (currentRotation == ROTATION_ONE) ROTATION_TWO else ROTATION_ONE
                },
            )
        }
        if (rotationDateAnimation.value == ROTATION_TWO) {
            filterByDateList(
                filterByDateList = filterByDateList,
                onSelectedFilterDate = onSelectedFilterDate,
            )
        }
        item {
            FilterByCategoriesHeader(
                rotation = rotationCategoriesAnimation.value,
                onClick = { currentRotation: Float ->
                    rotationCategories.floatValue =
                        if (currentRotation == ROTATION_ONE) ROTATION_TWO else ROTATION_ONE
                }
            )
        }
        if (rotationCategoriesAnimation.value == ROTATION_TWO) {
            filterByCategoryList(
                categories = categories,
                onCheckChangeListener = onCheckChangeListener,
            )
        }
        item {
            Spacer(modifier = Modifier.height(height = 142.dp))
        }
    }
}

@Composable
private fun FilterByDateHeader(
    rotation: Float,
    onClick: (Float) -> Unit,
) {
    Column {
        RowFieldView(
            modifier = Modifier
                .padding(all = PaddingTwo),
            content = {
                TextLabelStrong(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.fragment_filters_text_label_filter_by_date),
                )
            },
            end = {
                IconView(
                    modifier = Modifier.rotate(rotation),
                    icon = painterResource(id = R.drawable.ic_arrow_down),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            onClick = {
                onClick.invoke(rotation)
            }
        )
        HorizontalDivider()
    }
}

private fun LazyListScope.filterByDateList(
    filterByDateList: List<FilterByDateItem>,
    onSelectedFilterDate: (FilterByDateEnum) -> Unit,
) {
    itemsIndexed(filterByDateList) { index: Int, item: FilterByDateItem ->
        ItemFilterByDate(
            item = item,
            onClick = onSelectedFilterDate,
            modifier = Modifier.padding(horizontal = PaddingTwo)
        )
    }
}

@Composable
private fun FilterByCategoriesHeader(
    rotation: Float,
    onClick: (Float) -> Unit,
) {
    Column {
        RowFieldView(
            modifier = Modifier
//            .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(all = PaddingTwo),
            content = {
                TextLabelStrong(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.fragment_filters_text_label_filter_by_categories)
                )
            },
            end = {
                IconView(
                    modifier = Modifier.rotate(rotation),
                    icon = painterResource(id = R.drawable.ic_arrow_down),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            onClick = {
                onClick.invoke(rotation)
            }
        )
        HorizontalDivider()
    }
}


private fun LazyListScope.filterByCategoryList(
    categories: List<Category>,
    onCheckChangeListener: (Boolean, Category) -> Unit,
) {
    itemsIndexed(categories) { _: Int, item: Category ->
        ItemCategoryDefaultView(
            item = item,
            onCheckChangeListener = { isChecked: Boolean ->
                onCheckChangeListener(isChecked, item)
            },
            modifier = Modifier.padding(horizontal = PaddingTwo)
        )
    }
}


@Composable
private fun ItemFilterByDate(
    item: FilterByDateItem,
    onClick: (FilterByDateEnum) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickableNoRippleEffect {
                onClick(item.type)
            }
    ) {
        TextDefault(
            text = item.label,
            modifier = Modifier
                .padding(vertical = PaddingTwo)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )
        HorizontalDivider()
    }
}