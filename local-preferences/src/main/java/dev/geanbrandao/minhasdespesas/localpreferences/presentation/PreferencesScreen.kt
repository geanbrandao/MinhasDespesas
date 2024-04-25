package dev.geanbrandao.minhasdespesas.localpreferences.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreferencesScreen(
    goBack: () -> Unit,
    viewModel: PreferencesViewModel = koinViewModel()
) {

    val selectedTheme = viewModel.selectedTheme.collectAsState()
    val selectedSwipe = viewModel.selectedSwipe.collectAsState()

    PreferencesScreenView(
        selectedValueTheme = selectedTheme.value,
        selectedValueSwipe = selectedSwipe.value,
        onSelectedThemeOption = {
            viewModel.updateSelectedTheme(it)
        },
        onSelectedSwipeOption = {
            viewModel.updateSelectedSwipe(it)
        },
        onBackButtonClicked = goBack,
    )
}

@Composable
private fun PreferencesScreenView(
    selectedValueTheme: String?,
    selectedValueSwipe: String?,
    onSelectedThemeOption: (option: String) -> Unit,
    onSelectedSwipeOption: (option: String) -> Unit,
    onBackButtonClicked: () -> Unit,
) {

    BaseScreen(
        header = {
            ToolbarView(
                onBackButtonClicked = onBackButtonClicked, // todo adicionar voltar
                toolbarTitle = stringResource(R.string.preferences_screen_toolbar_title),
            )
        },
        isLoading = selectedValueTheme.isNull()
    ) {
        ThemeOptionSettingView(
            selectedOption = selectedValueTheme,
            onSelectedThemeOption = onSelectedThemeOption,
        )
        SwipeOptionSettingsView(
            selectedOption = selectedValueSwipe,
            onSelectedSwipeOption = onSelectedSwipeOption
        )
    }
}

@Composable
private fun SwipeOptionSettingsView(
    selectedOption: String?,
    onSelectedSwipeOption: (option: String) -> Unit,
) {
    val settingsOptionsIsOpen = remember { mutableStateOf(false) }

    val options = getSwipeOptions()
    val selectedLabel = options.getLabelOrFirst(selectedOption)

    SettingsItemView(
        title = stringResource(id = R.string.preferences_swipe_config_label),
        subtitle = selectedLabel,
        icon = painterResource(id = R.drawable.ic_swipe),
    ) {
        settingsOptionsIsOpen.value = true
    }
    if (settingsOptionsIsOpen.value) {
        SettingsOptionsDialogView(
            options = options,
            selectedOption = selectedOption.orEmpty(),
            onSelectedOption = onSelectedSwipeOption,
            onDismissRequest = {
                settingsOptionsIsOpen.value = false
            },
        )
    }
}

@Composable
private fun ThemeOptionSettingView(
    selectedOption: String?,
    onSelectedThemeOption: (option: String) -> Unit,
) {
    val settingsOptionsIsOpen = remember { mutableStateOf(false) }

    val options = getThemeOptions()
    val label = options.getLabelOrFirst(selectedOption)
    val icon = when (selectedOption) {
        stringArrayResource(id = R.array.preferences_theme_values)[0] -> {
            painterResource(id = R.drawable.ic_auto)
        }

        stringArrayResource(id = R.array.preferences_theme_values)[1] -> {
            painterResource(id = R.drawable.ic_light)
        }

        stringArrayResource(id = R.array.preferences_theme_values)[2] -> {
            painterResource(id = R.drawable.ic_dark)
        }

        else -> {
            painterResource(id = R.drawable.ic_auto)
        }
    }
    SettingsItemView(
        title = stringResource(id = R.string.preferences_theme_label),
        subtitle = label,
        icon = icon,
    ) {
        settingsOptionsIsOpen.value = true
    }
    if (settingsOptionsIsOpen.value) {
        SettingsOptionsDialogView(
            options = options,
            selectedOption = selectedOption.orEmpty(),
            onSelectedOption = onSelectedThemeOption,
            onDismissRequest = {
                settingsOptionsIsOpen.value = false
            },
        )
    }
}

@Composable
private fun SettingsItemView(
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
private fun SettingsOptionsDialogView(
    options: List<SettingOption>,
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
            options.forEach { option: SettingOption ->
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
private fun getThemeOptions(): List<SettingOption> {
    val optionsLabel = stringArrayResource(id = R.array.preferences_theme_labels)
    val optionsValues = stringArrayResource(id = R.array.preferences_theme_values)
    return listOf(
        SettingOption(
            value = optionsValues[0],
            label = optionsLabel[0]
        ),
        SettingOption(
            value = optionsValues[1],
            label = optionsLabel[1]
        ),
        SettingOption(
            value = optionsValues[2],
            label = optionsLabel[2]
        ),
    )
}

@Composable
private fun getSwipeOptions(): List<SettingOption> {
    val optionsLabels = stringArrayResource(id = R.array.preferences_swipe_labels)
    val optionsValues = stringArrayResource(id = R.array.preferences_swipe_values)
    return listOf(
        SettingOption(
            value = optionsValues[0],
            label = optionsLabels[0],
        ),
        SettingOption(
            value = optionsValues[1],
            label = optionsLabels[1],
        ),
        SettingOption(
            value = optionsValues[2],
            label = optionsLabels[2],
        ),
    )
}
private fun List<SettingOption>.getLabelOrFirst(value: String?): String {
    return this.firstOrNull { it.value == value }?.label ?: this[0].label
}


data class SettingOption(
    val value: String,
    val label: String,
)

@Preview
@Composable
private fun PreferencesScreenViewPreview() {
    PreferencesScreenView(
        selectedValueTheme = "auto",
        selectedValueSwipe = "Ambos os lados",
        onSelectedThemeOption = {},
        onSelectedSwipeOption = {},
        onBackButtonClicked = {}
    )
}

@Preview
@Composable
private fun ThemeOptionSettingViewPreview() {
    Column(Modifier.background(color = Color.White)) {
        ThemeOptionSettingView(
            selectedOption = "auto",
            onSelectedThemeOption = {}
        )
    }
}

@Preview
@Composable
private fun SwipeOptionSettingsViewPreview() {
    Column(Modifier.background(color = Color.White)) {
        SwipeOptionSettingsView(
            selectedOption = "Opções ao arrastar",
            onSelectedSwipeOption = {}
        )
    }
}

@Preview
@Composable
private fun SettingsOptionsDialogPreview() {
    val list = listOf(SettingOption("dark", "Escuro"), SettingOption("light", "Claro"))
    Column(modifier = Modifier.fillMaxSize()) {
        SettingsOptionsDialogView(
            options = list,
            selectedOption = "dark",
            onSelectedOption = {},
            onDismissRequest = {},
        )
    }
}