package dev.geanbrandao.minhasdespesas.feature.presentation.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.geanbrandao.lib.compose.piecharts.domain.model.PieChartModel
import dev.geanbrandao.lib.compose.piecharts.presentation.PieChart
import dev.geanbrandao.lib.compose.piecharts.presentation.renderer.SliceDrawer
import dev.geanbrandao.minhasdespesas.common.components.FiltersButton
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun ChartsScreen(
    navHostController: NavHostController
) {
    val listFilters = remember {
        mutableStateListOf("Filtro 1", "Filtro 2", "Filtro 3", "Filtro 4", "Filtro 5")
    }
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            FiltersButton(
                activeFiltersSize = listFilters.size,
                modifier = Modifier.align(alignment = Alignment.CenterEnd)
            ) { navHostController.navigate(Screen.Filters.route) }
        }
        PieChart(
            modifier = Modifier
                .padding(all = PaddingDefault)
                .height(200.dp)
                .width(200.dp),
            pieChartModel = PieChartModel(
                slices = listOf(
                    PieChartModel.Slice(10f, Color.Red),
                    PieChartModel.Slice(30f, Color.Blue),
                    PieChartModel.Slice(10f, Color.Green),
                )
            ),
            sliceDrawer = SliceDrawer(sliceThickness = 50f)
        )
    }
}