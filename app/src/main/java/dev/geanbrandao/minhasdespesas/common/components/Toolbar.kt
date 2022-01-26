package dev.geanbrandao.minhasdespesas.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Preview("TopBar preview")
@Composable
fun AppToolbar(
    navHostController: NavHostController = rememberNavController(),
    topBarTitle: String = "Title bar",
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    onColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    Card(elevation = 1.dp, shape = RectangleShape) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color)
                .padding(all = PaddingDefault),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Icone voltar",
                tint = onColor,
                modifier = Modifier
                    .align(alignment = CenterVertically)
                    .size(24.dp)
                    .clickable {
                        navHostController.navigateUp()
                    }
            )
            Spacer(modifier = Modifier.width(width = PaddingDefault))
            Text(
                text = topBarTitle,
                style = AppTypography.bodyLarge,
                color = onColor,
                modifier = Modifier
                    .align(alignment = CenterVertically)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
