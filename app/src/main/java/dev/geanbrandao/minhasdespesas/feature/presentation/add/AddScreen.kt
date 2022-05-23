package dev.geanbrandao.minhasdespesas.feature.presentation.add

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import dev.geanbrandao.minhasdespesas.common.components.texts.TextDateDay
import dev.geanbrandao.minhasdespesas.common.components.texts.TextLabelInput
import dev.geanbrandao.minhasdespesas.common.components.toolbar.AppToolbar
import dev.geanbrandao.minhasdespesas.common.utils.InputHandle.formatValueAsMoney
import dev.geanbrandao.minhasdespesas.feature.domain.model.SelectedCategoriesArg
import dev.geanbrandao.minhasdespesas.feature.presentation.add.components.ViewCalendarDate
import dev.geanbrandao.minhasdespesas.feature.presentation.add.components.ViewCategoriesList
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen.util.navigateWithArgument
import dev.geanbrandao.minhasdespesas.ui.theme.CornersDefault
import dev.geanbrandao.minhasdespesas.ui.theme.MarginThree
import dev.geanbrandao.minhasdespesas.ui.theme.MarginTwo
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate

@Composable
fun AddScreen(
    navHostController: NavHostController,
    viewModel: AddScreenViewModel = hiltViewModel()
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
        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        )
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
        mutableStateListOf("Restaurante", "Carro", "Lazer", "Familia")
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
                focusRequester = focusRequester
            )
            SpacerFour()
            InputTextSingleLine(
                inputValue = inputName,
                stringId = R.string.fragment_add_edit_expense_label_input_name,
            )
            SpacerFour()
            TextLabelInput(stringId = R.string.fragment_add_edit_expense_label_calendar)
            SpacerTwo()
            ViewCalendarDate(calendarState, isDataPickerVisible)
            SpacerFour()
            InputMultiLine(
                inputValue = inputDescription,
                stringId = R.string.fragment_add_edit_expense_label_input_description,
                modifier = Modifier.height(height = InputMultilineHeight)
            )
            SpacerFour()
            ViewCategoriesList(data = list, onClick = {
                navHostController.navigateWithArgument(
                    Screen.Categories.route,
                    keyArg = "SELECTED_CATEGORIES",
                    SelectedCategoriesArg(viewModel.getSelectedCategories())
                )
            })
            list.toCollection(arrayListOf())
            SpacerFour()
            SpacerFill(modifier = Modifier.weight(1f))
            ButtonDefault(stringId = R.string.button_default_text_save, onClick = {

            })
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

// todo extract to a different location
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

// todo extract to a different location
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
            TextDateDay(text = date.dayOfMonth.toString())
        }
    }
}

private const val CORNER_SELECTED = 50
private const val CORNER_UNSELECTED = 2