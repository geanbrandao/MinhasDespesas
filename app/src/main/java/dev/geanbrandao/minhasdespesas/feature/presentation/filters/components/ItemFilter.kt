package dev.geanbrandao.minhasdespesas.feature.presentation.filters.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.icons.IconDefault
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerOne
import dev.geanbrandao.minhasdespesas.common.components.texts.TextDefault
import dev.geanbrandao.minhasdespesas.common.utils.extensions.clickableRoundedEffect
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterEnum
import dev.geanbrandao.minhasdespesas.feature.domain.model.ActiveFiltersSimpleModel
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.ITEM_FILTER_ICON_CLOSE
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.ITEM_FILTER_ROOT
import dev.geanbrandao.minhasdespesas.ui.theme.ItemFilterCorners
import dev.geanbrandao.minhasdespesas.ui.theme.MarginOne
import dev.geanbrandao.minhasdespesas.ui.theme.MarginTwo
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingHalf

@Composable
fun ItemFilter(
    item: ActiveFiltersSimpleModel,
    onClickItemFilter: () -> Unit,
) {
//    val categoryName = when (item.typeFilter) {
//        TypeFilterEnum.DATE -> item.date.orEmpty()
//        TypeFilterEnum.CATEGORY -> item.categoryDb?.name.orEmpty()
//    }
    Card(
        shape = RoundedCornerShape(size = ItemFilterCorners),
        modifier = Modifier
            .testTag(ITEM_FILTER_ROOT)
            .padding(end = MarginTwo),
//        backgroundColor = MaterialTheme.colorScheme.secondary,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = PaddingHalf,
                    end = MarginOne,
                    top = MarginOne,
                    bottom = MarginOne
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
//            TextDefault(
//                text = categoryName,
//                textColor = MaterialTheme.colorScheme.onSecondary,
//            )
            SpacerOne()
            IconDefault(
                R.drawable.ic_close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .testTag(ITEM_FILTER_ICON_CLOSE)
                    .align(alignment = Alignment.CenterVertically)
                    .clickableRoundedEffect {
                        onClickItemFilter()
                    }
            )
        }
    }
}

//@Preview("Item Filter")
//@Composable
//fun Preview() {
//    ItemFilter(
//        ActiveFiltersSimpleModel(
//            TypeFilterEnum.DATE,
//            "Mês atual",
//            null
//        )
//    ) {}
//}
