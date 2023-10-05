package dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_IS_INSERTED = "isInserted"

@KoinViewModel
class SplashscreenViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
): ViewModel() {

    val isReady = state.getStateFlow<Boolean>(key = KEY_IS_INSERTED, initialValue = false)

    fun insertDefaultCategories(list: List<CategoryEntity>) {
        viewModelScope.launch {
            useCases.addCategories(list)
                .catch { throw Exception(it) }
                .collect {
                    state[KEY_IS_INSERTED] = it
                }
        }
    }

}