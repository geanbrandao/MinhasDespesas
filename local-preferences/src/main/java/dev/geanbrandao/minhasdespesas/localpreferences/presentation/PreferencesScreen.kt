package dev.geanbrandao.minhasdespesas.localpreferences.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.dev.geanbrandao.common.domain.clickableNoRippleEffect
import br.dev.geanbrandao.common.domain.isNull
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.text.TextBodyLarge
import br.dev.geanbrandao.common.presentation.components.text.TextLabelSmall
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarView
import br.dev.geanbrandao.common.presentation.resources.MarginOne
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.localpreferences.R
import dev.geanbrandao.minhasdespesas.localpreferences.domain.ThemeNameEnum
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreferencesScreen(
    navHostController: NavHostController,
    viewModel: PreferencesViewModel = koinViewModel()
) {

    val selectedTheme = viewModel.selectedTheme.collectAsState()
    val themeOptions = getThemeOptions()
    PreferencesScreenView(
        navHostController = navHostController,
        selectedTheme = selectedTheme.value,
        options = themeOptions,
        onSelectedOption = {
            viewModel.updateSelectedTheme(it)
        }
    )
}

@Composable
private fun PreferencesScreenView(
    navHostController: NavHostController,
    selectedTheme: String?,
    options: List<ThemeOption>,
    onSelectedOption: (option: String) -> Unit,
) {
    val settingsOptionIsOpen = remember { mutableStateOf(false) }
    BaseScreen(
        header = {
            ToolbarView(
                navHostController = navHostController,
                toolbarTitle = stringResource(R.string.preferences_screen_toolbar_title),
            )
        },
        isLoading = selectedTheme.isNull()
    ) {
        selectedTheme?.let {
            val (icon, themeLabel) = when (ThemeNameEnum.from(selectedTheme)) {
                ThemeNameEnum.AUTO -> {
                    Pair(
                        first = painterResource(id = R.drawable.ic_auto),
                        second = stringArrayResource(id = R.array.preferences_theme_entries)[0]
                    )
                }

                ThemeNameEnum.LIGHT -> {
                    Pair(
                        first = painterResource(id = R.drawable.ic_light),
                        second = stringArrayResource(id = R.array.preferences_theme_entries)[1]
                    )
                }

                ThemeNameEnum.DARK -> {
                    Pair(
                        first = painterResource(id = R.drawable.ic_dark),
                        second = stringArrayResource(id = R.array.preferences_theme_entries)[2]
                    )
                }
            }
            SettingsItem(
                title = stringResource(id = R.string.preferences_theme_label),
                subtitle = themeLabel,
                icon = icon,
            ) {
                settingsOptionIsOpen.value = true
            }
            if (settingsOptionIsOpen.value) {
                SettingsOptionsDialog(
                    options,
                    selectedTheme,
                    onSelectedOption = onSelectedOption,
                    onDismissRequest = {
                        settingsOptionIsOpen.value = false
                    },
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    subtitle: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    onSettingItemClicked: () -> Unit,
) {
    val color = MaterialTheme.colorScheme.onBackground
    Row(
        modifier = modifier
            .padding(all = PaddingTwo)
            .fillMaxWidth()
            .clickableNoRippleEffect {
                onSettingItemClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconView(icon = icon, tint = color)
        Spacer(modifier = Modifier.size(MarginOne))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextBodyLarge(text = title, color = color)
            TextLabelSmall(text = subtitle, color = color)
        }
    }
}

@Composable
private fun SettingsOptionsDialog(
    options: List<ThemeOption>,
    selectedOption: String,
    onSelectedOption: (option: String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val radioSelectedOption = remember { mutableStateOf(selectedOption) }
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(all = PaddingTwo)
        ) {
            TextBodyLarge(
                text = stringResource(id = R.string.preferences_theme_label),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(size = PaddingTwo))
            options.forEach { option: ThemeOption ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option.value == radioSelectedOption.value),
                            onClick = {
                                onSelectedOption(option.value)
                                onDismissRequest()
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option.value == selectedOption),
                        onClick = {
                            onSelectedOption(option.value)
                            onDismissRequest()
                        }
                    )
                    TextLabelSmall(
                        text = option.label,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun getThemeOptions(): List<ThemeOption> {
    val optionsLabel = stringArrayResource(id = R.array.preferences_theme_entries)
    return listOf(
        ThemeOption(
            value = ThemeNameEnum.AUTO.value,
            label = optionsLabel[0]
        ),
        ThemeOption(
            value = ThemeNameEnum.LIGHT.value,
            label = optionsLabel[1]
        ),
        ThemeOption(
            value = ThemeNameEnum.DARK.value,
            label = optionsLabel[2]
        )
    )
}

data class ThemeOption(
    val value: String,
    val label: String,
)

@Preview
@Composable
private fun PreferencesScreenViewPreview() {
    PreferencesScreenView(
        rememberNavController(),
        "auto",
        listOf(ThemeOption("dark", "Escuro"), ThemeOption("light", "Claro")),
        onSelectedOption = {},
    )
}