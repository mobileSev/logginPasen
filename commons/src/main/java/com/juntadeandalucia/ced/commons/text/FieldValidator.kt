package com.juntadeandalucia.ced.commons.text

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class FieldValidator {

    fun validateAliasLogin(alias: String) = alias.isNotBlank() && !alias.contains("*")
    fun validatePasswordLogin(password: String) = password.isNotBlank()

    fun validateEmail(email: String): Boolean {
        val expression =
            "^([\\w-_+%]+)(\\.[\\w-_+%]+)*@(\\w[\\w-_+%]*)(\\.[\\w-_+%]+)*(\\.[\\w-_+%]{1,5}[\\w-_+%]+)\$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        return pattern.matcher(email).matches()
    }

    fun validateAlias(alias: String) =
        alias.isNotBlank() && alias.length >= 6 && !alias.contains("*")

    fun hasSpecialCharacters(string: String): Boolean {
        val expression = "^([A-Za-z0-9_@\\.])*$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        return !pattern.matcher(string).matches()
    }

    fun isBirthdateValid(minDate: String, birthDate: String) =
        isAValidDate(birthDate) && isWellFormatted(birthDate) && isBirthdateBetweenRange(
            minDate,
            birthDate
        )

    fun isWellFormatted(birthDate: String) =
        birthDate.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}".toRegex())


    fun isAValidDate(birthDate: String): Boolean {
        val split = birthDate.split("/")

        return if (split.size != 3) false
        else {
            val day = split[0]
            val month = split[1]
            val year = split[2]

            if (month.toIntOrNull() != null && year.toIntOrNull() != null) {
                val monthValue = month.toInt()
                val yearValue = year.toInt()

                day.isAValidDay(monthValue, yearValue)
            } else {
                false
            }
        }
    }

    fun isBirthdateBetweenRange(minDate: String, birthDate: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "PT"))
        return try {
            val date = sdf.parse(birthDate)
            val minDate = sdf.parse(minDate)

            val maxDateCalendar = Calendar.getInstance()
            maxDateCalendar.add(Calendar.YEAR, -16)
            maxDateCalendar.add(Calendar.DATE, -1)
            maxDateCalendar.clear(Calendar.MILLISECOND)

            val minDateCalendar = Calendar.getInstance()
            minDateCalendar.time = minDate
            minDateCalendar.add(Calendar.DATE, 1)
            minDateCalendar.clear(Calendar.MILLISECOND)

            return date.time < maxDateCalendar.timeInMillis && date.time > minDateCalendar.timeInMillis

        } catch (e: Exception) {
            false
        }
    }
}
