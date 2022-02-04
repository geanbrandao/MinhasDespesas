package dev.geanbrandao.minhasdespesas.feature.filters.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.toolbar.AppToolbar
import dev.geanbrandao.minhasdespesas.common.components.FiltersButton
import dev.geanbrandao.minhasdespesas.common.components.dividers.ItemDefaultDivider
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerThree
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.log.DebugLog
import dev.geanbrandao.minhasdespesas.feature.filters.presentation.components.ItemCategory
import dev.geanbrandao.minhasdespesas.feature.filters.utils.TypeFilterDate
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.CardElevationLow
import dev.geanbrandao.minhasdespesas.ui.theme.CornersDefault
import dev.geanbrandao.minhasdespesas.ui.theme.MarginThree
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingHalf
import dev.geanbrandao.minhasdespesas.ui.theme.RotationHalf

private val log: DebugLog = DebugLog("FiltersScreen")

@ExperimentalAnimationApi
@Composable
fun FiltersScreen(
    navHostController: NavHostController,
) {

    val listFilters = remember {
        mutableStateListOf(
            "Últimos 7 dias",
            "Qualquer",
            "auhduahduad",
            "aidaijdaijdaidja",
            "15118515151"
        )
    }
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    FiltersButton(
                        activeFiltersSize = listFilters.size,
                        modifier = Modifier.align(alignment = Alignment.CenterEnd)
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
                    OptionsFiltersByDate(onColor = onColor) { value: TypeFilterDate ->
                        log.debug("typeFilter - $value")
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
                    OptionsFilterByCategory(
                        listOf("Lanche", "Débito", "Pet", "Supermercado aduahudhaduhadua audhada"),
                        onColor = onColor,
                        onCheckedChange = { item: String, isChecked: Boolean ->
                            log.debug("Item $item isChecked $isChecked")
                            if (isChecked) {
                                listFilters.add(item)
                            } else {
                                listFilters.remove(item)
                            }
                        }
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
    onClickFilterDate: (typeFilterDate: TypeFilterDate) -> Unit,
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
                onClickFilterDate(TypeFilterDate.WEEK)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_3),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDate.MONTH)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_8),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDate.CURRENT_MONTH)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_4),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDate.YEAR)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_5),
            onColor = onColor,
            isLastItem = false,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDate.PICKED_DATE)
            },
        )
        TextFilterByDate(
            filterName = stringResource(id = R.string.bottom_sheet_dialog_filter_option_6),
            onColor = onColor,
            modifier = Modifier,
            isLastItem = true,
            onClickFilterDate = {
                onClickFilterDate(TypeFilterDate.RANGE_DATE)
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
fun OptionsFilterByCategory(
    categories: List<String>,
    onColor: Color,
    onCheckedChange: (item: String, isChecked: Boolean) -> Unit,
) {
    Column {
        categories.forEachIndexed { index, item ->
            ItemCategory(color = onColor, item = item, onCheckChange = { isChecked ->
                onCheckedChange(
                    item,
                    isChecked
                )
            })
            ItemDefaultDivider(
                color = onColor,
                shouldShow = categories.lastIndex != index,
                modifier = Modifier.padding(horizontal = PaddingDefault),
            )
        }
    }

}

@Composable
fun TextFilterByDate(
    modifier: Modifier = Modifier,
    filterName: String = "Últimos 7 dias",
    onColor: Color,
    isLastItem: Boolean,
    onClickFilterDate: (typeFilterDate: TypeFilterDate) -> Unit,
) {
    Column(
        Modifier.clickable {
            onClickFilterDate(TypeFilterDate.PICKED_DATE)
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
