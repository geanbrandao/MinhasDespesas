package dev.geanbrandao.minhasdespesas.domain.usecase

import org.koin.core.annotation.Single

@Single
data class PreferencesUseCases(
    val setSelectedFiltersUseCase: SetSelectedFiltersUseCase,
    val getSelectedFiltersUseCase: GetSelectedFiltersUseCase,
)