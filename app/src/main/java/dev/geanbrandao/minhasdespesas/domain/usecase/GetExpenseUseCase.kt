package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.domain.convertTo
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetExpenseUseCase(
    private val repository: MyExpensesRepository,
) {
    operator fun invoke(expenseId: Long) = flow {
        val response = repository.getExpense(expenseId)
        val categories = response.categories.map { categoryEntity: CategoryEntity ->
            categoryEntity.convertTo()
        }
        emit(response.convertTo(categories))
    }
}