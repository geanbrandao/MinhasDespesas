package dev.geanbrandao.minhasdespesas.presentation.filters

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.domain.CategoryIconUtils
import br.dev.geanbrandao.common.presentation.components.RowFieldView
import br.dev.geanbrandao.common.presentation.components.icon.IconType
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.text.TextAction
import br.dev.geanbrandao.common.presentation.components.text.TextLabelSmall
import br.dev.geanbrandao.common.presentation.resources.PaddingHalf
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.domain.model.Category
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Composable
fun SelectedFiltersView(
    list: List<SelectedFilter>,
    onCleanFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LazyRow(modifier = modifier.weight(1f)) {
            item {
                Spacer(modifier = Modifier.size(PaddingTwo))
            }
            itemsIndexed(list) { index, item ->
                ItemSelectedFilter(
                    item = item,
                    onRemoveFilter = {
//                        onRemoveFilter(item)
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.size(PaddingOne))
            }
        }
        Spacer(modifier = Modifier.size(size = PaddingHalf))
        TextAction(
            text = stringResource(id = R.string.fragment_filters_button_clean).plus("(${list.size})"),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.clickable { onCleanFilters() }
        )
        Spacer(modifier = Modifier.size(size = PaddingOne))
    }
}

@Composable
private fun ItemSelectedFilter(
    item: SelectedFilter,
    onRemoveFilter: () -> Unit,
) {

    val colorBackground = MaterialTheme.colorScheme.secondaryContainer
    val color = MaterialTheme.colorScheme.onSecondaryContainer

    RowFieldView(
        modifier = Modifier
            .height(height = 32.dp)
            .padding(end = PaddingOne)
            .background(color = colorBackground, shape = RoundedCornerShape(size = 8.dp))
            .padding(vertical = PaddingHalf, horizontal = PaddingOne),
        start = {
            val iconId: Int = item.category?.let {
                CategoryIconUtils.getCategoryIcon(it.icon)
            } ?: R.drawable.ic_calendar
            IconView(
                icon = painterResource(id = iconId),
                tint = color,
                iconType = IconType.Small,
                onClick = {
                    onRemoveFilter()
                }
            )
        },
        content = {
            Spacer(modifier = Modifier.size(PaddingHalf))
            TextLabelSmall(
                text = item.label,
                color = color,
            )
        },
    )
}

@Preview
@Composable
fun SelectedFiltersViewPreview() {
    SelectedFiltersView(
        listOf(
            SelectedFilter(
                date = FilterDate(0L, null, "", FilterByDateEnum.YEAR),
                null,
            ),
            SelectedFilter(
                date = null,
                category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
            ),
            SelectedFilter(
                date = null,
                category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
            ),
            SelectedFilter(
                date = null,
                category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
            ),
            SelectedFilter(
                date = null,
                category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
            ),
        ),
        {}
    )
}

@Serializable
@Parcelize
data class SelectedFilter(
    val date: FilterDate? = null,
    val category: Category? = null,
) : Parcelable {
    val label: String
        get() {
            return date?.label ?: category?.name ?: ""
        }
}

//@Parcelize
//sealed class SelectedFilter(open val label: String): Parcelable {
//    data class DateType(
//        override val label: String,
//        val date: FilterDate,
//    ) : SelectedFilter(label)
//
//    data class CategoryType(
//        override val label: String,
//        val category: Category,
//    ) : SelectedFilter(label)
//}