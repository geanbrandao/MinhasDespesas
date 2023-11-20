package dev.geanbrandao.minhasdespesas.domain.usecase

import br.dev.geanbrandao.common.domain.ignoreAccents
import dev.geanbrandao.minhasdespesas.domain.convertTo
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class GetCategoriesUsingIdUseCase(
    private val repository: MyExpensesRepository
) {

    operator fun invoke(ids: List<Long>) = flow {
        val categories = repository.getCategories(ids)
        val response = categories.map { it.convertTo() }.sortedBy { it.name.ignoreAccents() }
        emit(response)
    }
}