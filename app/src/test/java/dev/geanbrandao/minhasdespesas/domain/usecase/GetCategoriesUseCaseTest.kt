package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCategoriesUseCaseTest {


    // Mock the repository using MockK
    private val repository: MyExpensesRepository = mockk(relaxed = true)

    private var getCategoriesUseCase: GetCategoriesUseCase = GetCategoriesUseCase(repository)
    private val categoriesResult = listOf(
        Category(
            categoryId = 0,
            name = "Category",
            icon = "icon",
            canRemove = false,
        )
    )
    private val categoriesMockk = listOf(
        CategoryEntity(
            categoryId = 0,
            name = "Category",
            icon = "icon",
            canRemove = false
        )
    )

    @Test
    fun `when GetCategoriesUseCase then return correct data`() = runBlocking {
        // Mock the behavior of the repository
        coEvery {
            repository.getCategories()
        } returns categoriesMockk

        // Invoke the use case
        val result = getCategoriesUseCase().first()

        // Verify that the repository method was called
        coVerify { repository.getCategories() }

        // Assert the result matches the test data
        assertEquals(expected = categoriesResult, actual = result)
    }

    @Test
    fun `test filter category`() {
        val newCategories = listOf(
            CategoryEntity(0, "Categoria 0", "icon 0", canRemove = false),
            CategoryEntity(0, "Categoria 2", "icon 2", canRemove = false),
        )
        val defaultCategories = listOf(
            CategoryEntity(0, "Categoria 0", "icon 0", canRemove = false),
            CategoryEntity(1, "Categoria 1", "icon 1", canRemove = false),
        )
        val categoriesMustAdd = newCategories.filterNot { newCategory ->
            defaultCategories.any { previousCategory ->
                newCategory.name == previousCategory.name &&
                        newCategory.canRemove == previousCategory.canRemove &&
                        newCategory.icon == previousCategory.icon
            }
        }


        val categoriesMustRemove = defaultCategories.filterNot { previousCategory ->
            newCategories.any { newCategory ->
                newCategory.name == previousCategory.name &&
                        newCategory.canRemove == previousCategory.canRemove &&
                        newCategory.icon == previousCategory.icon
            }
        }

        assertEquals(listOf(CategoryEntity(0, "Categoria 2", "icon 2", canRemove = false)), categoriesMustAdd)
        assertEquals(listOf(CategoryEntity(1, "Categoria 1", "icon 1", canRemove = false)), categoriesMustRemove)
    }

    private fun filterCategory() {

    }
}