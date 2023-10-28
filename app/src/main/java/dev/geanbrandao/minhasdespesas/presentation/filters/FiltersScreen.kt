package dev.geanbrandao.minhasdespesas.presentation.filters

import android.content.Context
import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.dev.geanbrandao.common.domain.getCurrentTimeInMillis
import br.dev.geanbrandao.common.domain.getLongTimeMillis
import br.dev.geanbrandao.common.domain.getOffsetDateTime
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.components.button.ButtonAction
import br.dev.geanbrandao.common.presentation.components.button.ButtonPrimary
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarView
import br.dev.geanbrandao.common.presentation.resources.PaddingOne
import br.dev.geanbrandao.common.presentation.resources.PaddingThree
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.utils.extensions.endOfMonth
import dev.geanbrandao.minhasdespesas.common.utils.extensions.startOfMonth
import dev.geanbrandao.minhasdespesas.domain.model.Category
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel

@Composable
fun FiltersScreen(
    navHostController: NavHostController,
    viewModel: FiltersViewModel = koinViewModel(),
) {
    val categories = viewModel.categories.collectAsState()
    val selectedFilters = viewModel.selectedFilters.collectAsState(emptyList())
    val context = LocalContext.current
    FiltersScreenView(
        navHostController = navHostController,
        selectedFilters = selectedFilters.value,
        categories = categories.value,
        onCheckChangeListener = { isChecked: Boolean, item: Category ->
            viewModel.updateSelectedCategories(categoryId = item.categoryId, isChecked = isChecked)
        },
        onSelectedFilterDate = {
            viewModel.updateFilterDate(it.toFilterDate(context))
        },
        onRemoveFilter = {

        }
    )
}

@Composable
private fun FiltersScreenView(
    navHostController: NavHostController,
    selectedFilters: List<SelectedFilter>,
    categories: List<Category>,
    onSelectedFilterDate: (FilterByDateEnum) -> Unit,
    onCheckChangeListener: (Boolean, Category) -> Unit,
    onRemoveFilter: (SelectedFilter) -> Unit,
) {
    BaseScreen(
        header = {
            ToolbarView(
                navHostController = navHostController,
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
                        SelectedFiltersView(
                            list = selectedFilters,
                            onRemoveFilter = onRemoveFilter,
                            modifier = Modifier
                                .padding(
                                    start = PaddingTwo,
                                    end = PaddingTwo,
                                    top = PaddingTwo,
                                    bottom = PaddingOne
                                ),
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
                            onSelectedFilterDate.invoke(it)
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
                        onClick = {

                        },
                    )
                    ButtonAction(
                        text = stringResource(id = R.string.fragment_filters_button_cancel), 
                        isEnabled = true,
                        onClick = { navHostController.navigateUp() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = PaddingThree, top = PaddingOne)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun FiltersScreenViewPreview() {
    val selectedFilters = listOf(
        SelectedFilter.DateType("Dia xpto", FilterDate(0L, null, "", FilterByDateEnum.ALL)),
        SelectedFilter.CategoryType(
            label = "Categoria",
            category = Category(categoryId = 0, name = "Restaurante", icon = "ic_bus")
        )
    )
//    val selectedFilters = listOf<SelectedFilter>()
    FiltersScreenView(
        navHostController = rememberNavController(),
        selectedFilters = selectedFilters,
        categories = listOf(),
        onRemoveFilter = {},
        onSelectedFilterDate = {},
        onCheckChangeListener = { isChecked: Boolean, item: Category ->

        },
    )
}

@Parcelize
data class FilterDate(
    val startDate: Long?,
    val endData: Long? = null,
    val label: String?,
    val type: FilterByDateEnum
): Parcelable

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

fun FilterByDateEnum.toFilterDate(context: Context): FilterDate? {
    val dateMillis = getCurrentTimeInMillis()
    val dateOffset = dateMillis.getOffsetDateTime()
    return when (this) {
        FilterByDateEnum.WEEK -> {
            val endDate = dateOffset.minusDays(7)
            FilterDate(
                startDate = dateMillis,
                endData = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_2),
                type = this,
            )
        }
        FilterByDateEnum.MONTH -> {
            val endDate = dateOffset.minusDays(30)
            FilterDate(
                startDate = dateMillis,
                endData = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_3),
                type = this,
            )
        }
        FilterByDateEnum.CURRENT_MONTH -> {
            val startDate = dateOffset.startOfMonth()
            val endDate = dateOffset.endOfMonth()
            FilterDate(
                startDate = startDate.getLongTimeMillis(),
                endData = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_8),
                type = this,
            )
        }
        FilterByDateEnum.YEAR -> {
            val endDate = dateOffset.minusMonths(12)
            FilterDate(
                startDate = dateMillis,
                endData = endDate.getLongTimeMillis(),
                label = context.getString(R.string.bottom_sheet_dialog_filter_option_4),
                type = this,
            )
        }
        FilterByDateEnum.PICK_DATE -> {
            null
        }
        FilterByDateEnum.BETWEEN_DATES -> {
            null
        }
        else -> {
            null
        }
    }
}



data class FilterByDateItem(
    val label: String,
    val type: FilterByDateEnum,
)



