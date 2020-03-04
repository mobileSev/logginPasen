package com.juntadeandalucia.ced.commons

import com.juntadeandalucia.ced.commons.text.StringFormatter
import org.junit.Assert
import org.junit.Test

class StringFormatterTest
{
    private val stringFormatter = StringFormatter()

    @Test

    fun `Replace symbol with - It Replace the specified symbol with the gicen values in order`(){

        val symbol = "%@"
        val text = "Hola %@ como estas, %@ , %@..."
        val value1 = "Juan"
        val value2 = "Todo bien?"
        val value3 = "Como Te encuentras?"

        val actualString = stringFormatter.replaceSymbolWith(text, symbol, listOf(value1, value2, value3))

        Assert.assertEquals(
            "Hola Juan como estas, Todo bien? , Como Te encuentras?...",
            actualString
        )



    }

}