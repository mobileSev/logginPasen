package com.juntadeandalucia.ced.commons

import com.juntadeandalucia.ced.commons.text.DocumentValidator
import org.junit.Assert
import org.junit.Test

class DocumentValidatorTest {

    private val digitValidator = DocumentValidator()


    @Test
    fun `Spain DNI Validation - It should fit Spain format`() {
        Assert.assertTrue(digitValidator.validateDni("06675966D"))

    }
}