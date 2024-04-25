package dev.geanbrandao.minhasdespesas.domain.usecase.preferences

import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import org.koin.core.annotation.Factory

@Factory
class GetSwipeDirection(
    private val preferences: PreferencesDataStore,
) {
    suspend operator fun invoke() = preferences.getSwipe()
}