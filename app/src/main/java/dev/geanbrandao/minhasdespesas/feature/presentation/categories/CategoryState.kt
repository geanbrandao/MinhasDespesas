package dev.geanbrandao.minhasdespesas.feature.presentation.categories

import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb

data class CategoryState(
    val categories: List<CategoryDb> = emptyList(),
    val isLoading: Boolean = false
)
