package dev.geanbrandao.minhasdespesas.feature.add.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.ListFilters
import dev.geanbrandao.minhasdespesas.common.components.icons.IconInput
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.utils.extensions.toStringDateFormatted
import dev.geanbrandao.minhasdespesas.ui.theme.MarginTwo
import dev.geanbrandao.minhasdespesas.ui.theme.SizeIconDefault
import dev.geanbrandao.minhasdespesas.ui.theme.SizeIconLarge
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState

@Composable
fun ViewCalendarDate(
    calendarState: CalendarState<DynamicSelectionState>,
    isDataPickerVisible: MutableState<Boolean>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconInput(
            iconId = R.drawable.ic_calendar,
            iconSize = SizeIconLarge,
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            text = calendarState.selectionState.selection.firstOrNull()?.toStringDateFormatted()
                .orEmpty(),
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .clickable {
                    isDataPickerVisible.value = true
                },
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.size(size = MarginTwo))
        IconInput(
            iconId = R.drawable.ic_arrow_right,
            iconSize = SizeIconDefault,
            contentDescription = null,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun ViewCalendarPreview() {
    val calendarState = rememberSelectableCalendarState()
    val isDataPickerVisible = remember {
        mutableStateOf(false)
    }
    ViewCalendarDate(
        calendarState = rememberSelectableCalendarState(),
        isDataPickerVisible = isDataPickerVisible,
    )
}

@Preview
@Composable
fun ViewCategoriesListPreview() {
    val list = remember {
        mutableStateListOf<String>("filtro 1", "filtro 2", "filtro 3")
    }
    ViewCategoriesList(list) {

    }
}

@Composable
fun ViewCategoriesList(
    data: SnapshotStateList<String>,
    onClickItemFilter: (index: Int) -> Unit,
) {

    Row(modifier = Modifier.fillMaxWidth()) {
        IconInput(
            iconId = R.drawable.ic_tag_default,
            iconSize = SizeIconLarge,
            contentDescription = null,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        SpacerTwo()
        ListFilters(data = data, modifier = Modifier.weight(1f), onClickItemFilter = {})
        SpacerTwo()
        IconInput(
            iconId = R.drawable.ic_arrow_right,
            iconSize = SizeIconDefault,
            contentDescription = null,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}