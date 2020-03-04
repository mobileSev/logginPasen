package com.juntadeandalucia.ced.commons.text

fun Int.styleDate(): String {
    return if (this in 0..9) "0" + toString() else toString()
}

fun String.isAValidMonth() = toIntOrNull() in 1..12
fun String.isAValidDay(month: Int, year: Int) = when (month) {
    1, 3, 5, 7, 8, 10, 12 -> toIntOrNull() in 1..31
    4, 6, 9, 11 -> toIntOrNull() in 1..30
    2 -> this.validateLeapYear(year)
    else -> false
}

fun String.validateLeapYear(year: Int): Boolean {
    return if (year % 4 == 0 && toIntOrNull() in 1..29) true
    else year % 4 != 0 && toIntOrNull() in 1..28
}
