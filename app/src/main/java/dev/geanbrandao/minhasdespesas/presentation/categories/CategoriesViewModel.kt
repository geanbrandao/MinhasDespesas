package dev.geanbrandao.minhasdespesas.presentation.categories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.usecase.MyExpensesUseCases
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Key
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

private const val KEY_CATEGORIES = "category"

@KoinViewModel
class CategoriesViewModel(
    private val state: SavedStateHandle,
    private val useCases: MyExpensesUseCases,
) : ViewModel() {

    private val argSelectedCategories = state.getStateFlow<String?>(Key.SELECTED_CATEGORIES, null)
    val categories = state.getStateFlow<List<Category>>(key = KEY_CATEGORIES, initialValue = emptyList())


    init {
        getCategories()
        println(argSelectedCategories.value)
    }

    fun getCategories() {
        viewModelScope.launch {
            useCases.getCategories()
                .catch {
                    throw Exception(it)
                }.collect {
                    val idList = argSelectedCategories.value?.split(",")?.map { id -> id.toLong() }.orEmpty()
                    val result = it.map { category: Category ->
                        if (category.categoryId in idList) {
                            category.copy(isChecked = true)
                        } else {
                            category
                        }
                    }
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
}