package dev.geanbrandao.minhasdespesas.localpreferences.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesKeys.KEY_SELECTED_FILTERS
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesKeys.KEY_SWIPE_NAME
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesKeys.KEY_THEME_NAME
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

enum class ThemeNameEnum(val value: String) {
    AUTO(value = THEME_AUTO),
    LIGHT(value = THEME_LIGHT),
    DARK(value = THEME_DARK);

    companion object {
        fun from(themeName: String) =
            values().find { it.value == themeName } ?: throw Exception("Tema $themeName não existe")
    }
}

const val THEME_AUTO = "auto"
const val THEME_LIGHT = "light"
const val THEME_DARK = "dark"

@Single
class PreferencesDataStoreImpl(
    private val dataStore: DataStore<Preferences>,
) : PreferencesDataStore {

    override suspend fun getTheme() : Flow<String> {
        return dataStore.data
            .catch { throwable: Throwable ->
                if (throwable is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw throwable
                }
            }
            .map { value: Preferences ->
                value[KEY_THEME_NAME] ?: ThemeNameEnum.AUTO.value
            }
    }

    override suspend fun setTheme(themeName: String) {
        dataStore.edit { value: MutablePreferences ->
            value[KEY_THEME_NAME] = themeName
        }
    }

    override suspend fun getSwipe(): Flow<String> {
        return dataStore.data
            .catch { throwable: Throwable ->
                if (throwable is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw throwable
                }
            }
            .map { value: Preferences ->
                value[KEY_SWIPE_NAME] ?: "both"
            }
    }

    override suspend fun setSwipe(swipeName: String) {
        dataStore.edit { value: MutablePreferences ->
            value[KEY_SWIPE_NAME] = swipeName
        }
    }

    override suspend fun setSelectedFilters(selectedFilters: String) {
        dataStore.edit { value: MutablePreferences ->
            value[KEY_SELECTED_FILTERS] = selectedFilters
        }
    }

    override suspend fun getSelectedFilters(): Flow<String?> {
        return dataStore.data
            .catch { throwable: Throwable ->
                if (throwable is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw throwable
                }
            }
            .map { value: Preferences ->
                value[KEY_SELECTED_FILTERS]
            }
    }
}

