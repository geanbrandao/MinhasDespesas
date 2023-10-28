package br.dev.geanbrandao.common.presentation.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.dev.geanbrandao.common.presentation.components.button.ButtonTypeEnum.ACTION
import br.dev.geanbrandao.common.presentation.components.button.ButtonTypeEnum.DIALOG
import br.dev.geanbrandao.common.presentation.components.button.ButtonTypeEnum.PRIMARY
import br.dev.geanbrandao.common.presentation.components.button.ButtonTypeEnum.SECONDARY
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerThree
import br.dev.geanbrandao.common.presentation.components.text.TextAction
import br.dev.geanbrandao.common.presentation.components.text.TextButtonDefault
import br.dev.geanbrandao.common.presentation.components.text.TextSmall
import br.dev.geanbrandao.common.presentation.resources.ButtonPrimaryHeight
import br.dev.geanbrandao.common.presentation.resources.CornersDefault

private val ShapePrimaryButton = RoundedCornerShape(size = CornersDefault)

enum class ButtonTypeEnum {
    PRIMARY,
    SECONDARY,
    ACTION,
    DIALOG,
}

sealed class ButtonType(val buttonType: ButtonTypeEnum) {
    data object Primary : ButtonType(PRIMARY)
    data object Secondary : ButtonType(SECONDARY)
    data object Action : ButtonType(ACTION)

    data object Dialog: ButtonType(DIALOG)
}

@Composable
fun ButtonView(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    buttonType: ButtonType = ButtonType.Primary,
    onClick: () -> Unit,
) {
    // todo descobrir uma forma de tratar as cores do texto de acordo com o isEnabled
    when (buttonType) {
        ButtonType.Action -> {
            ButtonAction(
                text = text,
                isEnabled = isEnabled,
                modifier = modifier,
                onClick = onClick
            )
        }

        ButtonType.Primary -> {
            ButtonPrimary(
                text = text,
                isEnabled = isEnabled,
                modifier = modifier,
                onClick = onClick
            )
        }

        ButtonType.Secondary -> TODO()

        ButtonType.Dialog -> {
            ButtonDialog(
                text = text,
                isEnabled = isEnabled,
                modifier = modifier,
                onClick = onClick
            )
        }
    }
}

@Composable
fun ButtonPrimary(
    text: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier.background(color = Color.White, shape = ShapePrimaryButton)
    ) {
        Button(
            onClick = { onClick() },
            enabled = isEnabled,
            shape = ShapePrimaryButton,
            modifier = Modifier
                .fillMaxWidth()
                .height(height = ButtonPrimaryHeight),
        ) {
            val textColor = if (isEnabled) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            }
            TextButtonDefault(
                text = text,
                color = textColor,
            )
        }
    }
}

//@Composable
//fun ButtonSecondary (
//    text: String,
//    isEnabled: Boolean,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit,
//) {
//    Box(
//        modifier.background(color = Color.White, shape = ShapePrimaryButton)
//    ) {
//        Button(
//            onClick = { onClick() },
//            enabled = isEnabled,
//            shape = ShapePrimaryButton,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(height = ButtonPrimaryHeight),
//        ) {
//            val textColor = if (isEnabled) {
//                MaterialTheme.colorScheme.onPrimary
//            } else {
//                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
//            }
//            TextButtonDefault(
//                text = text,
//                color = textColor,
//            )
//        }
//    }
//}

@Composable
fun ButtonAction(
    text: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = { onClick() },
        enabled = isEnabled,
        shape = ShapePrimaryButton,
        modifier = modifier
            .height(height = ButtonPrimaryHeight),
    ) {
        TextAction(
            text = text,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun ButtonDialog(
    text: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier
    ) {
        TextSmall(
            text = text,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Preview
@Composable
private fun ButtonViewPreview() {
    Column(modifier = Modifier.background(Color.Yellow)) {
        ButtonView(text = "Botão", isEnabled = true, buttonType = ButtonType.Primary) {

        }
        SpacerThree()
        ButtonPrimary(text = "Primary", isEnabled = false) {

        }
        SpacerThree()
//        ButtonSecondary(text = "Secondary", isEnabled = true) {
//
//        }
        SpacerThree()
        ButtonView(
            text = "Botão",
            isEnabled = true,
            buttonType = ButtonType.Action,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

        }
        SpacerThree()
        ButtonView(
            text = "Botão",
            isEnabled = true,
            buttonType = ButtonType.Dialog,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

        }
    }
}