package br.dev.geanbrandao.common.domain

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val PATTERN_MONTH_THREE = "MMM"
const val PATTERN_DAY = "dd"
const val PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

fun Any?.isNotNull() = this != null
fun Any?.isNull() = this == null

inline fun Modifier.clickableNoRippleEffect(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

inline fun Modifier.conditionalClickable(
    exist: Boolean,
    crossinline onClick: () -> Unit
): Modifier = composed {
    if (exist) {
        clickableNoRippleEffect {
            onClick()
        }
    } else {
        Modifier
    }
}


fun Context.getIconIdFromString(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}

fun getCurrentTimeInMillis(): Long {
    val calendar = Calendar.getInstance()
    return calendar.timeInMillis
}

//fun Long.getOffsetDateTime(): OffsetDateTime {
//    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
//}

fun Long.getOffsetDateTime(): OffsetDateTime {
    val stringDate = this.toBrazilianDateFormat()
    return stringDate.toOffsetDateTime()
}

fun OffsetDateTime.getLongTimeMillis(): Long {
    return this.toInstant().toEpochMilli()
}


fun Long.toStringDateFormatted(): String {
    val offsetDateTime = this.getOffsetDateTime()
    val builder = StringBuilder()
    builder.append(
        offsetDateTime.getDayName()
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    )
    builder.append(", ")
    builder.append(offsetDateTime.getDayNumber())
    builder.append(" de ")
    builder.append(
        offsetDateTime.getMonthName()
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    )
    return builder.toString()
}

fun Long.getMonth3LettersName(): String {
    val offsetDateTime = Date(this)
    val simpleDateFormat = SimpleDateFormat("MMM", Locale.getDefault())
    return simpleDateFormat.format(offsetDateTime)
}

fun Long.getDayNumber(): String {
    val offsetDateTime = this.getOffsetDateTime()
    val simpleDateFormat = SimpleDateFormat("dd", Locale.getDefault())
    return simpleDateFormat.format(offsetDateTime)
}

fun Long.getYearNumber(): String {
    val offsetDateTime = Date(this)
    val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return simpleDateFormat.format(offsetDateTime)
}

fun Long.getMonthName(): String {
    val offsetDateTime = Date(this)
    val simpleDateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    return simpleDateFormat.format(offsetDateTime)
}

fun Long.getDayName(): String {
    val offsetDateTime = Date(this)
    val simpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    return simpleDateFormat.format(offsetDateTime)
}

fun Long.getDayName3LettersName(): String {
    val offsetDateTime = Date(this)
    val simpleDateFormat = SimpleDateFormat("EEE", Locale.getDefault())
    return simpleDateFormat.format(offsetDateTime)
}

fun OffsetDateTime.toStringDateFormatted(): String {
    val builder = StringBuilder()
    builder.append(this.getDayName().capitalize(Locale.getDefault()))
    builder.append(", ")
    builder.append(this.getDayNumber())
    builder.append(" de ")
    builder.append(this.getMonthName().capitalize(Locale.getDefault()))
    return builder.toString()
}

fun OffsetDateTime.getMonth3LettersName(): String =
    DateTimeFormatter.ofPattern("MMM", Locale.getDefault()).format(this)

fun OffsetDateTime.getDayNumber(): String =
    DateTimeFormatter.ofPattern("dd", Locale.getDefault()).format(this)

fun OffsetDateTime.getYearNumber(): String =
    DateTimeFormatter.ofPattern("yyyy", Locale.getDefault()).format(this)

fun OffsetDateTime.getMonthName(): String =
    DateTimeFormatter.ofPattern("MMMM", Locale.getDefault()).format(this)

fun OffsetDateTime.getDayName(): String =
    DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()).format(this)

fun OffsetDateTime.getDayName3LettersName(): String =
    DateTimeFormatter.ofPattern("EEE", Locale.getDefault()).format(this)

fun Long.isValidDate(): Boolean {
    return try {
        val date = Date(this)
        date.time == this
    } catch (e: Exception) {
        false
    }
}

fun String.toFloat(): Float {
    val cleanedString = this.replace(",", ".")
    return cleanedString.toFloatOrNull() ?: 0.0f
}

fun Long.toBrazilianDateFormat(
    pattern: String = PATTERN_ISO
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale("pt", "BR"))
    formatter.timeZone = TimeZone.getTimeZone("GMT")
    return formatter.format(date)
}

fun String.toOffsetDateTime(): OffsetDateTime {
    // Parse the formatted string into a Date
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale("pt", "BR"))
    val date = formatter.parse(this)

    // Convert the Date to OffsetDateTime
    val instant = date!!.toInstant()
    val offset = ZoneOffset.UTC // You can specify the desired time zone offset here
    return OffsetDateTime.ofInstant(instant, offset)
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}