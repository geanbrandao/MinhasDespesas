package dev.geanbrandao.minhasdespesas.presentation.addexpenses

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import br.dev.geanbrandao.common.domain.isNotNull
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.components.RowFieldView
import br.dev.geanbrandao.common.presentation.components.button.ButtonType
import br.dev.geanbrandao.common.presentation.components.button.ButtonView
import br.dev.geanbrandao.common.presentation.components.icon.IconType
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.inputs.InputType
import br.dev.geanbrandao.common.presentation.components.inputs.InputView
import br.dev.geanbrandao.common.presentation.components.inputs.InputViewData
import br.dev.geanbrandao.common.presentation.components.inputs.InputViewState
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerFour
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerThree
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerTwo
import br.dev.geanbrandao.common.presentation.components.text.TextDefault
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarView
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import br.dev.geanbrandao.common.presentation.resources.PaddingThree
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerFill
import dev.geanbrandao.minhasdespesas.common.utils.extensions.toStringDateFormatted
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.presentation.categories.ListCategorySmallView
import java.time.Instant
import java.time.ZoneId
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddExpenseScreen(
    // arguments
//    argSelectedCategories: String?,
//    argExpenseId: String?,
    // navigation
//    openCategories: (selectedCategories: String?) -> Unit,
//    goBack: () -> Unit,
    // viewModel
    viewModel: AddExpenseViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

//    ObserveAsEvents(
//        flow = viewModel.insertedOrUpdated,
//        onEvent = {
//            if (it) { goBack() }
//        }
//    )

//    LaunchedEffect(key1 = argSelectedCategories) {
////        viewModel.updateSelectedCategories(argSelectedCategories)
//    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getSelectedCategoriesIds()
        }
    }

    AddExpenseScreenView(
        uiState = uiState.value,
        onUiStateIsUpdated = {
            viewModel.updateUiState(it)
        },
        onAddClick = {
            if (uiState.value.isEdit) {
                viewModel.updateExpense()
            } else {
                viewModel.addExpense()
            }
        },
        goToCategoryScreen = {
            viewModel.navigateToCategoriesScreen()
        },
        onBackButtonClicked = {
            viewModel.navigateBack()
        },
    )
}

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(onEvent)
        }
    }
}

@Composable
private fun AddExpenseScreenView(
    uiState: AddExpenseScreenState,
    onUiStateIsUpdated: (uiState: AddExpenseScreenState) -> Unit,
    onAddClick: () -> Unit,
    onBackButtonClicked: () -> Unit,
    goToCategoryScreen: ()-> Unit,
) {
    val scrollState = rememberScrollState()

    BaseScreen(
        header = {
            ToolbarView(
                toolbarTitle = stringResource(id = R.string.fragment_add_edit_expense_toolbar_title),
                onBackButtonClicked = onBackButtonClicked,
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(horizontal = PaddingTwo)
                    .verticalScroll(scrollState)
            ) {
                SpacerThree()
                ExpenseValueField(
                    amount = uiState.amount,
                    onTextChange = { text: String ->
                        onUiStateIsUpdated(uiState.copy(amount = text))
                    }
                )
                SpacerTwo()
                ExpenseNameField(
                    name = uiState.name,
                    onTextChange = { text: String ->
                        onUiStateIsUpdated(uiState.copy(name = text))
                    }
                )
                SpacerFour()
                ExpenseDateField(
                    selectedDate = uiState.selectedDate,
                    onSelectDate = { dateMillis ->
                        dateMillis?.let {
                            onUiStateIsUpdated(uiState.copy(selectedDate = dateMillis))
                        }
                    },
                )
                SpacerFour()
                ExpenseDescriptionField(
                    description = uiState.description,
                    onTextChange = { text: String ->
                        onUiStateIsUpdated(uiState.copy(description = text))
                    }
                )
                SpacerFour()
                ExpenseCategoriesField(
                    selectedCategories = uiState.selectedCategories,
                    goToCategoryScreen = goToCategoryScreen,
                )
                SpacerFill(modifier = Modifier.weight(weight = 1f))
                ButtonView(
                    text = if (uiState.isEdit) {
                        stringResource(id = R.string.button_default_text_update)
                    } else {
                        stringResource(id = R.string.button_default_text_add)
                    },
                    isEnabled = uiState.isNextButtonEnabled,
                    onClick = onAddClick,
                    modifier = Modifier.padding(top = PaddingThree)
                )
                SpacerFour()
            }
        }
    )
}

