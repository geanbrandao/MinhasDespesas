package dev.geanbrandao.minhasdespesas.feature.domain.model

import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.model.TypeFilterDate.NONE

data class ActiveFiltersModel(
    val typeFilterDate: TypeFilterDate = NONE,
    val selectedCategories: List<CategoryDb> = emptyList()
)
