package dev.geanbrandao.minhasdespesas.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.MarginDefault
import dev.geanbrandao.minhasdespesas.ui.theme.MarginHalf
import dev.geanbrandao.minhasdespesas.ui.theme.SizeIconLarge

@Composable
fun InputField(
    inputValue: MutableState<TextFieldValue>,
    keyboardOptions: KeyboardOptions,
    @DrawableRes iconId: Int
) {
    Row {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(size = SizeIconLarge),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.size(size = MarginDefault))
        Column(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
            BasicTextField(
                value = inputValue.value,
                onValueChange = {
                    val formattedText = formatValueAsMoney(it.text)
                    inputValue.value = TextFieldValue(
                        text = formattedText,
                        selection = TextRange(formattedText.length)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MarginHalf),
                keyboardOptions = keyboardOptions,
                textStyle = AppTypography.bodyLarge.copy(
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.secondary,
                ),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(0.5.dp)
                    )
            )

        }
    }

}

// extrair isso para uma extension ou utils
fun formatValueAsMoney(value: String) = "%.2f".format(value.getDigits().toDouble() / 100f)

fun String.getDigits() = this.filter { it.isDigit() }