package dev.geanbrandao.minhasdespesas.common.utils.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

inline fun Modifier.clickableRoundedEffect(crossinline onClick: ()-> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(bounded = false),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}