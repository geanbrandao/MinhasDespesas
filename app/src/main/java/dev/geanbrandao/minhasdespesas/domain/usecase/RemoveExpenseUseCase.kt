package dev.geanbrandao.minhasdespesas.domain.usecase

import br.dev.geanbrandao.common.domain.isNotNull
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class RemoveExpenseUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke(expenseId: Long) = flow {
        repository.removeExpense(expenseId = expenseId)
        val expense = repository.getExpense(expenseId = expenseId)
        if (expense.isNotNull()) {
            emit(false)
        } else {
            emit(true)
        }
    }
}