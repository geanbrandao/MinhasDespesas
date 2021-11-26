package dev.geanbrandao.minhasdespesas.feature.filters.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.ListFilters
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.CardElevation
import dev.geanbrandao.minhasdespesas.ui.theme.CardElevationLow
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingHalf

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun FiltersScreen() {

    val listFilters = remember {
        mutableStateOf(listOf<String>())
    }

    Column(
        modifier = Modifier
            .padding(all = PaddingDefault)
            .background(color = MaterialTheme.colors.background)
    ) {
        ListFilters(listFilters.value)
        TopicFilterDate {

        }
        TopicFilterDate {

        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun TopicFilterDate(onClickTopic: () -> Unit) {
    val isExpanded = remember {
        mutableStateOf(value = false)
    }
    val rotation = animateFloatAsState(targetValue = if (isExpanded.value.not()) 0f else 180f)
    Surface(
        elevation = CardElevationLow,
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            isExpanded.value = isExpanded.value.not()
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingDefault)
                .animateContentSize()
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.fragment_filters_text_label_filter_by_date),
                    style = AppTypography.labelLarge,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .weight(1f)
                        .align(alignment = Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Icon expandir",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .rotate(degrees = rotation.value)
                )
            }
            if (isExpanded.value) {
                Spacer(modifier = Modifier.height(height = PaddingHalf))
                OptionsFiltersByDate()
            }
        }
    }
    TopicRowSpacer(visible = isExpanded.value)
}

@Composable
fun OptionsFiltersByDate() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextFilterByDate()
    }
}

@ExperimentalAnimationApi
@Composable
fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(height = PaddingHalf))
    }
}

@Composable
fun TextFilterByDate(filterName: String = "Últimos 7 dias") {
    Text(
        text = filterName,
        style = AppTypography.titleMedium,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier.padding(vertical = PaddingHalf)
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun FilterScreenPreview() {
    FiltersScreen()
}
