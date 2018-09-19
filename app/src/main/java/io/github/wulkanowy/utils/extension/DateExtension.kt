package io.github.wulkanowy.utils.extension

import io.github.wulkanowy.utils.DATE_PATTERN
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

private val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)

fun LocalDate.toDate(): Date = java.sql.Date.valueOf(this.format(formatter))

fun LocalDate.toFormattedString(): String = this.format(formatter)

fun Date.toLocalDate(): LocalDate = Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDate()

fun Date.getWeekDayName(): String = this.toLocalDate().format(DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()))

fun Date.toFormattedString(): String = this.toLocalDate().toFormattedString()
