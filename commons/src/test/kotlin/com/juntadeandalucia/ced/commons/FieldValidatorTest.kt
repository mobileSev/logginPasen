package com.juntadeandalucia.ced.commons

import com.juntadeandalucia.ced.commons.text.FieldValidator
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class FieldValidatorTest {

    private val fieldValidator = FieldValidator()


    companion object {
        private val VALID_EMAILS = arrayOf(
            "email@example.com",
            "firstname.lastname@example.com",
            "email@subdomain.example.com",
            "firstname+lastname@example.com",
            "1234567890@example.com",
            "email@example-one.com",
            "_______@example.com",
            "email@example.museum",
            "email@example.co.jp",
            "firstname-lastname@example.com",
            "email@111.222.333.44444"
        )

        private val INVALID_EMAILS = arrayOf(
            "",
            "\n",
            "\t",
            " ",
            " \n\t ",
            "eá@a.com",
            "emáil@example.web",
            "webmaster@müller.de",
            "testmail@mail.com.",
            "test@@gmail.com",
            "user@.invalid.com",
            "plainaddress",
            "#@%^%#$@#$@#.com",
            "@example.com",
            "Joe Smith <email@example.com>",
            "email.example.com",
            "email@example@example.com",
            ".email@example.com",
            "email.@example.com",
            "あいうえお@example.com",
            "email@example.com (Joe Smith)",
            "email@example",
            "email@-example.com",
            "email@example..com",
            "Abc..123@example.com",
            "\"(),:;<>[\\]@example.com",
            "just\"not\"right@example.com",
            "this\\ is\"really\"not\\allowed@example.com"
        )

    }

    @Test
    fun `AliasLogin - It cannot be empty`() {
        Assert.assertFalse(fieldValidator.validateAliasLogin(""))
    }

    @Test
    fun `AliasLogin - It cannot be blank`() {
        Assert.assertFalse(fieldValidator.validateAliasLogin("\n"))
        Assert.assertFalse(fieldValidator.validateAliasLogin("\t"))
        Assert.assertFalse(fieldValidator.validateAliasLogin(" "))
        Assert.assertFalse(fieldValidator.validateAliasLogin(" \n\t "))
    }

    @Test
    fun `Password Login- It cannot by empty`(){
        Assert.assertFalse(fieldValidator.validatePasswordLogin(""))

    }

    @Test
    fun `PasswordLogin - It cannot be blank`() {
        Assert.assertFalse(fieldValidator.validatePasswordLogin("\n"))
        Assert.assertFalse(fieldValidator.validatePasswordLogin("\t"))
        Assert.assertFalse(fieldValidator.validatePasswordLogin(" "))
        Assert.assertFalse(fieldValidator.validatePasswordLogin(" \n\t "))
    }

    @Test
    fun `Email - Check email format`() {
        for (validEmail in VALID_EMAILS) {
            Assert.assertTrue(
                "'$validEmail' should be a valid email.",
                fieldValidator.validateEmail(validEmail)
            )
        }

        for (invalidEmail in INVALID_EMAILS) {
            Assert.assertFalse(
                "'$invalidEmail' should be an invalid email.",
                fieldValidator.validateEmail(invalidEmail)
            )
        }
    }

    @Test
    fun `String not have special characters`(){
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("a"))
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("aaa"))
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("aa11"))
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("a1"))
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("a00"))
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("_"))
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("aa_"))
        Assert.assertFalse(fieldValidator.hasSpecialCharacters("_aa"))

        Assert.assertTrue(fieldValidator.hasSpecialCharacters("?"))
        Assert.assertTrue(fieldValidator.hasSpecialCharacters(":"))
        Assert.assertTrue(fieldValidator.hasSpecialCharacters("*"))
        Assert.assertTrue(fieldValidator.hasSpecialCharacters("-"))
    }

    @Test
    fun `Is Birthdate Valid - It should check if well formatted, if it's a valid date and if it's between ranges`() {
        val fieldValidatorSpy = Mockito.spy(fieldValidator)

        fieldValidatorSpy.isBirthdateValid("01/01/1990", "24/03/1990")
        Mockito.verify(fieldValidatorSpy).isAValidDate("24/03/1990")
        Mockito.verify(fieldValidatorSpy).isWellFormatted("24/03/1990")
        Mockito.verify(fieldValidatorSpy).isBirthdateBetweenRange("01/01/1990", "24/03/1990")
    }


}