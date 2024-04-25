package dev.geanbrandao.minhasdespesas.common.utils.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun Long.getFormattedDateFromMilli(): String {
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
    return date.toStringDateFormatted()
}

fun LocalDateTime.toStringDateFormatted(): String {
    val builder = StringBuilder()
    builder.append(this.getDayName().capitalize(Locale.getDefault()))
    builder.append(", ")
    builder.append(this.getDayNumber())
    builder.append(" de ")
    builder.append(this.getMonthName().capitalize(Locale.getDefault()))
    return builder.toString()
}

fun LocalDateTime.getMonth3LettersName(): String =
    DateTimeFormatter.ofPattern("MMM", Locale.getDefault()).format(this)

fun LocalDateTime.getDayNumber(): String =
    DateTimeFormatter.ofPattern("dd", Locale.getDefault()).format(this)

fun LocalDateTime.getYearNumber(): String =
    DateTimeFormatter.ofPattern("yyyy", Locale.getDefault()).format(this)

fun LocalDateTime.getMonthName(): String =
    DateTimeFormatter.ofPattern("MMMM", Locale.getDefault()).format(this)

fun LocalDateTime.getDayName(): String =
    DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()).format(this)

fun LocalDateTime.getDayName3LettersName(): String =
    DateTimeFormatter.ofPattern("EEE", Locale.getDefault()).format(this)