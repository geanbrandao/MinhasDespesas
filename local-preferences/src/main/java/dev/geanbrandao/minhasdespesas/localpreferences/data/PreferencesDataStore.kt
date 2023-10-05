package dev.geanbrandao.minhasdespesas.localpreferences.data

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {
    suspend fun getTheme() : Flow<String>
    suspend fun setTheme(themeName: String)
}