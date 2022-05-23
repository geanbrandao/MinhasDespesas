package dev.geanbrandao.minhasdespesas.common.utils

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

object InputHandle {
    private const val MAX_MONETARY_LENGTH = 10
    private const val MAX_SINGLE_LINE_LENGTH = 60
    private const val MAX_MULTILINE_LENGTH = 120

    fun handleInputMultiline(text: String): TextFieldValue {
        return TextFieldValue(
            text = if (text.length <= MAX_MULTILINE_LENGTH) text else text.substring(0, MAX_MULTILINE_LENGTH),
            selection = TextRange(text.length),
        )
    }

    fun handleInputMonetaryValue(text: String): TextFieldValue {
        val formattedText = formatValueAsMoney(text)
        return TextFieldValue(
            text = if (text.length <= MAX_MONETARY_LENGTH) formattedText else text.substring(0, MAX_MONETARY_LENGTH),
            selection = TextRange(formattedText.length)
        )
    }

    fun handleInputTextValue(text: String): TextFieldValue {
        return TextFieldValue(
            text = if (text.length <= MAX_SINGLE_LINE_LENGTH) text else text.substring(0, MAX_SINGLE_LINE_LENGTH),
            selection = TextRange(0)
        )
    }

    fun formatValueAsMoney(value: String) = "%.2f".format(value.getDigits().toDouble() / 100f)

    private fun String.getDigits() = this.filter { it.isDigit() }
}