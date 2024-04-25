package br.dev.geanbrandao.common.presentation.components.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerTypeEnum.FIVE
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerTypeEnum.FOUR
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerTypeEnum.ONE
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerTypeEnum.THREE
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerTypeEnum.TWO
import br.dev.geanbrandao.common.presentation.resources.MarginFour
import br.dev.geanbrandao.common.presentation.resources.MarginOne
import br.dev.geanbrandao.common.presentation.resources.MarginThree
import br.dev.geanbrandao.common.presentation.resources.MarginTwo

enum class SpacerTypeEnum {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
}

sealed class SpacerType(val spacerType: SpacerTypeEnum) {
    data object One : SpacerType(ONE)
    data object Two : SpacerType(TWO)
    data object Three : SpacerType(THREE)
    data object Four : SpacerType(FOUR)
    data object Five : SpacerType(FIVE)
}

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
fun SpacerFive(modifier: Modifier) {
    Spacer(modifier = modifier.size(size = MarginFour))
}