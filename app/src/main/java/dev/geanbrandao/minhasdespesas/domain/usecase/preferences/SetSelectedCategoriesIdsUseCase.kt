package dev.geanbrandao.minhasdespesas.domain.usecase.preferences

import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class SetSelectedCategoriesIdsUseCase(
    private val preferences: PreferencesDataStore,
) {

    suspend operator fun invoke(list: List<Long>) = flow<Boolean> {
        val selectedIds = list.takeIf { it.isNotEmpty() }?.joinToString(",")
        preferences.setSelectedCategoriesIds(selectedIds)
        emit(true)
    }
}