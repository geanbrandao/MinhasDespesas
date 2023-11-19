package br.dev.geanbrandao.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import br.dev.geanbrandao.common.R
import br.dev.geanbrandao.common.domain.CategoryIconUtils
import br.dev.geanbrandao.common.presentation.resources.CornersDefault
import br.dev.geanbrandao.common.presentation.resources.PaddingHalf

@Composable
fun CategoryIconView(
    iconName: String,
    backgroundColor: Color,
    iconColor: Color,
) {
    val icon = getPainterIconFromString(iconName)
    CategoryIcon(icon = icon, backgroundColor = backgroundColor, iconColor = iconColor)
}

@Composable
private fun CategoryIcon(
    icon: Painter,
    backgroundColor: Color,
    iconColor: Color,
) {
    Icon(
        painter = icon,
        tint = iconColor,
        contentDescription = "Icon that represents the category",
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(CornersDefault))
            .padding(PaddingHalf)
    )
}

@Composable
fun getPainterIconFromString(iconIdName: String): Painter {
    val iconId = CategoryIconUtils.getCategoryIcon(iconIdName)
    return painterResource(id = iconId)
}

@Preview
@Composable
fun CategoryIconPreview() {
    CategoryIcon(
        painterResource(id = R.drawable.ic_bus),
        backgroundColor = Color.Green,
        iconColor = Color.White
    )
}