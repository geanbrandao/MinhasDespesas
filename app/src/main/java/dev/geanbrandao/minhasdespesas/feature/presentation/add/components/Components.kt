package dev.geanbrandao.minhasdespesas.feature.presentation.add.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.icons.IconDefault
import dev.geanbrandao.minhasdespesas.common.components.icons.IconInput
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerFill
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.utils.extensions.toStringDateFormatted
import dev.geanbrandao.minhasdespesas.feature.presentation.categories.components.ListCategoriesItemSmall
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
            contentDescription = null,
            modifier = Modifier.align(alignment = CenterVertically)
        )
        SpacerFill(modifier = Modifier.weight(weight = 1f))
        Text(
            text = calendarState.selectionState.selection.firstOrNull()?.toStringDateFormatted()
                .orEmpty(),
            modifier = Modifier
                .align(alignment = CenterVertically)
                .clickable {
                    isDataPickerVisible.value = true
                },
            color = MaterialTheme.colorScheme.secondary
        )
        SpacerTwo()
        IconDefault(
            iconId = R.drawable.ic_arrow_right,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(alignment = CenterVertically)
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
        calendarState = calendarState,
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
    onClick: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        IconInput(
            iconId = R.drawable.ic_tag_default,
            contentDescription = null,
            modifier = Modifier.align(alignment = CenterVertically)
        )
        SpacerTwo()
        ListCategoriesItemSmall(data = data, modifier = Modifier.weight(weight = 1f))
        SpacerTwo()
        IconDefault(
            iconId = R.drawable.ic_arrow_right,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(alignment = CenterVertically)
        )
    }
}