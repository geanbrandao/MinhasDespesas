package dev.geanbrandao.minhasdespesas.feature.presentation.splashscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.geanbrandao.minhasdespesas.common.utils.DatabaseOperationState
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class SplashscreenViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
): ViewModel() {

    private val _state = mutableStateOf(DatabaseOperationState.NONE)
    val state: State<DatabaseOperationState> = _state

    private var postCategoryJob: Job? = null

    fun insertDefaultCategories(data: List<CategoryDb>) {
        postCategoryJob?.cancel()
        postCategoryJob = expenseUseCases.postCategories(data = data)
            .onEach {
                _state.value = it
            }.catch {
                // do something
            }.launchIn(viewModelScope)
    }

}