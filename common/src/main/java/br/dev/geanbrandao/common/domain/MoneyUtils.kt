package br.dev.geanbrandao.common.domain

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import java.text.DecimalFormat
import java.util.*


private const val MAX_MONETARY_LENGTH = 10
object MoneyUtils {

    fun handleInputMonetaryValue(text: String): TextFieldValue {
        val formattedText = formatValueAsMoney(text)
        return TextFieldValue(
            text = if (text.length <= MAX_MONETARY_LENGTH) formattedText else text.substring(0, MAX_MONETARY_LENGTH),
            selection = TextRange(formattedText.length)
        )
    }

    private fun formatValueAsMoney(value: String) = "%.2f".format(value.getDigits().toDouble() / 100f)

    private fun String.getDigits() = this.filter { it.isDigit() }

    fun Float.formatToBrl(): String {
        val format = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))
        return format.format(this.toDouble())
    }
}