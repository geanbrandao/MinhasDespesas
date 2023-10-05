package dev.geanbrandao.minhasdespesas.presentation.addexpenses

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.presentation.categories.CategoryItem
import dev.geanbrandao.minhasdespesas.presentation.categories.ListCategoryType
import dev.geanbrandao.minhasdespesas.presentation.categories.ListCategoryView
import java.time.Instant
import java.time.ZoneId
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddExpenseScreen(
    navHostController: NavHostController,
    viewModel: AddExpenseViewModel = koinViewModel(),
) {
    // aqui vao ficar os dados da viewModel
    val categories = viewModel.categories.collectAsState()
    AddExpenseScreenView(
        navHostController = navHostController,
        selectedCategories = categories.value,
    )
}


@Composable
fun createCategoryHelper() = listOf(
    CategoryItem(
        0,
        "Casa",
        painterResource(id = R.drawable.ic_house),
        false,
    ),
    CategoryItem(
        1,
        "Educação",
        painterResource(id = R.drawable.ic_education),
        false,
    ),
    CategoryItem(
        2,
        "Eletrônicos",
        painterResource(id = R.drawable.ic_computer),
        false,
    ),
    CategoryItem(
        3,
        "Outros",
        painterResource(id = R.drawable.ic_others),
        false,
    ),
    CategoryItem(
        4,
        "Restaurante",
        painterResource(id = R.drawable.ic_restaurant),
        false,
    ),
)

@Composable
private fun AddExpenseScreenView(
    navHostController: NavHostController,
    selectedCategories: List<Category> = emptyList(),
) {
    val context = LocalContext.current

//    val selectedCategories = createCategoryHelper() // todo viewModel responsibility
//    val selectedCategories = listOf<CategoryItem>() // todo viewModel responsibility
    val isAddEnabled = remember { mutableStateOf(true) } // todo viewModel responsibility
    val scrollState = rememberScrollState()
    BaseScreen(
        header = {
            ToolbarView(
                navHostController = navHostController,
                toolbarTitle = stringResource(id = R.string.fragment_add_edit_expense_toolbar_title)
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
                ExpenseValueField()
                SpacerTwo()
                ExpenseNameField()
                SpacerFour()
                ExpenseDateField(
                    // todo não existe data padrão ainda.
                    onSelectedDate = { dateMillis ->

                    },
                )
                SpacerFour()
                ExpenseDescriptionField()
                SpacerFour()
                ExpenseCategoriesField(
                    selectedCategories = selectedCategories,
                    openCategoryScreens = {
                        navHostController.navigate(Screen.Categories.route)
                    }
                )
                SpacerFill(modifier = Modifier.weight(1f))
                ButtonView(
                    text = stringResource(id = R.string.button_default_text_add),
                    isEnabled = isAddEnabled.value,
                    onClick = {},
                    modifier = Modifier.padding(top = PaddingThree)
                )
                SpacerFour()
            }
        }
    )
}

@Composable
fun ExpenseValueField() {
    val inputState = InputViewState(
        textLabel = stringResource(id = R.string.fragment_add_edit_expense_label_input_value),
        textInput = "0,00",
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
fun ExpenseNameField() {
    val inputState = InputViewState(
        textLabel = stringResource(id = R.string.fragment_add_edit_expense_label_input_name),
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
fun ExpenseDescriptionField() {
    val inputState = InputViewState(
        textLabel = stringResource(id = R.string.fragment_add_edit_expense_label_input_description),
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
    onSelectedDate: (timeMillis: Long?) -> Unit,
) {
    val defaultLocale = LocalConfiguration.current.locales[0]
    val isPickerVisible = remember { mutableStateOf(false) }
    val dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() }
    val datePickerState = rememberDatePickerState()

    val selectDateText = stringResource(id = R.string.dialog_picker_select_a_date)
    val dateVerbose: MutableState<String?> = remember { mutableStateOf(selectDateText) }



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
        onSelectedDate = {
            dateVerbose.value = formatVerboseDateMillis(
                dateFormatter = dateFormatter,
                defaultLocale = defaultLocale,
                dateMillis = it
            )
            onSelectedDate(it)
        }
    )
}

@Composable
fun ExpenseCategoriesField(
    selectedCategories: List<Category>,
    openCategoryScreens: () -> Unit,
) {
    val color = MaterialTheme.colorScheme.onBackground
    RowFieldView(
        start = {
            IconView(icon = painterResource(id = R.drawable.ic_tag_default), tint = color)
        },
        content = {
            if (selectedCategories.isNotEmpty()) { // selected categories
                ListCategoryView(
                    listCategoryType = ListCategoryType.Small(list = selectedCategories),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = PaddingOne),
                    onClick = openCategoryScreens
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
        onClick = openCategoryScreens,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
private fun formatVerboseDateMillis(
    dateFormatter: DatePickerFormatter,
    defaultLocale: java.util.Locale,
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
    onSelectedDate: (timeMillis: Long?) -> Unit,
) {
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.dialog_picker_select_a_date)
    if (datePickerIsVisible) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                ButtonView(
                    text = stringResource(id = R.string.button_default_text_ok2),
                    buttonType = ButtonType.Action
                ) {
                    datePickerState.selectedDateMillis?.let {
                        onSelectedDate(it)
                        onDismiss()
                    } ?: run {
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                dateFormatter = dateFormatter
            ) // todo talvez tirar o toggle mode
        }
    }
}

fun getFormattedDateFromMilli(date: Long): String {
    val localDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDateTime()
    return localDate.toStringDateFormatted()
}

@Preview
@Composable
fun AddExpenseScreenViewPreview() {
    AddExpenseScreenView(navHostController = rememberNavController())
}