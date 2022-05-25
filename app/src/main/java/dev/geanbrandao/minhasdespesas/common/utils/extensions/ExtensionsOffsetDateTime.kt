package dev.geanbrandao.minhasdespesas.common.utils.extensions

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
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

fun OffsetDateTime.startOfDay(): OffsetDateTime {
    return OffsetDateTime.of(
        this.year,
        this.monthValue,
        this.dayOfMonth,
        0,
        0,
        0,
        0,
        ZoneOffset.UTC
    )
}

fun OffsetDateTime.endOfDay(): OffsetDateTime {
    return OffsetDateTime.of(
        this.year,
        this.monthValue,
        this.dayOfMonth,
        23,
        59,
        59,
        0,
        ZoneOffset.UTC
    )
}

fun OffsetDateTime.startOfMonth(): OffsetDateTime {
    return this.with(month).with(TemporalAdjusters.firstDayOfMonth())
}

fun OffsetDateTime.endOfMonth(): OffsetDateTime {
    return this.with(month).with(TemporalAdjusters.lastDayOfMonth())
}
