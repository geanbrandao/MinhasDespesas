package dev.geanbrandao.minhasdespesas.feature.presentation.expenses.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun BgSwipeToDelete() {
    val backgroundColor = MaterialTheme.colorScheme.errorContainer
    val iconColor = MaterialTheme.colorScheme.onErrorContainer
    val icon = Icons.Rounded.Delete

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(start = PaddingDefault)
        )
    }
}

@Composable
fun BgSwipeToEdit() {
    val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val iconColor = MaterialTheme.colorScheme.onSecondaryContainer
    val icon = Icons.Rounded.Edit

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .padding(end = PaddingDefault)
        )
    }
}

@Preview("BgSwipe")
@Composable
fun PreviewThis() {
    Column {
        BgSwipeToDelete()
        BgSwipeToEdit()
    }
}
