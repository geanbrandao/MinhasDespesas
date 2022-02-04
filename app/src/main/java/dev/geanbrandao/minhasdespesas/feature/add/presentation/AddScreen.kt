package dev.geanbrandao.minhasdespesas.feature.add.presentation

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.buttons.ButtonDefault
import dev.geanbrandao.minhasdespesas.common.components.inputs.InputMoney
import dev.geanbrandao.minhasdespesas.common.components.inputs.InputMultiLine
import dev.geanbrandao.minhasdespesas.common.components.inputs.InputMultilineHeight
import dev.geanbrandao.minhasdespesas.common.components.inputs.InputTextSingleLine
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerFill
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerFour
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.components.texts.TextLabelScreen
import dev.geanbrandao.minhasdespesas.common.components.toolbar.AppToolbar
import dev.geanbrandao.minhasdespesas.common.utils.InputHandle.formatValueAsMoney
import dev.geanbrandao.minhasdespesas.feature.add.presentation.components.ViewCalendarDate
import dev.geanbrandao.minhasdespesas.feature.add.presentation.components.ViewCategoriesList
import dev.geanbrandao.minhasdespesas.ui.theme.CornersDefault
import dev.geanbrandao.minhasdespesas.ui.theme.MarginThree
import dev.geanbrandao.minhasdespesas.ui.theme.MarginTwo
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate

@Composable
fun AddScreen(
    navHostController: NavHostController
) {
    val initialMonetaryValue = formatValueAsMoney(0.toString())

    val inputValue = remember {
        mutableStateOf(
            TextFieldValue(
                text = formatValueAsMoney(initialMonetaryValue),
                selection = TextRange(initialMonetaryValue.length)
            )
        )
    }

    val inputName = remember {
        mutableStateOf(TextFieldValue(text = ""))
    }

    val inputDescription = remember {
        mutableStateOf(TextFieldValue(text = ""))
    }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val isDataPickerVisible = remember {
        mutableStateOf(false)
    }

    val calendarState = rememberSelectableCalendarState(
        initialSelection = listOf(LocalDate.now())
    )

    val list = remember {
        mutableStateListOf("filtro 1", "filtro 2", "filtro 3", "filtro 4", "filtro 5")
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppToolbar(
            navHostController = navHostController,
            stringId = R.string.fragment_add_edit_expense_toolbar_title
        )
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize(1F)
                .padding(all = MarginThree)
                .verticalScroll(scrollState)
        ) {
            InputMoney(
                inputValue = inputValue,
                stringId = R.string.fragment_add_edit_expense_label_input_value,
                iconId = R.drawable.ic_money,
                focusRequester = focusRequester
            )
            SpacerFour()
            InputTextSingleLine(
                inputValue = inputName,
                stringId = R.string.fragment_add_edit_expense_label_input_name,
                iconId = R.drawable.ic_text,
            )
            SpacerFour()
            TextLabelScreen(stringId = R.string.fragment_add_edit_expense_label_calendar)
            SpacerTwo()
            ViewCalendarDate(calendarState, isDataPickerVisible)
            SpacerFour()
            InputMultiLine(
                inputValue = inputDescription,
                stringId = R.string.fragment_add_edit_expense_label_input_description,
                modifier = Modifier.height(height = InputMultilineHeight)
            )
            SpacerFour()
            ViewCategoriesList(data = list, onClickItemFilter = {})
            SpacerFour()
            SpacerFill(modifier = Modifier.weight(1f))
            ButtonDefault(stringId = R.string.button_default_text_save)
            if (isDataPickerVisible.value) {
                DatePicker(calendarState) {
                    isDataPickerVisible.value = false
                    if (calendarState.selectionState.selection.isEmpty()) {
                        calendarState.selectionState.selection = listOf(LocalDate.now())
                    }
                }
            }

        }
    }
}

@Composable
fun DatePicker(
    calendarState: CalendarState<DynamicSelectionState>,
    onDismissRequest: () -> Unit,
) {
    Box(modifier = Modifier.wrapContentSize()) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            SelectableCalendar(
                calendarState = calendarState,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(CornersDefault)
                    )
                    .padding(MarginTwo),
                dayContent = {
                    CustomDay(it)
                }
            )
        }
    }
}

@Composable
fun <T : SelectionState> CustomDay(
    state: DayState<T>,
    modifier: Modifier = Modifier,
    selectionColor: Color = MaterialTheme.colorScheme.secondary,
    currentDayColor: Color = MaterialTheme.colorScheme.primary,
    onClick: (LocalDate) -> Unit = {},
) {
    val date = state.date
    val selectionState = state.selectionState

    val isSelected = selectionState.isDateSelected(date)
    val cornerSize =
        animateIntAsState(targetValue = if (isSelected) CORNER_SELECTED else CORNER_UNSELECTED)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        elevation = if (state.isFromCurrentMonth) 4.dp else 0.dp,
        backgroundColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(cornerSize.value),
        border = if (state.isCurrentDay) BorderStroke(1.5.dp, currentDayColor) else null,
        contentColor = if (isSelected) selectionColor else MaterialTheme.colorScheme.onSurface
    ) {
        Box(
            modifier = Modifier.clickable {
                onClick(date)
                selectionState.onDateSelected(date)
            },
            contentAlignment = Alignment.Center,
        ) {
            Text(text = date.dayOfMonth.toString())
        }
    }
}

private const val CORNER_SELECTED = 50
private const val CORNER_UNSELECTED = 2