package dev.geanbrandao.minhasdespesas.feature.presentation.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.dividers.ItemDefaultDivider
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerThree
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.components.toolbar.AppToolbar
import dev.geanbrandao.minhasdespesas.common.log.DebugLog
import dev.geanbrandao.minhasdespesas.common.utils.extensions.clickableRoundedEffect
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum
import dev.geanbrandao.minhasdespesas.feature.presentation.filters.components.ListCategoryOptions
import dev.geanbrandao.minhasdespesas.feature.presentation.filters.components.ListSelectedFilters
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.CardElevationLow
import dev.geanbrandao.minhasdespesas.ui.theme.CornersDefault
import dev.geanbrandao.minhasdespesas.ui.theme.MarginThree
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import dev.geanbrandao.minhasdespesas.ui.theme.RotationHalf

private val log: DebugLog = DebugLog("FiltersScreen")

@ExperimentalAnimationApi
@Composable
fun FiltersScreen(
    navHostController: NavHostController,
    viewModel: FiltersViewModel = hiltViewModel()
) {
    val filtersState = viewModel.state

    val activeFilters = viewModel.stateActiveFilters

    val color = MaterialTheme.colorScheme.secondaryContainer
    val onColor = MaterialTheme.colorScheme.onSecondaryContainer

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        AppToolbar(
            stringId = R.string.fragment_filters_title_toolbar,
            navHostController = navHostController,
        )
        LazyColumn {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(all = MarginThree)) {
                    ListSelectedFilters(
                        dataList = activeFilters.value,
                        modifier = Modifier.align(alignment = Alignment.CenterStart),
                        onRemoveFilter = { atIndex: Int ->

                        }
                    )
                }
            }
            item {
                TopicFilter(
                    topicName = stringResource(id = R.string.fragment_filters_text_label_filter_by_date),
                    color = color,
                    onColor = onColor,
                    onClickTopic = {}
                ) {
                    OptionsFiltersByDate(onColor = onColor) { value: TypeFilterDateEnum ->
                        log.debug("typeFilter - $value")
                        viewModel.setActiveFilterByDate(value)
                    }
                }
            }
            item {
                TopicFilter(
                    topicName = stringResource(id = R.string.fragment_filters_text_label_filter_by_categories),
                    color = color,
                    onColor = onColor,
                    onClickTopic = {}
                ) {
                    ListCategoryOptions(
                        state = filtersState.value,
                        onCheckedChangeListener = { isChecked, categoryDb ->
                            viewModel.onCategoryCheckChange(isChecked = isChecked, categoryDb = categoryDb)
                        },
                        modifier = Modifier.wrapContentHeight()
                    )
                }
                SpacerThree()
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun TopicFilter(
    topicName: String,
    color: Color,
    onColor: Color,
    onClickTopic: () -> Unit,
    content: @Composable () -> Unit,
) {
    val isExpanded = remember {
        mutableStateOf(value = false)
    }

    val cornerRadius =
        animateDpAsState(targetValue = if (isExpanded.value) CornersDefault else 0.dp)
    val rotation =
        animateFloatAsState(targetValue = if (isExpanded.value.not()) 0f else RotationHalf)
    TopicRowSpacer(visible = isExpanded.value)

    Surface(
        tonalElevation = CardElevationLow,
        color = color,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingDefault),
        onClick = {
            isExpanded.value = isExpanded.value.not()
        },
        shape = RoundedCornerShape(cornerRadius.value),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = PaddingDefault,
                        vertical = PaddingDefault
                    )
            ) {
                Text(
                    text = topicName,
                    style = AppTypography.bodyLarge,
                    color = onColor,
                    modifier = Modifier
                        .weight(1f)
                        .align(alignment = Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Icone expandir",
                    tint = onColor,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .rotate(degrees = rotation.value)
                )
            }
            if (isExpanded.value) {
                content()
            }
        }
    }
    TopicRowSpacer(visible = isExpanded.value)
}

@Composable
fun OptionsFiltersByDate(
    onColor: Color,
    onClickFilterDate: (typeFilterDate: TypeFilterDateEnum) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingDefault)
    ) {
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_2),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDateEnum.WEEK)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_3),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDateEnum.MONTH)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_8),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDateEnum.CURRENT_MONTH)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_4),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDateEnum.YEAR)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_5),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDateEnum.PICKED_DATE)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_6),
            onColor = onColor,
            modifier = Modifier,
            isLastItem = true,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDateEnum.RANGE_DATE)
            },
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        SpacerTwo()
    }
}

@Composable
fun TextFilterByDate(
    modifier: Modifier = Modifier,
    filterName: String = "Últimos 7 dias",
    onColor: Color,
    isLastItem: Boolean,
    onClickFilterDate: (typeFilterDate: TypeFilterDateEnum) -> Unit,
) {
    Column(
        Modifier.clickableRoundedEffect {
            onClickFilterDate(TypeFilterDateEnum.PICKED_DATE)
        },
    ) {
        Text(
            text = filterName,
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.Light,
            color = onColor,
            modifier = modifier.padding(vertical = PaddingDefault)
        )
        ItemDefaultDivider(color = onColor, shouldShow = isLastItem.not())
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun FilterScreenPreview() {
    FiltersScreen(
        navHostController = rememberNavController()
    )
}
