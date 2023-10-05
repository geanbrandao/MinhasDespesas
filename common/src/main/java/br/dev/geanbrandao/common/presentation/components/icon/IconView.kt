package br.dev.geanbrandao.common.presentation.components.icon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import br.dev.geanbrandao.common.R
import br.dev.geanbrandao.common.domain.conditionalClickable
import br.dev.geanbrandao.common.domain.isNotNull
import br.dev.geanbrandao.common.presentation.components.icon.IconTypeEnum.DEFAULT
import br.dev.geanbrandao.common.presentation.components.icon.IconTypeEnum.INPUT
import br.dev.geanbrandao.common.presentation.components.icon.IconTypeEnum.SMALL
import br.dev.geanbrandao.common.presentation.resources.IconOne
import br.dev.geanbrandao.common.presentation.resources.IconThree
import br.dev.geanbrandao.common.presentation.resources.IconTwo

enum class IconTypeEnum {
    DEFAULT,
    INPUT,
    SMALL,
}

sealed class IconType(val iconType: IconTypeEnum) {
    data object Default : IconType(DEFAULT)
    data object Input: IconType(INPUT)
    data object Small: IconType(SMALL)
}

@Composable
fun IconView(
    icon: Painter,
    tint: Color,
    modifier: Modifier = Modifier,
    iconType: IconType = IconType.Default,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null
) {
    val size = when(iconType) {
        IconType.Default -> IconOne
        IconType.Input -> IconTwo
        IconType.Small -> IconThree
    }
    Icon(
        painter = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .size(size = size)
            .conditionalClickable(onClick.isNotNull()) {
                onClick?.invoke()
            }
        ,
        tint = tint,
    )
}

@Preview
@Composable
private fun IconViewPreview() {
    Column {
        IconView(
            icon = painterResource(id = R.drawable.ic_tag),
            tint = MaterialTheme.colorScheme.onSecondary,
            iconType = IconType.Input
        )
        IconView(
            icon = painterResource(id = R.drawable.ic_tag),
            tint = MaterialTheme.colorScheme.onSecondary,
            iconType = IconType.Small,
        )

    }
}