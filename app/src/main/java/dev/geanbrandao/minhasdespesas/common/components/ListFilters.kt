package dev.geanbrandao.minhasdespesas.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation.components.ItemFilter
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.MarginDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun ListFilters(
    data: SnapshotStateList<String>,
    onClickItemFilter: (index: Int) -> Unit,
) {

    val listFilters = remember {
        mutableStateOf(data)
    }
    if (listFilters.value.isEmpty()) {
        Text(
            text = "Nenhum filtro selecionado",
            style = AppTypography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(all = PaddingDefault),
        )
    } else {
        Row(
            modifier = Modifier.padding(all = PaddingDefault)
        ) {
            LazyRow(
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(
                    items = listFilters.value,
                    key = { _: Int, item: String ->
                        item.hashCode()
                    },
                ) { _, item ->
                    ItemFilter(item = item) {
                        onClickItemFilter(1)
                        data.remove(item)
                    }
                }
            }
        }

    }
}

@Composable
fun FiltersButton(
    activeFiltersSize: Int,
    modifier: Modifier,
    onClick: () -> Unit = {},
) {
    if (activeFiltersSize < 1) {
        Text(
            text = "Nenhum filtro selecionado",
            style = AppTypography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(all = PaddingDefault)
                .clickable { onClick() },
        )
    } else {
        Row(
            modifier = modifier
                .padding(all = PaddingDefault)
                .clickable { onClick() },
        ) {
            Icon(
                painterResource(id = R.drawable.ic_filters),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.size(MarginDefault))
            Text(
                text = "Filtros($activeFiltersSize)",
                modifier = Modifier
                    .align(alignment = CenterVertically),
                color = MaterialTheme.colorScheme.secondary,
                style = AppTypography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ListFiltersPreview() {
    val list = remember {
        mutableStateListOf<String>("filtro 1", "filtro 2", "filtro 3","filtro 1", "filtro 2", "filtro 3")
    }
    ListFilters(
        data = list,
        onClickItemFilter = {}
    )
}