package dev.geanbrandao.minhasdespesas.domain.usecase

import br.dev.geanbrandao.common.domain.isNotNull
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class RemoveCategoryUseCase(
    private val repository: MyExpensesRepository,
) {

    operator fun invoke(categoryId: Long)  = flow {
        repository.removeCategory(categoryId = categoryId)
        val category = repository.getCategory(categoryId = categoryId)
        if (category.isNotNull()) {
            emit(false)
        } else {
            emit(true)
        }
    }
}