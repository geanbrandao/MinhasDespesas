package dev.geanbrandao.minhasdespesas.presentation.filters

import android.content.Context
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.dev.geanbrandao.common.domain.PATTERN_SHORT_DATE
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import br.dev.geanbrandao.common.domain.getLongTimeMillis
import br.dev.geanbrandao.common.domain.getOffsetDateTime
import br.dev.geanbrandao.common.domain.isNotNull
import br.dev.geanbrandao.common.domain.toBrazilianDateFormat
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.components.button.ButtonAction
import br.dev.geanbrandao.common.presentation.components.button.ButtonPrimary
import br.dev.geanbrandao.common.presentation.components.datepicker.DatePickerView
import br.dev.geanbrandao.common.presentation.components.datepicker.DateRangePickerView
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarView
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import br.dev.geanbrandao.common.presentation.resources.PaddingThree
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.utils.extensions.endOfDay
import dev.geanbrandao.minhasdespesas.common.utils.extensions.endOfMonth
import dev.geanbrandao.minhasdespesas.common.utils.extensions.startOfDay
import dev.geanbrandao.minhasdespesas.common.utils.extensions.startOfMonth
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.presentation.filters.FilterByDateEnum.BETWEEN_DATES
import dev.geanbrandao.minhasdespesas.presentation.filters.FilterByDateEnum.PICK_DATE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun FiltersScreen(
    goBack: () -> Unit,
    viewModel: FiltersViewModel = koinViewModel(),
) {
    val categories = viewModel.categories.collectAsState()
    val selectedFilters = viewModel.selectedFilters.collectAsState(emptyList())
    val context = LocalContext.current
    val onFiltersApply = viewModel.onFiltersApply.collectAsState()

    if (onFiltersApply.value) goBack() //change this

    FiltersScreenView(
        selectedFilters = selectedFilters.value,
        categories = categories.value,
        onCheckChangeListener = { isChecked: Boolean, item: Category ->
            viewModel.updateSelectedCategories(categoryId = item.categoryId, isChecked = isChecked)
        },
        onSelectedFilterDate = { filterTypeEnum: FilterByDateEnum, startDate: Long?, endDate: Long? ->
            viewModel.updateFilterDate(
                filterTypeEnum.toFilterDate(
                    context = context,
                    pickedStartDate = startDate,
                    pickedEndDate = endDate
                )
            )
        },
        onCleanFilters = {
            viewModel.setSelectedFilters()
        },
        onApplyFilters = {
            viewModel.setSelectedFilters(selectedFilters.value)
        },
        onCancelClicked = goBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FiltersScreenView(
    selectedFilters: List<SelectedFilter>,
    categories: List<Category>,
    onSelectedFilterDate: (FilterByDateEnum, Long?, Long?) -> Unit,
    onCheckChangeListener: (Boolean, Category) -> Unit,
    onCleanFilters: () -> Unit,
    onApplyFilters: () -> Unit,
    onCancelClicked: () -> Unit,
) {

    val isDatePickerVisible = remember { mutableStateOf(false) }
    val isRangePicker = remember { mutableStateOf(false) }
    val dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() }

    BaseScreen(
        header = {
            ToolbarView(
                onBackButtonClicked = {}, // todo adicionar voltar
                toolbarTitle = stringResource(id = R.string.fragment_filters_title_toolbar)
            )
        },
        content = {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (selectedFilters.isNotEmpty()) {
                        Spacer(modifier = Modifier.size(size = PaddingTwo))
                        SelectedFiltersView(
                            list = selectedFilters,
                            onCleanFilters = onCleanFilters,
                        )
                    }

//                    FiltersListView(
//                        categories = categories,
//                        onSelectedFilterDate = onSelectedFilterDate,
//                        onCheckChangeListener = onCheckChangeListener,
//                    )
                    FiltersListView2(
                        categories = categories,
                        onSelectedFilterDate = {
                            if (it == PICK_DATE || it == BETWEEN_DATES) {
                                isDatePickerVisible.value = true
                                isRangePicker.value = it == BETWEEN_DATES
                            } else {
                                onSelectedFilterDate.invoke(it, null, null)
                            }
                        },
                        onCheckChangeListener = onCheckChangeListener,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PaddingTwo)
                        .align(alignment = Alignment.BottomCenter)
                ) {
                    ButtonPrimary(
                        text = stringResource(id = R.string.fragment_filters_button_apply_filters),
                        isEnabled = selectedFilters.isNotEmpty(),
                        onClick = onApplyFilters,
                    )
                    ButtonAction(
                        text = stringResource(id = R.string.fragment_filters_button_cancel),
                        isEnabled = true,
                        onClick = onCancelClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = PaddingThree, top = PaddingOne)
                    )
                }
                if (isRangePicker.value) {
                    DateRangePickerView(
                        isVisible = isDatePickerVisible.value,
                        warningMessage = stringResource(id = R.string.dialog_picker_select_a_date),
                        confirmButtonText = stringResource(id = R.string.button_default_text_ok2),
                        dismissButtonText = stringResource(id = R.string.button_text_default_cancel),
                        onDismiss = { isDatePickerVisible.value = false },
                        onSelectRangeDate = { startDate: Long, endDate: Long ->
                            onSelectedFilterDate(BETWEEN_DATES, startDate, endDate)
                        }
                    )
                } else {
                    DatePickerView(
                        isVisible = isDatePickerVisible.value,
                        warningMessage = stringResource(id = R.string.dialog_picker_select_a_date),
                        confirmButtonText = stringResource(id = R.string.button_default_text_ok2),
                        dismissButtonText = stringResource(id = R.string.button_text_default_cancel),
                        onDismiss = { isDatePickerVisible.value = false },
                        onSelectDate = { selectedDate ->
                            onSelectedFilterDate(PICK_DATE, selectedDate, null)
                        },
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HandleFilterDatePicker(
    isVisible: Boolean,
    dateFormatter: DatePickerFormatter,
    modifier: Modifier = Modifier,
    isRangePicker: Boolean = false,
    onDismiss: () -> Unit,
    onSelectDate: (timeMillis: Long?) -> Unit,
    onSelectRangeDate: (startDate: Long, endDate: Long) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val datePickerRangeState = rememberDateRangePickerState()
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.dialog_picker_select_a_date)
    if (isVisible) {
        Column(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
            if (isRangePicker) {
                DateRangePicker(
                    state = datePickerRangeState,
                    showModeToggle = false,
                    dateFormatter = dateFormatter,
                    modifier = Modifier.weight(1f),
                )
            } else {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    dateFormatter = dateFormatter,
                    modifier = Modifier.weight(1f),
                )
            }
            Row {
                Spacer(modifier = Modifier.weight(1f))
                ButtonAction(
                    text = stringResource(id = R.string.button_text_default_cancel),
                    isEnabled = true,
                    onClick = onDismiss,
                )
                ButtonAction(
                    text = stringResource(id = R.string.button_default_text_ok2),
                    isEnabled = true,
                    onClick = {
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
                )
            }
        }
    }
}

@Preview
@Composable
private fun FiltersScreenViewPreview() {
    val selectedFilters = listOf(
        SelectedFilter(
            category = null,
            date = FilterDate(0L, null, "", FilterByDateEnum.YEAR),
        ),
        SelectedFilter(
            date = null,
            category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
        )
    )
//    val selectedFilters = listOf<SelectedFilter>()
    FiltersScreenView(
        selectedFilters = selectedFilters,
        categories = listOf(),
        onCleanFilters = {},
        onSelectedFilterDate = { filterByDateEnum: FilterByDateEnum, startDate: Long?, endDate: Long? ->

        },
        onCheckChangeListener = { isChecked: Boolean, item: Category ->
        },
        onApplyFilters = {},
        onCancelClicked = {}
    )
}

@Serializable
@Parcelize
data class FilterDate(
    val startDate: Long?,
    val endDate: Long? = null,
    val label: String?,
    val type: FilterByDateEnum
) : Parcelable

sealed class FilterByDate(
    open val startDate: Long,
    open val endData: Long? = null
) {
    data class Week(override val startDate: Long) :
        FilterByDate(startDate) // todo fazer uma conta para pegar menos 7 dias aqui

    data class Month(override val startDate: Long) :
        FilterByDate(startDate) // todo fazer uma conta para pegar menos 30 dias aqui

    data class CurrentMonth(override val startDate: Long, override val endData: Long) :
        FilterByDate(startDate, endData)

    data class Year(override val startDate: Long) :
        FilterByDate(startDate) // todo fazer uma conta para pegar menos 365 dias aqui

    data class PickDate(override val startDate: Long) :
        FilterByDate(startDate) // todo fazer uma conta para pegar menos 24 horas aqui

    data class BetweenDates(override val startDate: Long, override val endData: Long) :
        FilterByDate(startDate, endData)
}

enum class FilterByDateEnum {
    WEEK,
    MONTH,
    CURRENT_MONTH,
    YEAR,
    PICK_DATE,
    BETWEEN_DATES,
    ALL;
}

fun FilterByDateEnum.toFilterDate(
    context: Context,
    pickedStartDate: Long?,
    pickedEndDate: Long?
): FilterDate {
    val date = getCurrentTimeInMillis().getOffsetDateTime()

    return when (this) {
        FilterByDateEnum.ALL -> {
            FilterDate(
                startDate = null,
                endDate = null,
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_1), // todo tem 2 lugares com essa mesma lógica de label
                type = this,
            )
        }
        FilterByDateEnum.WEEK -> {
            val startDate = date.startOfDay().minusDays(7)
            val endDate = date.endOfDay()
            FilterDate(
                startDate = startDate.getLongTimeMillis(),
                endDate = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_2),
                type = this,
            )
        }

        FilterByDateEnum.MONTH -> {
            val startDate = date.startOfDay().minusDays(30)
            val endDate = date.endOfDay()
            FilterDate(
                startDate = startDate.getLongTimeMillis(),
                endDate = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_3),
                type = this,
            )
        }

        FilterByDateEnum.CURRENT_MONTH -> {
            val startDate = date.startOfMonth()
            val endDate = date.endOfMonth()
            FilterDate(
                startDate = startDate.getLongTimeMillis(),
                endDate = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_8),
                type = this,
            )
        }

        FilterByDateEnum.YEAR -> {
            val startDate = date.startOfDay().minusMonths(12)
            val endDate = date.endOfDay()
            FilterDate(
                startDate = startDate.getLongTimeMillis(),
                endDate = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_4),
                type = this,
            )
        }

        PICK_DATE -> {
            val startDate = pickedStartDate?.getOffsetDateTime()?.startOfDay()?.getLongTimeMillis()
            val endDate = pickedStartDate?.getOffsetDateTime()?.endOfDay()?.getLongTimeMillis()
            FilterDate(
                startDate = startDate,
                endDate = endDate,
                label = startDate?.toBrazilianDateFormat(PATTERN_SHORT_DATE).orEmpty(),
                type = this,
            )
        }

        BETWEEN_DATES -> {
            val startDate = pickedStartDate?.getOffsetDateTime()?.startOfDay()?.getLongTimeMillis()
            val endDate = pickedEndDate?.getOffsetDateTime()?.endOfDay()?.getLongTimeMillis()
            val label = buildString {
                append(
                    startDate?.toBrazilianDateFormat(PATTERN_SHORT_DATE).orEmpty()
                )
                append(" - ")
                append(
                    endDate?.toBrazilianDateFormat(PATTERN_SHORT_DATE).orEmpty()
                )
            }
            FilterDate(
                startDate = startDate,
                endDate = endDate,
                label = label,
                type = this,
            )
        }
    }
}

data class FilterByDateItem(
    val label: String,
    val type: FilterByDateEnum,
)



