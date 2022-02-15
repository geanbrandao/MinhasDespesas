package dev.geanbrandao.minhasdespesas.common.components.inputs

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.dividers.DividerInput
import dev.geanbrandao.minhasdespesas.common.components.icons.IconInput
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.components.texts.TextLabelInput
import dev.geanbrandao.minhasdespesas.common.utils.InputHandle.handleInputMonetaryValue
import dev.geanbrandao.minhasdespesas.common.utils.InputHandle.handleInputMultiline
import dev.geanbrandao.minhasdespesas.common.utils.InputHandle.handleInputTextValue
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.MarginOne
import dev.geanbrandao.minhasdespesas.ui.theme.SizeIconLarge

val InputMultilineHeight = 64.dp

@Composable
fun InputMoney(
    inputValue: MutableState<TextFieldValue>,
    @StringRes stringId: Int,
    focusRequester: FocusRequester = FocusRequester()
) {
    InputBasic(
        inputValue = inputValue,
        stringId = stringId,
        iconId = R.drawable.ic_money,
        focusRequester = focusRequester,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { handleInputMonetaryValue(it) }
    )
}

@Composable
fun InputTextSingleLine(
    inputValue: MutableState<TextFieldValue>,
    @StringRes stringId: Int,
    focusRequester: FocusRequester = FocusRequester()
) {
    InputBasic(
        inputValue = inputValue,
        stringId = stringId,
        iconId = R.drawable.ic_text,
        focusRequester = focusRequester,
        onValueChange = { handleInputTextValue(it) }
    )
}

@Composable
fun InputMultiLine(
    inputValue: MutableState<TextFieldValue>,
    @StringRes stringId: Int,
    modifier: Modifier = Modifier
) {
    InputBasic(
        inputValue = inputValue,
        stringId = stringId,
        singleLine = false,
        onValueChange = {
            handleInputMultiline(it)
        },
        modifier = modifier,
    )
}

@Composable
fun InputBasic(
    inputValue: MutableState<TextFieldValue>,
    @StringRes stringId: Int,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
    onValueChange: (text: String) -> TextFieldValue = { handleInputTextValue(it) },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    focusRequester: FocusRequester = FocusRequester(),
    singleLine: Boolean = true
) {
    Column {
        TextLabelInput(stringId = stringId)
        SpacerTwo()
        Row {
            Column(modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .weight(1f)
            ) {
                BasicTextField(
                    value = inputValue.value,
                    onValueChange = {
                        inputValue.value = onValueChange(it.text)
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = MarginOne)
                        .focusRequester(focusRequester),
                    keyboardOptions = keyboardOptions,
                    textStyle = AppTypography.bodyLarge.copy(
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.secondary,
                    ),
                    singleLine = singleLine,
                )
                DividerInput()
            }
            iconId?.let {
                SpacerTwo()
                IconInput(iconId = it, contentDescription = null)
            }
        }
    }
}