package com.juntadeandalucia.ced.commons

import com.juntadeandalucia.ced.commons.text.isAValidDay
import com.juntadeandalucia.ced.commons.text.isAValidMonth
import com.juntadeandalucia.ced.commons.text.styleDate
import com.juntadeandalucia.ced.commons.text.validateLeapYear
import org.junit.Assert
import org.junit.Test

class DateExtensionTest {

    @Test
    fun `Style date`() {
        Assert.assertEquals("-1", (-1).styleDate())
        Assert.assertEquals("00", 0.styleDate())
        Assert.assertEquals("09", 9.styleDate())
        Assert.assertEquals("11", 11.styleDate())
        Assert.assertEquals("100", 100.styleDate())
    }


    @Test
    fun `A valid month`() {
        Assert.assertTrue("1".isAValidMonth())
        Assert.assertTrue("05".isAValidMonth())
        Assert.assertTrue("12".isAValidMonth())
        Assert.assertTrue("09".isAValidMonth())

        Assert.assertFalse("0".isAValidMonth())
        Assert.assertFalse("13".isAValidMonth())
        Assert.assertFalse("-1".isAValidMonth())
        Assert.assertFalse("55".isAValidMonth())
    }


    @Test
    fun `Validate Leap Year`() {
        Assert.assertTrue("29".validateLeapYear(2004))
        Assert.assertTrue("28".validateLeapYear(2005))
        Assert.assertTrue("1".validateLeapYear(2016))
        Assert.assertTrue("25".validateLeapYear(2016))

        Assert.assertFalse("0".validateLeapYear(2004))
        Assert.assertFalse("30".validateLeapYear(2004))
        Assert.assertFalse("-28".validateLeapYear(2004))
    }

    @Test
    fun `Is a valid day`() {
        Assert.assertTrue("14".isAValidDay(1, 1990))
        Assert.assertTrue("1".isAValidDay(1, 1990))
        Assert.assertTrue("31".isAValidDay(5, 2004))
        Assert.assertTrue("31".isAValidDay(7, 2003))
        Assert.assertTrue("31".isAValidDay(8, 1980))
        Assert.assertTrue("1".isAValidDay(8, 1990))
        Assert.assertTrue("31".isAValidDay(10, 1980))
        Assert.assertTrue("31".isAValidDay(12, 1980))
        Assert.assertTrue("30".isAValidDay(4, 1980))
        Assert.assertTrue("30".isAValidDay(6, 1980))
        Assert.assertTrue("30".isAValidDay(9, 1980))
        Assert.assertTrue("30".isAValidDay(11, 1980))
        Assert.assertTrue("28".isAValidDay(2, 2003))
        Assert.assertTrue("29".isAValidDay(2, 2004))

        Assert.assertFalse("0".isAValidDay(1, 1990))
        Assert.assertFalse("32".isAValidDay(1, 1990))
    }

}