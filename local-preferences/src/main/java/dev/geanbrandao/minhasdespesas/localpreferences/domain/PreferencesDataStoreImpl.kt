package dev.geanbrandao.minhasdespesas.localpreferences.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesKeys.KEY_SELECTED_CATEGORIES
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesKeys.KEY_SELECTED_FILTERS
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesKeys.KEY_SWIPE_NAME
import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesKeys.KEY_THEME_NAME
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

// theme options
const val THEME_AUTO = "auto"
const val THEME_LIGHT = "light"
const val THEME_DARK = "dark"
// swipe options
const val SWIPE_BOTH = "both"
const val SWIPE_RIGHT = "right"
const val SWIPE_LEFT = "left"

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
                value[KEY_THEME_NAME] ?: THEME_AUTO
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
                value[KEY_SWIPE_NAME] ?: SWIPE_BOTH
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

    override suspend fun setSelectedCategoriesIds(selectedIds: String?) {
        dataStore.edit { value: MutablePreferences ->
            value[KEY_SELECTED_CATEGORIES] = selectedIds.orEmpty()
        }
    }

    override suspend fun getSelectedCategoriesIds(): Flow<String?> {
        return dataStore.data
            .catch { throwable: Throwable ->
                if (throwable is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw throwable
                }
            }
            .map { value: Preferences ->
                value[KEY_SELECTED_CATEGORIES]
            }
    }
}

