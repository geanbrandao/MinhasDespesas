package dev.geanbrandao.minhasdespesas.domain.usecase.preferences

import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SetSelectedCategoriesIdsUseCaseTest {

    private val preferencesDataStore: PreferencesDataStore = mockk(relaxed = true)
    private val useCase = SetSelectedCategoriesIdsUseCase()

    @Test
    fun `when use case is invoke then call preferencesDataStore`() = runBlocking {
        val ids = listOf<Long>(1L, 2L, 3L)
        useCase(ids)

        coVerify {
            preferencesDataStore.setSelectedCategoriesIds("1,2,3")
        }
    }
}