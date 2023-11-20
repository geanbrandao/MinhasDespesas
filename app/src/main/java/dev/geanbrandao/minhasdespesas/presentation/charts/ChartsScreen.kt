package dev.geanbrandao.minhasdespesas.presentation.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.lib.compose.piecharts.domain.ChartColors
import dev.geanbrandao.lib.compose.piecharts.domain.model.PieChartModel
import dev.geanbrandao.lib.compose.piecharts.presentation.ChartLabels
import dev.geanbrandao.lib.compose.piecharts.presentation.PieChart
import dev.geanbrandao.lib.compose.piecharts.presentation.renderer.SliceDrawer
import dev.geanbrandao.minhasdespesas.domain.model.CategoryChartModel
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChartsScreen(
    viewModel: ChartsViewModel = koinViewModel()
) {
    val chartData = viewModel.chartData.collectAsState()
    ChartsScreenView(chartData = chartData.value)
}

@Composable
private fun ChartsScreenView(
    chartData: List<CategoryChartModel>,
) {
    BaseScreen {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(all = PaddingTwo),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // get only the 10 first items, to better visualisation of the chart
            val chartDataLimit = chartData.takeIf {
                it.size <= 10
            }?.let { chartData } ?: chartData.subList(0, 10)

            val slices = chartDataLimit
                .mapIndexed { index: Int, item: CategoryChartModel ->
                    PieChartModel.Slice(
                        value = item.amount,
                        color = ChartColors.colors[index],
                        label = item.label,
                        iconName = item.icon,
                    )
                }

            PieChart(
                modifier = Modifier
                    .padding(all = PaddingDefault)
                    .height(200.dp)
                    .width(200.dp),
                pieChartModel = PieChartModel(slices),
                sliceDrawer = SliceDrawer(sliceThickness = 50f),
            )
            ChartLabels(pieChartModel = PieChartModel(slices))
        }
    }
}

@Preview
@Composable
private fun ChartsScreenViewPreview() {
    val data = listOf(
        CategoryChartModel(
            1,
            "ic_bus",
            "Onibus",
            38f,
        ),
        CategoryChartModel(
            1,
            "ic_gym",
            "Academia",
            23f,
        ),
    )
    ChartsScreenView(data)
}