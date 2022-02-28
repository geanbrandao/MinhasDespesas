package dev.geanbrandao.minhasdespesas.feature.presentation.add

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.PostExpense
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
): ViewModel() {

    private val _stateSelectCategories = mutableStateOf(emptyList<CategoryDb>())
    private val stateSelectCategories: State<List<CategoryDb>> = _stateSelectCategories

    fun getSelectedCategories() = _stateSelectCategories.value
//    fun postExpense()
}