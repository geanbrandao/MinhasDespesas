package dev.geanbrandao.minhasdespesas.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.dev.geanbrandao.common.domain.clickableNoRippleEffect
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.components.NavigationEvent

@Composable
fun ProfileScreen(
    navigateTo: (NavigationEvent) -> Unit,
) {
    ProfileScreenView(
        openCategoriesScreen = { navigateTo(NavigationEvent.NavigateToCategories) },
        openPreferenceScreen = { navigateTo(NavigationEvent.NavigateToPreferences) },
        openFiltersScreen = { navigateTo(NavigationEvent.NavigateToFilters) },
        openPlaygroundScreen = { navigateTo(NavigationEvent.NavigateToPlayground) },
    )
}

@Composable
fun ProfileScreenView(
    openCategoriesScreen: () -> Unit,
    openPreferenceScreen: () -> Unit,
    openFiltersScreen: () -> Unit,
    openPlaygroundScreen: () -> Unit,
) {
    BaseScreen(
        content = {
            Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                Text(text = "Gerenciar Categorias",
                    modifier = Modifier
                        .clickableNoRippleEffect { openCategoriesScreen() }
                        .padding(all = PaddingTwo)
                )
                Text(
                    text = "Configurações",
                    modifier = Modifier
                        .clickableNoRippleEffect { openPreferenceScreen() }
                        .padding(all = PaddingTwo)
                )
                Text(
                    text = "Filtros",
                    modifier = Modifier
                        .clickableNoRippleEffect { openFiltersScreen() }
                        .padding(all = PaddingTwo)
                )
                Text(
                    text = "Teste",
                    modifier = Modifier
                        .clickableNoRippleEffect { openPlaygroundScreen() }
                        .padding(all = PaddingTwo)
                )
            }
        }
    )

}

@Preview
@Composable
fun ProfileScreenViewPreview() {
    ProfileScreenView(
        openCategoriesScreen = {},
        openPreferenceScreen = {},
        openFiltersScreen = {},
        openPlaygroundScreen = {},
    )
}