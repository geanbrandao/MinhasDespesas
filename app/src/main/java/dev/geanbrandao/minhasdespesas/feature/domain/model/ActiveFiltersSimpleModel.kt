package dev.geanbrandao.minhasdespesas.feature.domain.model

import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.hekko.TypeFilterEnum

data class ActiveFiltersSimpleModel(
    val typeFilter: TypeFilterEnum,
    val date: String?,
    val categoryDb: CategoryDb?,
)
