package dev.geanbrandao.minhasdespesas.feature.domain.model

import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterDateEnum.NONE

data class ActiveFiltersModel(
    val typeFilterDate: TypeFilterDateEnum = NONE,
    val selectedCategories: List<CategoryDb> = emptyList()
)
