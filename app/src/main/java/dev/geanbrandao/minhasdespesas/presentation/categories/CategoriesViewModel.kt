package dev.geanbrandao.minhasdespesas.presentation.categories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_CATEGORY = "category"

@KoinViewModel
class CategoriesViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
) : ViewModel() {

    val categories = state.getStateFlow<List<Category>>(key = KEY_CATEGORY, initialValue = emptyList())

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            useCases.getCategories()
                .catch {
                    throw Exception(it)
                }.collect {
                    state[KEY_CATEGORY] = it
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
}