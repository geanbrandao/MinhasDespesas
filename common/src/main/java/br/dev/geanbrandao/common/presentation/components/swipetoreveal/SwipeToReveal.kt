package br.dev.geanbrandao.common.presentation.components.swipetoreveal

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import br.dev.geanbrandao.common.R
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.DragAnchors.End
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.DragAnchors.Start
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import kotlin.math.roundToInt

enum class DragAnchors {
    Default,
    Start,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@ExperimentalMaterial3Api
fun SwipeToReveal(
    state: RevealState,
    maxSwipeWidth: Float,
    background: @Composable RowScope.() -> Unit,
    dismissContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    directions: Set<DragAnchors> = setOf(Start, End)
) {

    Box(
        modifier
            .anchoredDraggable(
                state = state.anchoredDraggableState,
                orientation = Orientation.Horizontal,
            )
            .onSizeChanged { _ ->
                val newAnchors = DraggableAnchors {
                    DragAnchors.Default at 0f
                    if (Start in directions) {
                        Start at maxSwipeWidth
                    }
                    if (End in directions) {
                        End at -maxSwipeWidth
                    }
                }
                state.anchoredDraggableState.updateAnchors(newAnchors)
            }
    ) {
        Row(
            content = background,
            modifier = Modifier.matchParentSize()
        )
        Row(
            content = dismissContent,
            modifier = Modifier.offset { IntOffset(state.requireOffset().roundToInt(), 0) }
        )
    }
}


@Composable
fun BgSwipeToDelete(
    onClick: () -> Unit,
) {
    val backgroundColor = MaterialTheme.colorScheme.errorContainer
    val iconColor = MaterialTheme.colorScheme.onErrorContainer

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        IconView(
            icon = painterResource(id = R.drawable.ic_delete),
            tint = iconColor,
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .padding(horizontal = PaddingTwo),
            onClick = onClick,
        )
    }
}

@Composable
fun BgSwipeToEdit(
    onClick: () -> Unit,
) {
    val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val iconColor = MaterialTheme.colorScheme.onSecondaryContainer

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        IconView(
            icon = painterResource(id = R.drawable.ic_edit),
            tint = iconColor,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(horizontal = PaddingTwo),
            onClick = onClick,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
class RevealState constructor(
    initialValue: DragAnchors,
    positionalThreshold: (totalDistance: Float) -> Float,
    velocityThreshold: () -> Float,
    animationSpec: AnimationSpec<Float>,
    confirmValueChange: (DragAnchors) -> Boolean = { true },
) {

    internal val anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        animationSpec =  animationSpec,
        confirmValueChange = confirmValueChange,
        positionalThreshold = positionalThreshold,
        velocityThreshold = velocityThreshold,
    )

    internal val offset: Float get() = anchoredDraggableState.offset

    fun requireOffset(): Float = anchoredDraggableState.requireOffset()

    val dismissDirection: DragAnchors?
        get() = if (offset == 0f || offset.isNaN())
            null
        else if (offset > 0f) Start else End
}