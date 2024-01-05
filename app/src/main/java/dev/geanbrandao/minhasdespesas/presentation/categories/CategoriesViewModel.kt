package dev.geanbrandao.minhasdespesas.presentation.categories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import dev.geanbrandao.minhasdespesas.domain.usecase.preferences.PreferencesUseCases
import dev.geanbrandao.minhasdespesas.navigation.data.AppNavigator
import dev.geanbrandao.minhasdespesas.navigation.domain.Destination
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_CATEGORIES = "category"

@KoinViewModel
class CategoriesViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
    private val preferencesUseCases: PreferencesUseCases,
    private val appNavigator: AppNavigator,
) : ViewModel() {



//    private val argSelectedCategories = state.getStateFlow<String?>(Key.SELECTED_CATEGORIES, null)
//    private val argSelectedCategories: String? = state[Key.SELECTED_CATEGORIES]

    val categories = state.getStateFlow<List<Category>>(key = KEY_CATEGORIES, initialValue = emptyList())

    fun getCategories() {
        viewModelScope.launch {
            combine(
                useCases.getCategories(), preferencesUseCases.getSelectedCategoriesIdsUseCase()
            ) { categories: List<Category>, ids: List<Long> ->
                categories.map { category ->
                    if (category.categoryId in ids) {
                        category.copy(isChecked = true)
                    } else {
                        category
                    }
                }
            }.catch {
                throw Exception(it)
            }.collect { result: List<Category> ->
                state[KEY_CATEGORIES] = result
            }
        }
    }


    fun updateSelectedCategories(categoryId: Long, isChecked: Boolean) {
        state[KEY_CATEGORIES] = categories.value.map { category ->
            if(categoryId == category.categoryId ) {
                category.copy(isChecked = isChecked)
            } else {
                category
            }
        }
    }

    fun addNewCategory(newCategoryName: String) {
        viewModelScope.launch {
            useCases.addCategory(categoryName = newCategoryName)
                .catch {
                    throw Exception(it)
                }.collect {
                    if (it != -1L) {
                        getCategories()
                    }
                }
        }
    }

    fun removeCategory(categoryId: Long) {
        viewModelScope.launch {
            useCases.removeCategory(categoryId = categoryId)
                .catch { throw Exception(it) }
                .collect {
                    getCategories()
                }
        }
    }


    fun navigateBack() = appNavigator.tryNavigateBack()

    fun navigateBack(selectedIds: List<Long>) {
        viewModelScope.launch {
            preferencesUseCases.setSelectedCategoriesIdsUseCase(selectedIds)
                .catch { throw Exception(it) }
                .collect {
                    appNavigator.navigateBack(
                        route = Destination.Expense.fullRoute
                    )
                }
        }
    }
}