@Composable
fun ExpenseValueField(
    amount: String,
    onTextChange: (text: String) -> Unit,
) {
    val inputState = InputViewState(
        textLabel = stringResource(id = R.string.fragment_add_edit_expense_label_input_value),
        textInput = amount,
//        selection = amount.length,
        onTextChange = onTextChange,
    )
    val inputData = InputViewData(
        leadingIcon = painterResource(id = R.drawable.ic_money),
        keyboardOptions = KeyboardOptions()
    )
    InputView(
        inputViewState = inputState,
        inputViewData = inputData,
        inputType = InputType.Money,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun ExpenseNameField(
    name: String,
    onTextChange: (text: String) -> Unit,
) {
    val inputState = InputViewState(
        textLabel = stringResource(id = R.string.fragment_add_edit_expense_label_input_name),
        textInput = name,
//        selection = name.length,
        onTextChange = onTextChange,
    )
    val inputData = InputViewData(
        leadingIcon = painterResource(id = R.drawable.ic_text),
        trailingIcon = painterResource(id = R.drawable.clear),
        keyboardOptions = KeyboardOptions()
    )
    InputView(
        inputViewState = inputState,
        inputViewData = inputData,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun ExpenseDescriptionField(
    description: String,
    onTextChange: (text: String) -> Unit = {},
) {
    val inputState = InputViewState(
        textLabel = stringResource(id = R.string.fragment_add_edit_expense_label_input_description),
        textInput = description,
//        selection = description.length,
        onTextChange = onTextChange,
    )
    val inputData = InputViewData()
    InputView(
        inputViewState = inputState,
        inputViewData = inputData,
        inputType = InputType.Large,
        modifier = Modifier.fillMaxWidth(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDateField(
    selectedDate: Long,
    onSelectDate: (timeMillis: Long?) -> Unit,
) {
    val defaultLocale = LocalConfiguration.current.locales[0]
    val isPickerVisible = remember { mutableStateOf(false) }
    val dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() }
    val datePickerState = rememberDatePickerState()

    val selectDateText = stringResource(id = R.string.dialog_picker_select_a_date)
    val dateVerbose: MutableState<String?> = remember { mutableStateOf(selectDateText) }

    // seleciona a data atual
    datePickerState.selectedDateMillis = selectedDate
    dateVerbose.value = formatVerboseDateMillis(
        dateFormatter = dateFormatter,
        defaultLocale = defaultLocale,
        dateMillis = selectedDate,
    )

    val color = MaterialTheme.colorScheme.onBackground
    RowFieldView(
        start = {
            IconView(
                icon = painterResource(id = R.drawable.ic_calendar),
                tint = color,
                iconType = IconType.Input,
            )
        },
        content = {
            TextDefault(
                text = dateVerbose.value.orEmpty(),
                textColor = color,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        },
        end = {
            IconView(
                icon = painterResource(id = R.drawable.ic_arrow_right),
                tint = color,
                iconType = IconType.Default,
            )
        },
        onClick = {
            isPickerVisible.value = true
        }
    )

    ExpenseDatePicker(
        datePickerIsVisible = isPickerVisible.value,
        datePickerState = datePickerState,
        dateFormatter = dateFormatter,
        onDismiss = {
            isPickerVisible.value = false
        },
        onSelectDate = {
            dateVerbose.value = formatVerboseDateMillis(
                dateFormatter = dateFormatter,
                defaultLocale = defaultLocale,
                dateMillis = it
            )
            onSelectDate(it)
        }
    )
}

@Composable
fun ExpenseCategoriesField(
    selectedCategories: List<Category>,
    goToCategoryScreen: () -> Unit,
) {
    val color = MaterialTheme.colorScheme.onBackground
    RowFieldView(
        start = {
            IconView(icon = painterResource(id = R.drawable.ic_tag_default), tint = color)
        },
        content = {
            if (selectedCategories.isNotEmpty()) { // selected categories
                ListCategorySmallView(
                    list = selectedCategories,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = PaddingOne),
                )
            } else { // label
                TextDefault(
                    text = stringResource(id = R.string.fragment_add_edit_expense_text_select_the_categories),
                    textColor = color,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                )
            }
        },
        end = {
            IconView(
                icon = painterResource(id = R.drawable.ic_arrow_right),
                tint = color,
                iconType = IconType.Default,
            )
        },
        onClick = goToCategoryScreen,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
private fun formatVerboseDateMillis(
    dateFormatter: DatePickerFormatter, // precisa usar esse cara para pegar a data correta
    defaultLocale: Locale,
    dateMillis: Long?,
) = dateFormatter.formatDate(
    dateMillis = dateMillis,
    locale = defaultLocale,
    forContentDescription = true
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePicker(
    datePickerIsVisible: Boolean,
    datePickerState: DatePickerState,
    dateFormatter: DatePickerFormatter,
    onDismiss: () -> Unit,
    onSelectDate: (timeMillis: Long) -> Unit,
) {

    if (datePickerIsVisible) {
        MyDataPickerDialog(
            datePickerState = datePickerState,
            datePickerRangeState = rememberDateRangePickerState(),
            onDismiss = onDismiss,
            onSelectDate = onSelectDate,
            onSelectRangeDate = { _: Long, _: Long -> }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                dateFormatter = dateFormatter
            ) // todo talvez tirar o toggle mode
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable // todo mover para um arquivo próprio
fun MyDataPickerDialog(
    datePickerState: DatePickerState,
    datePickerRangeState: DateRangePickerState,
    onDismiss: () -> Unit,
    onSelectDate: (timeMillis: Long) -> Unit,
    onSelectRangeDate: (startDate: Long, endDate: Long) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.dialog_picker_select_a_date)
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            ButtonView(
                text = stringResource(id = R.string.button_default_text_ok2),
                buttonType = ButtonType.Action
            ) {
                datePickerState.selectedDateMillis?.let {
                    onSelectDate(it)
                    onDismiss()
                } ?: run {
                    val startDate = datePickerRangeState.selectedStartDateMillis
                    val endDate = datePickerRangeState.selectedEndDateMillis
                    if (startDate.isNotNull() and endDate.isNotNull()) {
                        onSelectRangeDate(startDate!!, endDate!!)
                    } else {
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        },
        content = content
    )
}

fun getFormattedDateFromMilli(date: Long): String {
    val localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDateTime()
    return localDate.toStringDateFormatted()
}

@Preview
@Composable
fun AddExpenseScreenViewPreview() {
    val uiState = AddExpenseScreenState(
        amount = "32,55",
        name = "Maça",
        selectedDate = getCurrentTimeInMillis(),
        description = "Aquele negocio lá",
        selectedCategories = listOf(Category(
            categoryId = 1,
            name = "teste",
            icon = "ic_tag",
            canRemove = false,
            isChecked = false
        )),
        createdAt = 0L,
        expenseId = 0L,
    )
    AddExpenseScreenView(
        uiState = uiState,
        onUiStateIsUpdated = {},
        onAddClick = {},
        onBackButtonClicked = {},
        goToCategoryScreen = {},
    )
}