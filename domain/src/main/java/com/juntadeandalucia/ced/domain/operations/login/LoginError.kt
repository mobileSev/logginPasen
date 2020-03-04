package com.juntadeandalucia.ced.domain.operations.login

sealed class LoginError {
    object InvalidUser : LoginError()
    data class UnknownError (val message : String) : LoginError()
}
