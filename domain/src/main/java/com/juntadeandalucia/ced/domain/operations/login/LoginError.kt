package com.juntadeandalucia.ced.domain.operations.login


sealed class LoginError {
    object UserIncorrect : LoginError()
}
