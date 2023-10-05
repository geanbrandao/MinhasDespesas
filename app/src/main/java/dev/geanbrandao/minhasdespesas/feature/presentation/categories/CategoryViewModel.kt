package dev.geanbrandao.minhasdespesas.feature.presentation.categories

import androidx.lifecycle.ViewModel
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import kotlinx.coroutines.Job
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CategoryViewModel(
    private val expenseUseCases: ExpenseUseCases
) : ViewModel() {
//    private val _state = mutableStateOf(CategoryState())
//    private val _stateSelectedCategories = mutableStateOf(emptyList<CategoryDb>())
//
//    val state: State<CategoryState> = _state

    private var getCategoriesJob: Job? = null

    init {
        getCategories()
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()
//        getCategoriesJob = expenseUseCases.getCategories()
//            .onEach { list ->
//                _state.value = _state.value.copy(dataList = list)
//            }.catch {
//                // error
//                Log.e("HELLOO", "error", it)
//            }.launchIn(viewModelScope)
    }

//    fun onCategoryCheckChange(isChecked: Boolean, categoryDb: CategoryDb) {
//        if (isChecked) {
//            _stateSelectedCategories.value = _stateSelectedCategories.value.plus(element = categoryDb)
//        } else {
//            _stateSelectedCategories.value = _stateSelectedCategories.value.filter {
//                it.categoryId != categoryDb.categoryId
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        getCategoriesJob?.cancel()
    }
}