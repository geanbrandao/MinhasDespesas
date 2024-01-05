package dev.geanbrandao.minhasdespesas.domain.usecase.preferences

import dev.geanbrandao.minhasdespesas.localpreferences.data.PreferencesDataStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetSelectedCategoriesIdsUseCaseTest {

    private val preferencesDataStore: PreferencesDataStore = mockk(relaxed = true)
    private val useCase = GetSelectedCategoriesIdsUseCase(preferencesDataStore)

    @Test
    fun `when use case is then call preferencesDataStore`() = runBlocking {
        coEvery {
            preferencesDataStore.getSelectedCategoriesIds()
        } returns flowOf("1,2,4")

        val result = useCase().first()

        coVerify { preferencesDataStore.getSelectedCategoriesIds() }
        assertEquals(listOf(1L, 2L, 4L), result)
    }

}