package br.dev.geanbrandao.common.presentation.components.swipetoreveal

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.DragAnchors.Default
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.DragAnchors.End
import br.dev.geanbrandao.common.presentation.components.swipetoreveal.DragAnchors.Start
import kotlin.math.roundToInt

enum class DragAnchors {
    Default,
    Start,
    End,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBothSides(
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {

    val density = LocalDensity.current
    val maxSwipeWidth = with(density) { 56.dp.toPx() }

    val state = remember {
        RevealState(
            initialValue = Default,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    SwipeToReveal(
        state = state,
        maxSwipeWidth = maxSwipeWidth,
        directions = setOf(Start, End),
        background = {
            if (state.dismissDirection == Start) {
                BgSwipeToEdit {
                    onEditClicked()
                }
            } else if (state.dismissDirection == End) {
                BgSwipeToDelete {
                    onDeleteClicked()
                }
            }
        },
        dismissContent = content,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeRight(
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val density = LocalDensity.current
    val maxSwipeWidth = with(density) { 112.dp.toPx() }

    val state = remember {
        RevealState(
            initialValue = Default,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    SwipeToReveal(
        state = state,
        maxSwipeWidth = maxSwipeWidth,
        directions = setOf(Start),
        background = {
            if (state.dismissDirection == Start) {
                BgSwipeOneSide(
                    onEditClick = onEditClicked,
                    onDeleteClick = onDeleteClicked,
                )
            }
        },
        dismissContent = content,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeLeft(
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val density = LocalDensity.current
    val maxSwipeWidth = with(density) { 112.dp.toPx() }

    val state = remember {
        RevealState(
            initialValue = Default,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    SwipeToReveal(
        state = state,
        maxSwipeWidth = maxSwipeWidth,
        directions = setOf(End),
        background = {
            if (state.dismissDirection == End) {
                BgSwipeOneSide(
                    onEditClick = onEditClicked,
                    onDeleteClick = onDeleteClicked,
                    isLeft = true,
                )
            }
        },
        dismissContent = content,
        modifier = modifier,
    )
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
                    Default at 0f
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
        animationSpec = animationSpec,
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

@Preview
@Composable
private fun SwipeBothSidesPreview() {
    SwipeBothSides(
        onEditClicked = {},
        onDeleteClicked = {},
        content = {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(55.dp)
                    .fillMaxWidth()
            )
        }
    )
}

    @Preview
@Composable
private fun SwipeRightPreview() {
    SwipeRight(
        onEditClicked = {},
        onDeleteClicked = {},
        content = {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(55.dp)
                    .fillMaxWidth()
            )
        }
    )
}

@Preview
@Composable
private fun SwipeLeftPreview() {
    SwipeLeft(
        onEditClicked = {},
        onDeleteClicked = {},
        content = {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(55.dp)
                    .fillMaxWidth()
            )
        }
    )
}

