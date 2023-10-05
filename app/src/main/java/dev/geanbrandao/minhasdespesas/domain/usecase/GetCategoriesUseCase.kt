package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.domain.convertTo
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetCategoriesUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke() : Flow<List<Category>> = flow {
        val categories = repository.getCategories()
        val response =  categories.map {
            it.convertTo()
        }.sortedBy { it.name }
        emit(response)
    }
}