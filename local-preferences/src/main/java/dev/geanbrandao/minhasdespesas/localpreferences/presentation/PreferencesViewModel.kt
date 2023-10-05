package dev.geanbrandao.minhasdespesas.localpreferences.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_SELECTED_THEME = "selectedTheme"

@KoinViewModel
class PreferencesViewModel(
    private val state: SavedStateHandle,
    private val preferences: PreferencesDataStore,
) : ViewModel() {

    val selectedTheme = state.getStateFlow<String?>(key = KEY_SELECTED_THEME, initialValue = null)

    init {
        getSelectedTheme()
    }

    private fun getSelectedTheme() {
        viewModelScope.launch {
            preferences.getTheme()
                .catch { throw Exception(it) }
                .collect { theme ->
                    state[KEY_SELECTED_THEME] = theme
                }
        }
    }

    fun updateSelectedTheme(themeName: String) {
        viewModelScope.launch {
            preferences.setTheme(themeName)
        }
    }
}