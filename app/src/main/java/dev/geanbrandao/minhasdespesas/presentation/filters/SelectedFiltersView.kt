package dev.geanbrandao.minhasdespesas.presentation.filters

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.presentation.components.RowFieldView
import br.dev.geanbrandao.common.presentation.components.icon.IconType
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.text.TextLabelSmall
import br.dev.geanbrandao.common.presentation.resources.PaddingHalf
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import dev.geanbrandao.minhasdespesas.CategoryIconUtils
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.domain.model.Category
import kotlinx.parcelize.Parcelize

@Composable
fun SelectedFiltersView(
    list: List<SelectedFilter>,
    onRemoveFilter: (SelectedFilter) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyRow(modifier = modifier) {
        itemsIndexed(list) { index, item ->
            ItemSelectedFilter(
                item = item,
                onRemoveFilter = {
                    onRemoveFilter(item)
                }
            )
        }
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
            val iconId: Int = if (item is SelectedFilter.CategoryType) {
                CategoryIconUtils.getCategoryIcon(item.category.icon)
            } else {
                R.drawable.ic_calendar
            }
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
        end = {
//            IconView(
//                icon = painterResource(id = R.drawable.ic_close),
//                tint = color,
//                iconType = IconType.Small,
//                onClick = {
//                    onRemoveFilter()
//                }
//            )
        }
    )
}

@Preview
@Composable
fun SelectedFiltersViewPreview() {
    SelectedFiltersView(
        listOf(
            SelectedFilter.DateType("Dia xpto", FilterDate(0L, null, "", FilterByDateEnum.ALL)),
            SelectedFilter.CategoryType(
                label = "Categoria",
                category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
            )
        ),
        {}
    )
}

@Parcelize
sealed class SelectedFilter(open val label: String): Parcelable {
    data class DateType(
        override val label: String,
        val date: FilterDate,
    ) : SelectedFilter(label)

    data class CategoryType(
        override val label: String,
        val category: Category,
    ) : SelectedFilter(label)
}