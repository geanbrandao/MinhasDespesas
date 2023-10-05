package br.dev.geanbrandao.common.domain

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

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

fun Long.getOffsetDateTime(): OffsetDateTime {
    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
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
    val offsetDateTime = Date(this)
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
