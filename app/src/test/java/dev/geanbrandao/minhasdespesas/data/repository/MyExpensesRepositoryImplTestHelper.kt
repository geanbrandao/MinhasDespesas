package dev.geanbrandao.minhasdespesas.data.repository

import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpenseEntity
import dev.geanbrandao.minhasdespesas.data.entity.ExpensesWithCategories
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import java.time.OffsetDateTime

class MyExpensesRepositoryImplTestHelper: MyExpensesRepository {

    private val PAGE_SIZE = 2

    private val categories = mutableListOf<CategoryEntity>(
        CategoryEntity(
            categoryId = 1,
            name = "Category 1",
            icon = "icon_name_1",
            canRemove = false,
        ),
        CategoryEntity(
            categoryId = 2,
            name = "Category 2",
            icon = "icon_name_2",
            canRemove = false,
        ),
        CategoryEntity(
            categoryId = 3,
            name = "Category 3",
            icon = "icon_name_3",
            canRemove = false,
        ),
    )

    private val expenses = mutableListOf<ExpensesWithCategories>(
        ExpensesWithCategories(
            expense = ExpenseEntity(
                expenseId = 1,
                amount = 11.1f,
                name = "Despesa 1",
                selectedDate = OffsetDateTime.now(),
                description = "Description 1",
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
            ),
            categories = categories,
        ),
        ExpensesWithCategories(
            expense = ExpenseEntity(
                expenseId = 2,
                amount = 22.2f,
                name = "Despesa 2",
                selectedDate = OffsetDateTime.now(),
                description = "Description 2",
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
            ),
            categories = listOf(),
        ),
        ExpensesWithCategories(
            expense = ExpenseEntity(
                expenseId = 3,
                amount = 33.3f,
                name = "Despesa 3",
                selectedDate = OffsetDateTime.now(),
                description = "Description 3",
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
            ),
            categories = listOf(),
        ),
    )

    override suspend fun addExpenses(
        expense: ExpenseEntity,
        categories: List<CategoryEntity>
    ): Long {
        expenses.add(
            ExpensesWithCategories(
                expense,
                categories,
            )
        )
        return expenses.lastIndex.toLong()
    }

    override suspend fun getExpenses(): List<ExpensesWithCategories> {
        return expenses
    }

    override suspend fun getExpenses(offset: Int): List<ExpensesWithCategories> {
        return expenses.subList(offset, offset + PAGE_SIZE)
    }

    override suspend fun getExpense(expenseId: Long): ExpensesWithCategories {
        return expenses.first { it.expense.expenseId == expenseId }
    }

    override suspend fun getCategories(): List<CategoryEntity> {
        return categories
    }

    override suspend fun getCategories(ids: List<Long>): List<CategoryEntity> {
        return categories.filter { it.categoryId in ids }
    }

    override suspend fun getCategory(categoryId: Long): CategoryEntity? {
        return categories.first { it.categoryId == categoryId }
    }

    override suspend fun addCategories(categories: List<CategoryEntity>) {
        this.categories.addAll(categories)
    }

    override suspend fun addCategory(category: CategoryEntity): Long {
        categories.add(category)
        return categories.lastIndex.toLong()
    }

    override suspend fun removeCategory(categoryId: Long) {
        categories.removeIf { it.categoryId == categoryId }
    }

    override suspend fun removeExpense(expenseId: Long) {
        expenses.removeIf { it.expense.expenseId == expenseId }
    }

}