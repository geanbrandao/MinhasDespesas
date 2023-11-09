package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import org.koin.core.annotation.Factory

@Factory
class GetExpensesBaseOnFiltersUseCase(
    private val repository: MyExpensesRepository,
) {

}