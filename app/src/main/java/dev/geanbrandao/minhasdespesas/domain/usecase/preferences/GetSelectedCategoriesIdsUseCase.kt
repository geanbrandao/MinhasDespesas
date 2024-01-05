package dev.geanbrandao.minhasdespesas.domain.usecase.preferences

import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class GetSelectedCategoriesIdsUseCase(
    private val preferences: PreferencesDataStore,
) {

    suspend operator fun invoke() = preferences.getSelectedCategoriesIds().map {
        it?.takeIf { it.isNotEmpty() }?.split(",")?.map { id -> id.toLong() }.orEmpty()
    }
}