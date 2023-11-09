package dev.geanbrandao.minhasdespesas.localpreferences.data

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {
    suspend fun getTheme() : Flow<String>
    suspend fun setTheme(themeName: String)
    suspend fun getSwipe() : Flow<String>
    suspend fun setSwipe(swipeName: String)
    suspend fun setSelectedFilters(selectedFilters: String)
    suspend fun getSelectedFilters(): Flow<String?>
}