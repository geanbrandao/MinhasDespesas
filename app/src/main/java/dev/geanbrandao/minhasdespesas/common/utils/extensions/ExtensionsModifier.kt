package dev.geanbrandao.minhasdespesas.common.utils.extensions

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

inline fun Modifier.clickableRoundedEffect(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(bounded = false),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}
inline fun Modifier.clickableNoRippleEffect(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = rememberRipple(bounded = false),
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Log.d("CLICK", "NoRippleEffect")
        onClick()
    }
}
