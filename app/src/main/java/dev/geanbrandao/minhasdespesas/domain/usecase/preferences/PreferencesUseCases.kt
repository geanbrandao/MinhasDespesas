package dev.geanbrandao.minhasdespesas.domain.usecase.preferences

import dev.geanbrandao.minhasdespesas.domain.usecase.GetSelectedFiltersUseCase
import org.koin.core.annotation.Single

@Single
data class PreferencesUseCases(
    val setSelectedFiltersUseCase: SetSelectedFiltersUseCase,
    val getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
    val getSwipeDirection: GetSwipeDirection,
    val setSelectedCategoriesIdsUseCase: SetSelectedCategoriesIdsUseCase,
    val getSelectedCategoriesIdsUseCase: GetSelectedCategoriesIdsUseCase,
)