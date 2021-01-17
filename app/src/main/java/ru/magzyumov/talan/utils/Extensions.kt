package ru.magzyumov.talan.utils

import ru.magzyumov.talan.utils.Constants.StringPattern.Companion.TIMESTAMP_PATTERN
import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToString(): String{
    return SimpleDateFormat(TIMESTAMP_PATTERN, Locale.getDefault()).format(this)
}

fun String.formatToDate(): Date {
    var result: Date = Date()

    SimpleDateFormat(TIMESTAMP_PATTERN, Locale.getDefault()).parse(this)?.let {
        result = it
    }

    return result
}