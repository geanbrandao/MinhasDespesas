package br.dev.geanbrandao.common.presentation.components.swipetoreveal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.dev.geanbrandao.common.R
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo

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
        SwipeIconText(
            icon = painterResource(id = R.drawable.ic_delete),
            tintColor = iconColor,
            bgColor = backgroundColor,
            text = stringResource(R.string.swipe_icon_remove_text),
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun BgSwipeToEdit(
    onClick: () -> Unit,
) {
    val bgColor = MaterialTheme.colorScheme.secondaryContainer
    val iconColor = MaterialTheme.colorScheme.onSecondaryContainer

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = bgColor)
    ) {
        SwipeIconText(
            icon = painterResource(id = R.drawable.ic_edit),
            tintColor = iconColor,
            bgColor = bgColor,
            text = stringResource(R.string.swipe_icon_edit_text),
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
fun BgSwipeOneSide(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isLeft: Boolean = false,
) {
    val editBgColor = MaterialTheme.colorScheme.secondaryContainer
    val editIconColor = MaterialTheme.colorScheme.onSecondaryContainer
    val deleteBgColor = MaterialTheme.colorScheme.errorContainer
    val deleteIconColor = MaterialTheme.colorScheme.onErrorContainer
    Row(
        modifier = Modifier.fillMaxHeight()
    ) {
        if (isLeft) {
            Spacer(modifier = Modifier.weight(1f))
        }
        SwipeIconText(
            icon = painterResource(id = R.drawable.ic_edit),
            tintColor = editIconColor,
            bgColor = editBgColor,
            text = stringResource(R.string.swipe_icon_edit_text),
            onClick = onEditClick,
        )
        SwipeIconText(
            icon =  painterResource(id = R.drawable.ic_delete),
            tintColor = deleteIconColor,
            bgColor = deleteBgColor,
            text = stringResource(R.string.swipe_icon_remove_text),
            onClick = onDeleteClick,
        )
    }
}

@Composable
private fun SwipeIconText(
    icon: Painter,
    tintColor: Color,
    bgColor: Color,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .background(color = bgColor)
            .fillMaxHeight(),
    ) {
        IconView(
            icon = icon,
            tint = tintColor,
            onClick = onClick,
            modifier = Modifier
                .background(color = bgColor)
                .padding(horizontal = PaddingTwo),
        )
        Text(text = text, fontSize = 12.sp)
    }
}
@Preview
@Composable
private fun SwipeIconTextPreview() {
    Column {
        Box(
            Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            SwipeIconText(
                icon =  painterResource(id = R.drawable.ic_delete),
                tintColor = Color.Black,
                bgColor = Color.Green,
                text = "Remover",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun BgSwipeToEditPreview() {
    Box(
        Modifier
            .height(45.dp)
            .fillMaxWidth()
    ) {
        BgSwipeToEdit {}
    }
}

@Preview
@Composable
fun BgSwipeToDeletePreview() {
    Box(
        Modifier
            .height(45.dp)
            .fillMaxWidth()
    ) { BgSwipeToDelete {} }
}

@Preview
@Composable
fun BgSwipeOneSidePreview() {
    Column {
        Box(
            Modifier
                .height(45.dp)
                .fillMaxWidth()
        ) {
            BgSwipeOneSide(
                onEditClick = {},
                onDeleteClick = {},
                isLeft = true,
            )
        }

        Box(
            Modifier
                .height(45.dp)
                .fillMaxWidth()
        ) {
            BgSwipeOneSide(
                onEditClick = {},
                onDeleteClick = {},
            )
        }
    }
}