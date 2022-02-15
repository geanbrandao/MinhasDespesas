package dev.geanbrandao.minhasdespesas.feature.filters.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.icons.IconDefault
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.components.texts.TextItemCategory
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun ItemCategory(
    item: String,
    color: Color,
    onCheckChange: (isChecked: Boolean) -> Unit,
    @DrawableRes iconId: Int = R.drawable.ic_tag_default,
) {
    val isChecked = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .clickable {
                isChecked.value = isChecked.value.not()
                onCheckChange(isChecked.value)
            }
            .fillMaxWidth()
            .padding(vertical = PaddingDefault, horizontal = PaddingDefault)
    ) {
        IconDefault(
            iconId = iconId,
            contentDescription = stringResource(id = R.string.content_description_item_category_item),
            tint = color,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        SpacerTwo()
        TextItemCategory(
            categoryName = item,
            modifier = Modifier
                .weight(weight = 1f)
                .align(alignment = Alignment.CenterVertically),
            color = color
        )
        SpacerTwo()
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.onSecondaryContainer,
                uncheckedColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f),
                checkmarkColor = MaterialTheme.colorScheme.background,
                disabledColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = ContentAlpha.disabled),
                disabledIndeterminateColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(
                    alpha = ContentAlpha.disabled
                )
            )
        )
    }
}

@Preview("Item Category")
@Composable
fun ItemCategoryPreview() {
    ItemCategory("Category name", color = MaterialTheme.colorScheme.onSurfaceVariant, {})
}
