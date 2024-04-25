package dev.geanbrandao.minhasdespesas.presentation.categories

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerThree
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.BgSwipeToDelete
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.BgSwipeToEdit
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.DragAnchors
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.RevealState
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.SwipeToReveal
import br.dev.geanbrandao.common.presentation.resources.MarginOne
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.presentation.categories.ListCategoryEnum.DEFAULT
import dev.geanbrandao.minhasdespesas.presentation.categories.ListCategoryEnum.SMALL
import dev.geanbrandao.minhasdespesas.ui.theme.MarginFour
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

enum class ListCategoryEnum {
    SMALL,
    DEFAULT,
}

sealed class ListCategoryType(val type: ListCategoryEnum) {
    data class Small(val list: List<Category>) : ListCategoryType(SMALL)
    data class Default(val list: List<Category>) : ListCategoryType(DEFAULT)
}

//@Composable
//fun ListCategoryView(
//    listCategoryType: ListCategoryType,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit = {},
//    onDeleteClicked: (id: Long) -> Unit = {},
//    onEditClicked: (id: Long) -> Unit = {},
//) {
//    when (listCategoryType) {
//        is ListCategoryType.Default -> {
//            ListCategoryDefault(
//                list = listCategoryType.list,
//                modifier = modifier,
//                onDeleteClicked = onDeleteClicked,
//                onEditClicked = onEditClicked,
//            )
//        }
//
//        is ListCategoryType.Small -> {
//            ListCategorySmall(
//                list = listCategoryType.list,
//                modifier = modifier
//            )
//        }
//    }
//
//}

@Composable
fun ListCategorySmallView(
    list: List<Category>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = MarginOne)
    ) {
        itemsIndexed(
            items = list,
            key = { _: Int, item: Category ->
                item.hashCode()
            }
        ) { _: Int, item ->
            ItemCategorySmallView(item = item)
        }
    }
}

@Composable
fun ListCategoryDefaultView(
    list: List<Category>,
    onDeleteClicked: (id: Long) -> Unit,
    onEditClicked: (id: Long) -> Unit,
    onCheckChangeListener: (isChecked: Boolean, item: Category) -> Unit,
    modifier: Modifier = Modifier,
    isSwipeEnabled: Boolean = true,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = MarginFour),
    ) {
        item {
            if (isSwipeEnabled) {
                SpacerThree()
            }
        }
        itemsIndexed(list) { index, item ->
            if (item.canRemove && isSwipeEnabled) {
                SwipeTo(
                    isLastItem = index == list.lastIndex,
                    item = item,
                    onDeleteClicked = onDeleteClicked,
                    onEditClicked = onEditClicked,
                    onCheckChangeListener = onCheckChangeListener,
                )
            } else {
                ItemCategoryDefaultView(
                    item = item,
                    isLastItem = index == list.lastIndex,
                    onCheckChangeListener = { isChecked: Boolean ->
                        onCheckChangeListener(isChecked, item)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun SwipeTo(
    item: Category,
    isLastItem: Boolean,
    onDeleteClicked: (id: Long) -> Unit,
    onEditClicked: (id: Long) -> Unit,
    onCheckChangeListener: (isChecked: Boolean, item: Category) -> Unit,
) {

    val density = LocalDensity.current
    val maxSwipeWidth = with(density) { 56.dp.toPx() }
    val state = remember {
        RevealState(
            initialValue = DragAnchors.Default,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    SwipeToReveal(
        state = state,
        background = {
            if (state.dismissDirection == DragAnchors.Start) {
                BgSwipeToEdit {
                    onEditClicked(item.categoryId)
                }
            } else if (state.dismissDirection == DragAnchors.End) {
                BgSwipeToDelete {
                    onDeleteClicked(item.categoryId)
                }
            }
        },
        dismissContent = {
            ItemCategoryDefaultView(
                item = item,
                isLastItem = isLastItem,
                onCheckChangeListener = { isChecked: Boolean ->
                    onCheckChangeListener(isChecked, item)
                }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        maxSwipeWidth = maxSwipeWidth,
    )
}

@Preview
@Composable
private fun ListCategoryViewPreview() {
    val list = createCategoryHelper()

    Column {
        ListCategorySmallView(
            list = list
        )
        ListCategoryDefaultView(
            list = list,
            onDeleteClicked = {

            },
            onEditClicked = {

            },
            onCheckChangeListener = { isChecked: Boolean, item: Category ->
                
            }
        )
    }
}


@Composable
fun createCategoryHelper() = listOf(
    Category(
        0,
        "Casa",
        "ic_tag",
        false,
    ),
    Category(
        1,
        "Educação",
        "ic_tag",
        false,
    ),
    Category(
        2,
        "Eletrônicos",
        "ic_tag",
        false,
    ),
    Category(
        3,
        "Outros",
        "ic_tag",
        false,
    ),
    Category(
        4,
        "Restaurante",
        "ic_tag",
        false,
    ),
)