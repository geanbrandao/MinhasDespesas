package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.domain.convertTo
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class AddExpenseUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke(
        expense: Expense,
        selectedCategories: List<Category>
    ): Flow<Long> = flow {

        val expenseId = repository.addExpenses(
            expense = expense.convertTo(),
            categories = selectedCategories.map { it.convertTo() }
        )
        emit(expenseId)
    }


}