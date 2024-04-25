package dev.geanbrandao.minhasdespesas.feature.presentation.add

import androidx.lifecycle.ViewModel
import dev.geanbrandao.minhasdespesas.feature.domain.use_case.ExpenseUseCases
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddScreenViewModel(
    private val expenseUseCases: ExpenseUseCases
): ViewModel() {

//    private val _stateSelectCategories = mutableStateOf(emptyList<CategoryDb>())
//    private val stateSelectCategories: State<List<CategoryDb>> = _stateSelectCategories
//
//    fun getSelectedCategories() = _stateSelectCategories.value
////    fun postExpense()
}