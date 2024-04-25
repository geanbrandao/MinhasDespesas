package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories
import dev.geanbrandao.minhasdespesas.domain.convertTo
import dev.geanbrandao.minhasdespesas.domain.model.Expense
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetExpensesUseCase(
    private val repository: MyExpensesRepository,
) {
    operator fun invoke(): Flow<List<Expense>> = flow {
        val expenses = repository.getExpenses()

        val response = expenses.map { expenseWithCategory: ExpensesWithCategories ->
            val categories = expenseWithCategory.categories.map { categoryEntity: CategoryEntity ->
                categoryEntity.convertTo()
            }
            expenseWithCategory.convertTo(categories = categories)
        }
        emit(response)
    }
}