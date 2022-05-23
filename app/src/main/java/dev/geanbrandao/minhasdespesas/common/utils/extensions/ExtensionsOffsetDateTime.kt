package dev.geanbrandao.minhasdespesas.common.utils.extensions

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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