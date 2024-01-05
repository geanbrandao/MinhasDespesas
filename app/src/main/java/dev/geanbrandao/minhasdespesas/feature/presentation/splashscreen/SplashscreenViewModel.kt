package dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import dev.geanbrandao.minhasdespesas.navigation.data.AppNavigator
import dev.geanbrandao.minhasdespesas.navigation.domain.Destination
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_IS_INSERTED = "isInserted"

@KoinViewModel
class SplashscreenViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
    private val appNavigator: AppNavigator,
): ViewModel() {

    val isReady = state.getStateFlow<Boolean>(key = KEY_IS_INSERTED, initialValue = false)

    fun insertDefaultCategories(list: List<CategoryEntity>) {
        viewModelScope.launch {
            useCases.addCategories(list)
                .catch { throw Exception(it) }
                .collect {
                    if (it) {
                        navigateToHome()
                    }
                    state[KEY_IS_INSERTED] = it
                }
        }
    }

    private suspend fun navigateToHome() {
        appNavigator.navigateTo(
            route = Destination.Expenses(),
            popUpToRoute = Destination.Splashscreen(),
            inclusive = true,
        )
    }
}