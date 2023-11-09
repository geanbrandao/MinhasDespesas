package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.domain.convertTo
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class UpdateExpenseUseCase(
    private val repository: MyExpensesRepository,
) {
    operator fun invoke(expense: Expense) = flow {
        val id = repository.addExpenses(
            expense = expense.convertTo(),
            categories = expense.categories.convertTo()
        )
        emit(id)
    }
}