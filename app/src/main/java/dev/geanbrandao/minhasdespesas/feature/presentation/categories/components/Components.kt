package dev.geanbrandao.minhasdespesas.feature.presentation.categories.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.dividers.DividerInput
import dev.geanbrandao.minhasdespesas.common.components.icons.IconDefault
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerOne
import dev.geanbrandao.minhasdespesas.common.components.texts.TextDefault
import dev.geanbrandao.minhasdespesas.common.utils.extensions.getIconIdFromString
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.ui.theme.CornersItemCategorySmall
import dev.geanbrandao.minhasdespesas.ui.theme.MarginOne
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingHalf

@Composable
fun CategoryItem(
    item: CategoryDb,
    isLastItem: Boolean = false,
    color: Color = MaterialTheme.colorScheme.secondary,
    onCheckChangeListener: (isChecked: Boolean) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = PaddingHalf, vertical = PaddingHalf)
        ) {
            val iconId = context.getIconIdFromString(name = item.icon)
            IconDefault(
                iconId = iconId,
                tint = color,
                contentDescription = null,
                modifier = Modifier.align(alignment = CenterVertically),
                onIconClick = null,
            )
            SpacerOne()
            TextDefault(
                text = item.name,
                textColor = color,
                modifier = Modifier
                    .weight(weight = 1F)
                    .align(alignment = CenterVertically)
            )
            SpacerOne()
            CheckBoxDefault { isChecked: Boolean ->

            }
        }
        if (isLastItem.not()) {
            DividerInput()
        }
    }
}

@Composable
fun CheckBoxDefault(onCheckChangeListener: (isChecked: Boolean) -> Unit) {
    val checkState = remember { mutableStateOf(false) }
    Checkbox(
        checked = checkState.value,
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.secondary,
            checkmarkColor = MaterialTheme.colorScheme.onSecondary,
            uncheckedColor = MaterialTheme.colorScheme.secondary,
        ),
        onCheckedChange = {
            checkState.value = checkState.value.not()
            onCheckChangeListener(checkState.value)
        })
}

@Composable
fun CategoryItemSmall(
    categoryName: String,
    @DrawableRes iconId: Int = R.drawable.ic_tag_default,
    color: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    Row(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(size = CornersItemCategorySmall),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            .padding(horizontal = PaddingHalf, vertical = PaddingHalf)
    ) {
        IconDefault(iconId = iconId, tint = color, contentDescription = null, onIconClick = null)
        SpacerOne()
        TextDefault(
            text = categoryName,
            textColor = color,
            modifier = Modifier
                .align(alignment = CenterVertically)
        )
    }
}

@Composable
fun ListCategoriesItemSmall(
    data: SnapshotStateList<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = MarginOne
        )
    ) {
        itemsIndexed(
            items = data,
            key = { _: Int, item: String ->
                item.hashCode()
            }
        ) { _: Int, item ->
            CategoryItemSmall(categoryName = item)
        }
    }
}

@Preview
@Composable
fun ListCategoriesItemSmallPreview() {
    val data = remember {
        mutableStateListOf("Restaurante", "Carro", "Lazer", "Familia")
    }
    ListCategoriesItemSmall(data = data)
}

@Preview
@Composable
fun CategoryItemSmallPreview() {
    CategoryItemSmall(categoryName = "Category")
}

@Preview
@Composable
fun CategoryItemPreview() {
    val checkBoxState = remember {
        mutableStateOf(false)
    }
    CategoryItem(
        item = CategoryDb(
            categoryId = 1L,
            name = "Acadêmia",
            icon = "ic_gym",
            canRemove = false
        )
    ) { isChecked: Boolean ->

    }

}