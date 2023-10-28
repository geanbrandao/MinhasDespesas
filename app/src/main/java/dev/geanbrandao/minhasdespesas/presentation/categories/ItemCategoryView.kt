package dev.geanbrandao.minhasdespesas.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.domain.clickableNoRippleEffect
import br.dev.geanbrandao.common.presentation.components.RowFieldView
import br.dev.geanbrandao.common.presentation.components.checkbox.CheckboxView
import br.dev.geanbrandao.common.presentation.components.icon.IconType
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerOne
import br.dev.geanbrandao.common.presentation.components.text.TextDefault
import br.dev.geanbrandao.common.presentation.components.text.TextSupporting
import br.dev.geanbrandao.common.presentation.resources.CornersSmall
import br.dev.geanbrandao.common.presentation.resources.MarginOne
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import dev.geanbrandao.minhasdespesas.CategoryIconUtils
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.dividers.DividerInput
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.presentation.categories.ItemCategoryEnum.DEFAULT
import dev.geanbrandao.minhasdespesas.presentation.categories.ItemCategoryEnum.SMALL

enum class ItemCategoryEnum {
    SMALL,
    DEFAULT,
}

sealed class ItemCategoryType(itemCategoryType: ItemCategoryEnum) {
    data object Small : ItemCategoryType(SMALL)
    data object Default : ItemCategoryType(DEFAULT)
}

//@Composable
//fun ItemCategoryView(
//    item: Category,
//    isLastItem: Boolean = false,
//    itemCategoryType: ItemCategoryType = ItemCategoryType.Default
//) {
//    val icon = getPainterIconFromString(item.icon)
//    when (itemCategoryType) {
//        ItemCategoryType.Default -> {
//            ItemCategoryDefault(
//                item = item,
//                icon = icon,
//                isLastItem = isLastItem
//            )
//        }
//
//        ItemCategoryType.Small -> {
//            ItemCategorySmall(item = item.name, icon = icon)
//        }
//    }
//}

@Composable
fun ItemCategorySmallView(
    item: Category,
) {
    val icon = getPainterIconFromString(item.icon)
    ItemCategorySmall(item = item.name, icon = icon)
}

@Composable
fun ItemCategoryDefaultView(
    item: Category,
    onCheckChangeListener: (isChecked: Boolean) -> Unit,
    isLastItem: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val icon = getPainterIconFromString(item.icon)
    ItemCategoryDefault(
        item = item,
        icon = icon,
        onCheckChangeListener = onCheckChangeListener,
        isLastItem = isLastItem,
        modifier = modifier
    )
}

@Composable
private fun getPainterIconFromString(iconIdName: String): Painter {
    val iconId = CategoryIconUtils.getCategoryIcon(iconIdName)
    return painterResource(id = iconId)
}

@Composable
private fun ItemCategorySmall(
    item: String,
    icon: Painter
) {
    val colorBackground = MaterialTheme.colorScheme.secondaryContainer
    val colorOnBackground = MaterialTheme.colorScheme.onSecondaryContainer
    Row(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(size = CornersSmall),
                color = colorBackground,
            )
            .padding(all = PaddingOne),
    ){
        IconView(
            icon = icon,
            tint = colorOnBackground,
            iconType = IconType.Small,
        )
        SpacerOne()
        TextSupporting(
            text = item,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
private fun ItemCategoryDefault(
    item: Category,
    icon: Painter,
    isLastItem: Boolean,
    onCheckChangeListener: (isChecked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
) {
    val color = MaterialTheme.colorScheme.secondary

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor),
    ) {
        RowFieldView(
            start = {
                Spacer(modifier = Modifier.size(4.dp))
                IconView(
                    icon = icon,
                    tint = color,
                )
            },
            content = {
                Spacer(modifier = Modifier.size(size = MarginOne))
                TextDefault(
                    text = item.name,
                    textColor = color,
                    modifier = Modifier.weight(1f)
                )
            },
            end = {
                CheckboxView(
                    isChecked = item.isChecked,
                    onCheckChangeListener = onCheckChangeListener
                )
            },
            onClick = {
                onCheckChangeListener(item.isChecked.not())
            }
        )
        if (isLastItem.not()) {
            DividerInput()
        }
    }
}

@Preview
@Composable
private fun ItemCategoryViewPreview() {
    val item = Category(categoryId = 0, name = "Restaurante", icon = "ic_tag")
    val item2 = Category(categoryId = 0, name = "Restaurante", icon = "ic_tag", isChecked = true)
    Column {
        ItemCategorySmall(item = "Restaurante", painterResource(id = R.drawable.ic_tag_default))
        ItemCategoryDefault(
            item = item,
            icon = painterResource(id = R.drawable.ic_tag_default),
            isLastItem = false,
            onCheckChangeListener = { isChecked: Boolean ->

            }
        )
        ItemCategoryDefault(
            item = item2,
            icon = painterResource(id = R.drawable.ic_tag_default),
            isLastItem = true,
            onCheckChangeListener = { isChecked: Boolean ->

            }
        )
    }
}