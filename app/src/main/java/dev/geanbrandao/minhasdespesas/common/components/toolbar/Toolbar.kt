package dev.geanbrandao.minhasdespesas.common.components.toolbar

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.icons.IconDefault
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerThree
import dev.geanbrandao.minhasdespesas.common.components.texts.TextToolbar
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Preview("TopBar preview")
@Composable
fun AppToolbar(
    navHostController: NavHostController = rememberNavController(),
    @StringRes stringId: Int = R.string.fragment_add_edit_expense_toolbar_title,
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
            IconDefault(
                onColor = onColor,
                navHostController = navHostController,
                modifier = Modifier.align(alignment = CenterVertically)
            )
            SpacerThree()
            TextToolbar(
                stringId = stringId,
                textColor = onColor,
                modifier = Modifier.align(alignment = CenterVertically)
            )
        }
    }
}
