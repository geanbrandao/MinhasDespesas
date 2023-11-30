package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.data.repository.MyExpensesRepositoryImplTestHelper
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test


class AddCategoryUseCaseTest {

        private val repository: MyExpensesRepository = MyExpensesRepositoryImplTestHelper()
    private val useCase = AddCategoryUseCase(repository)
    private val repository2: MyExpensesRepository = mockk(relaxed = true)
    private val useCase2 = AddCategoryUseCase(repository2)

    @Test
    fun `when use case is called then return new category id`() = runBlocking {
        val id = useCase.invoke(categoryName = "New Category").first()
        val categories = repository.getCategories()

        assertEquals(3, id)
        assertEquals(4, categories.size)
    }

    @Test
    fun `when use case is called then call repository`() = runBlocking {
        coEvery {
            repository2.addCategory(any())
        } returns 1L

        useCase2.invoke("new category").first()

        coVerify {
            repository2.addCategory(
                CategoryEntity(
                    categoryId = 0,
                    name = "new category",
                    icon = "ic_tag",
                    canRemove = true
                )
            )
        }
    }

}