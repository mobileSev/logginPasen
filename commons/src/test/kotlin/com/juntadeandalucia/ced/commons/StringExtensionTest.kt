package com.juntadeandalucia.ced.commons

import com.juntadeandalucia.ced.commons.text.getBeginIndexesOf
import com.juntadeandalucia.ced.commons.text.isGreaterVersionThan
import org.junit.Assert
import org.junit.Test

class StringExtensionTest {

    @Test
    fun `Get begin indexes of a text in another text`() {
        Assert.assertEquals(
            emptyList<Int>(),
            "This is a text with several texts (<-- plural): text text, text.".getBeginIndexesOf("")
        )
        Assert.assertEquals(
            emptyList<Int>(),
            "This is a text with several texts (<-- plural): text text, text.".getBeginIndexesOf("XXX")
        )
        Assert.assertEquals(
            listOf(10, 28, 48, 53, 59),
            "This is a text with several texts (<-- plural): text text, text.".getBeginIndexesOf("text")
        )

        Assert.assertEquals(
            emptyList<Int>(),
            "Text. It is case sensitive.".getBeginIndexesOf("text")
        )
    }


    @Test
    fun `Check Version Greater Than`() {
        Assert.assertTrue("1.21".isGreaterVersionThan("1.20"))
        Assert.assertTrue("1.50".isGreaterVersionThan("1.20"))
        Assert.assertTrue("2.10".isGreaterVersionThan("1.20"))

        Assert.assertFalse("1.20".isGreaterVersionThan("1.20"))
        Assert.assertFalse("1.10".isGreaterVersionThan("1.20"))
        Assert.assertFalse("2.35.13".isGreaterVersionThan("2.56.1"))
        Assert.assertFalse("2.13".isGreaterVersionThan("2.40.1.32"))

    }
}