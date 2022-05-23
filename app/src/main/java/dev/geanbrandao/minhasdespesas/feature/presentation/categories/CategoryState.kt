package dev.geanbrandao.minhasdespesas.feature.presentation.categories

import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.presentation.common.DefaultState

data class CategoryState(
    override val isLoading: Boolean = false,
    override val dataList: List<CategoryDb> = emptyList(),
    override val data: CategoryDb? = null
) : DefaultState<CategoryDb>