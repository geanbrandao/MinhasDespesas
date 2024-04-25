package dev.geanbrandao.minhasdespesas.localpreferences.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_SELECTED_THEME = "selectedTheme"
private const val KEY_SELECTED_SWIPE = "selectedSwipe"

@KoinViewModel
class PreferencesViewModel(
    private val state: SavedStateHandle,
    private val preferences: PreferencesDataStore,
) : ViewModel() {

    val selectedTheme = state.getStateFlow<String?>(key = KEY_SELECTED_THEME, initialValue = null)
    val selectedSwipe = state.getStateFlow<String?>(key = KEY_SELECTED_SWIPE, initialValue = null)

    init {
        getSelectedTheme()
        getSelectedSwipe()
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

    private fun getSelectedSwipe() {
        viewModelScope.launch {
            preferences.getSwipe()
                .catch { throw Exception(it) }
                .collect { swipeType ->
                    state[KEY_SELECTED_SWIPE] = swipeType
                }
        }
    }

    fun updateSelectedSwipe(swipeName: String) {
        viewModelScope.launch {
            preferences.setSwipe(swipeName)
        }
    }

    fun updateSelectedTheme(themeName: String) {
        viewModelScope.launch {
            preferences.setTheme(themeName)
        }
    }
}