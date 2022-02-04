package dev.geanbrandao.minhasdespesas.common.components.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.geanbrandao.minhasdespesas.ui.theme.MarginFour
import dev.geanbrandao.minhasdespesas.ui.theme.MarginOne
import dev.geanbrandao.minhasdespesas.ui.theme.MarginThree
import dev.geanbrandao.minhasdespesas.ui.theme.MarginTwo

@Composable
fun SpacerOne() {
    Spacer(modifier = Modifier.size(size = MarginOne))
}

@Composable
fun SpacerTwo() {
    Spacer(modifier = Modifier.size(size = MarginTwo))
}

@Composable
fun SpacerThree() {
    Spacer(modifier = Modifier.size(size = MarginThree))
}

@Composable
fun SpacerFour() {
    Spacer(modifier = Modifier.size(size = MarginFour))
}

@Composable
fun SpacerFill(modifier: Modifier) {
    Spacer(modifier = modifier.size(size = MarginFour))
}