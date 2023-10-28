package br.dev.geanbrandao.common.presentation.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.geanbrandao.common.R
import br.dev.geanbrandao.common.domain.MoneyUtils
import br.dev.geanbrandao.common.presentation.components.icon.IconType
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.inputs.InputTypeEnum.DEFAULT
import br.dev.geanbrandao.common.presentation.components.inputs.InputTypeEnum.LARGE
import br.dev.geanbrandao.common.presentation.components.inputs.InputTypeEnum.MONEY
import br.dev.geanbrandao.common.presentation.components.text.TextLabelInput
import br.dev.geanbrandao.common.presentation.components.text.TextSupporting
import br.dev.geanbrandao.common.presentation.resources.InputLargeHeight

private const val MAX_LENGTH_DEFAULT = 30
private const val MAX_LENGTH_MONEY = 9
private const val MAX_LENGTH_LARGE = 80

enum class InputTypeEnum {
    DEFAULT,
    MONEY,
    LARGE,
}

sealed class InputType(val inputType: InputTypeEnum, val maxLength: Int) {
    data object Default : InputType(DEFAULT, MAX_LENGTH_DEFAULT)
    data object Money : InputType(MONEY, MAX_LENGTH_MONEY)
    data object Large : InputType(LARGE, MAX_LENGTH_LARGE)
}

class InputViewState(
    val textLabel: String,
    textInput: String = "",
    val onTextChange: (text: String) -> Unit = {},
) {
    val textInput = mutableStateOf(
        TextFieldValue(text = textInput, selection = TextRange(textInput.length))
    )

    fun updateInput(textFieldValue: TextFieldValue) {
        textInput.value = textFieldValue
        onTextChange(textFieldValue.text)
    }
}

data class InputViewData(
    val leadingIcon: Painter? = null,
    val trailingIcon: Painter? = null,
    val keyboardOptions: KeyboardOptions = KeyboardOptions()
)

@Composable
fun InputView(
    inputViewState: InputViewState,
    inputViewData: InputViewData,
    modifier: Modifier = Modifier,
    inputType: InputType = InputType.Default,
) {
    when (inputType) {
        InputType.Default -> {
            InputDefault(
                textLabel = inputViewState.textLabel,
                textInput = inputViewState.textInput.value,
                leadingIcon = inputViewData.leadingIcon,
                trailingIcon = inputViewData.trailingIcon,
                keyboardOptions = inputViewData.keyboardOptions,
                maxLength = inputType.maxLength,
                modifier = modifier,
            ) {
                inputViewState.updateInput(it)
            }
        }

        InputType.Money -> {
            InputMoney(
                textLabel = inputViewState.textLabel,
                textInput = inputViewState.textInput.value,
                leadingIcon = inputViewData.leadingIcon,
                trailingIcon = inputViewData.trailingIcon,
                maxLength = inputType.maxLength,
                modifier = modifier,
            ) {
                inputViewState.updateInput(it)
            }
        }

        InputType.Large -> {
            InputLarge(
                textLabel = inputViewState.textLabel,
                textInput = inputViewState.textInput.value,
                leadingIcon = inputViewData.leadingIcon,
                trailingIcon = inputViewData.trailingIcon,
                maxLength = inputType.maxLength,
                modifier = modifier,
                onTextChange = {
                    inputViewState.updateInput(it)
                }
            )
        }
    }
}

@Composable
fun InputLarge(
    textLabel: String,
    textInput: TextFieldValue,
    leadingIcon: Painter?,
    trailingIcon: Painter?,
    maxLength: Int,
    modifier: Modifier = Modifier,
    onTextChange: (value: TextFieldValue) -> Unit,
) {
    InputDefault(
        textLabel = textLabel,
        textInput = textInput,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = false,
        maxLength = maxLength,
        onTextChange = onTextChange,
        modifier = modifier.height(height = InputLargeHeight)
    )
}

@Composable
fun InputMoney(
    textLabel: String,
    textInput: TextFieldValue,
    leadingIcon: Painter?,
    trailingIcon: Painter?,
    maxLength: Int,
    modifier: Modifier = Modifier,
    onTextChange: (value: TextFieldValue) -> Unit,
) {
    InputDefault(
        textLabel = textLabel,
        textInput = textInput,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLength = maxLength,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier,
        onTextChange = {
            onTextChange(MoneyUtils.handleInputMonetaryValue(it.text))
        }
    )
}

@Composable
private fun InputDefault(
    textLabel: String,
    textInput: TextFieldValue,
    leadingIcon: Painter?,
    trailingIcon: Painter?,
    maxLength: Int,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    singleLine: Boolean = true,
    onTextChange: (value: TextFieldValue) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TextField(
            value = textInput,
            onValueChange = {
                if (it.text.length <= maxLength) {
                    onTextChange(it)
                }
            },
            singleLine = singleLine,
            label = {
                TextLabelInput(text = textLabel)
            },
            leadingIcon = leadingIcon?.let {
                {
                    LeadingIcon(leadingIcon = it)
                }
            },
            trailingIcon = trailingIcon?.let {
                {
                    TrailingIcon(
                        trailingIcon = it,
                        textSize = textInput.text.length,
                    ) {
                        onTextChange(TextFieldValue(""))
                    }
                }
            },
            supportingText = textInput.text.length.takeIf { it >= maxLength -2 }?.let {// todo magic number
                {
                    TextSupporting(
                        text = "${textInput.text.length}/$maxLength",
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            },
            keyboardOptions = keyboardOptions.copy(capitalization = KeyboardCapitalization.Words),
            modifier = modifier,
        )
    }
}

@Composable
private fun LeadingIcon(leadingIcon: Painter) {
        IconView(
            icon = leadingIcon,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            iconType = IconType.Input
        )
}

@Composable
private fun TrailingIcon(
    trailingIcon: Painter,
    textSize: Int,
    onClick: () -> Unit,
) {
    if(textSize > 0) {
        IconView(
            icon = trailingIcon,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            iconType = IconType.Default
        ) {
            onClick()
        }
    }
}

@Preview
@Composable
private fun InputViewPreview() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(all = 16.dp)
    ) {
        InputView(
            inputViewState = InputViewState("Digite o valor da despesa", ""),
            inputViewData = InputViewData(
                leadingIcon = rememberVectorPainter(image = Icons.Rounded.AddCircle),
                trailingIcon = painterResource(id = R.drawable.clear),
            )
        )
        InputView(
            inputViewState = InputViewState("Digite o valor da despesa", "99999,99"),
            inputViewData = InputViewData(
                leadingIcon = rememberVectorPainter(image = Icons.Rounded.Edit),
                trailingIcon = null,
            ),
            inputType = InputType.Money,
        )
        InputView(
            inputViewState = InputViewState("Digite o valor da despesa", "lanche"),
            inputViewData = InputViewData(
                leadingIcon = null,
                trailingIcon = null,
            ),
            inputType = InputType.Large,
        )
    }
}