package dev.geanbrandao.minhasdespesas.presentation.categories

import androidx.compose.ui.graphics.painter.Painter

data class CategoryItem(
    val id: Long,
    val name: String,
    val icon: Painter,
    val canRemove: Boolean = false,
) // todo excluir
