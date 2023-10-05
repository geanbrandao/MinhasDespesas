package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class AddCategoriesUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke(categories: List<CategoryEntity>) = flow {
        repository.addCategories(categories)
        emit(true)
    }
}