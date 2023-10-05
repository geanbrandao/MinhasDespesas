package dev.geanbrandao.minhasdespesas.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.dev.geanbrandao.common.domain.clickableNoRippleEffect
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
) {
    ProfileScreenView(navHostController = navHostController)
}

@Composable
fun ProfileScreenView(
    navHostController: NavHostController,
) {
    BaseScreen(
        content = {
            Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                Text(text = "Gerenciar Categorias",
                    modifier = Modifier
                        .padding(all = PaddingTwo)
                        .clickableNoRippleEffect {
                        navHostController.navigate(Screen.Categories.route)
                    }
                )
                Text(
                    text = "Configurações",
                    modifier = Modifier
                        .padding(all = PaddingTwo)
                        .clickableNoRippleEffect {
                        navHostController.navigate(Screen.Preferences.route)
                    }
                )
            }
        }
    )

}

@Preview
@Composable
fun ProfileScreenViewPreview() {
    ProfileScreenView(
        navHostController = rememberNavController(),
    )
}