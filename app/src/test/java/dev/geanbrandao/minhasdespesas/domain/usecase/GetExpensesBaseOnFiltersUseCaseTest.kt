package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import io.mockk.mockk

class GetExpensesBaseOnFiltersUseCaseTest {

    private val repository: MyExpensesRepository = mockk(relaxed = true)
    val useCase = GetExpensesBaseOnFiltersUseCase(repository)

//    @Test
//    fun `when selected filters is empty return all expenses`() {
//
//        coEvery {
//            repository.getExpenses(offset = 40 * 1)
//        } returns
//    }

//    private fun createReturnExpenses() = listOf<ExpensesWithCategories>(
//        ExpensesWithCategories(
//            expense = ExpenseEntity(
//                expenseId = 1,
//                amount = 23.5f,
//                name = "expense 1",
//
//            )
//        )
//    )
}