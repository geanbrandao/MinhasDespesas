package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import dev.geanbrandao.minhasdespesas.presentation.filters.SelectedFilter
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory

@Factory
class GetSelectedFiltersUseCase(
    private val preferences: PreferencesDataStore,
) {

    suspend operator fun invoke() = preferences.getSelectedFilters().map { selectedFilters ->
        selectedFilters?.let {
            Json.decodeFromString<List<SelectedFilter>>(it)
        } ?: emptyList()
    }
}