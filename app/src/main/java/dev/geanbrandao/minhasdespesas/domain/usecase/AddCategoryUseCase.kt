package dev.geanbrandao.minhasdespesas.domain.usecase

import dev.geanbrandao.minhasdespesas.domain.convertTo
import dev.geanbrandao.minhasdespesas.domain.model.Category
import dev.geanbrandao.minhasdespesas.domain.repository.MyExpensesRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class AddCategoryUseCase(
    private val repository: MyExpensesRepository,
) {
    operator fun invoke(categoryName: String) = flow {
        val category = Category(
            categoryId = 0,
            name = categoryName,
            icon = "ic_tag",
            canRemove = true,
        )
        val newCategoryId = repository.addCategory(category.convertTo())
        emit(newCategoryId)
    }
}