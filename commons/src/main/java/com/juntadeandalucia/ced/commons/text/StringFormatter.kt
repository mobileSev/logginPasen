package com.juntadeandalucia.ced.commons.text

class StringFormatter {

    fun replaceSymbolWith(text: String, symbol: String, values: List<String>): String {
        var formattedText = text
        values.forEach { value ->
            formattedText = formattedText.replaceFirst(symbol, value)
        }
        return formattedText
    }

}
