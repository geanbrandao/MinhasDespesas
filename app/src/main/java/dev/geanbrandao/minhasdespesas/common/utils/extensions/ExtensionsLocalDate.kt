package dev.geanbrandao.minhasdespesas.common.utils.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.toStringDateFormatted(): String {
    val builder = StringBuilder()
    builder.append(this.getDayName().capitalize(Locale.getDefault()))
    builder.append(", ")
    builder.append(this.getDayNumber())
    builder.append(" de ")
    builder.append(this.getMonthName().capitalize(Locale.getDefault()))
    return builder.toString()
}

fun LocalDate.getMonth3LettersName(): String =
    DateTimeFormatter.ofPattern("MMM", Locale.getDefault()).format(this)

fun LocalDate.getDayNumber(): String =
    DateTimeFormatter.ofPattern("dd", Locale.getDefault()).format(this)

fun LocalDate.getYearNumber(): String =
    DateTimeFormatter.ofPattern("yyyy", Locale.getDefault()).format(this)

fun LocalDate.getMonthName(): String =
    DateTimeFormatter.ofPattern("MMMM", Locale.getDefault()).format(this)

fun LocalDate.getDayName(): String =
    DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()).format(this)

fun LocalDate.getDayName3LettersName(): String =
    DateTimeFormatter.ofPattern("EEE", Locale.getDefault()).format(this)