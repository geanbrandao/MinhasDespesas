package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory

@Factory
class SetSelectedFiltersUseCase(
    private val preferences: PreferencesDataStore,
) {

    suspend operator fun invoke(list: List<SelectedFilter>) {
        val selectedFilters = Json.encodeToString(list)
        preferences.setSelectedFilters(selectedFilters)
    }
}