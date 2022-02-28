package dev.geanbrandao.minhasdespesas.feature.domain.model

import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import java.io.Serializable

data class SelectedCategoriesArg(
    val list: List<CategoryDb>
): Serializable
