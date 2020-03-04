package com.juntadeandalucia.ced.domain.operations.login

import com.juntadeandalucia.ced.domain.RepositoryFailure

sealed class LoginError {
    data class Repository(val error : RepositoryFailure) : LoginError()
    data class KnownError (val error : LoginEº) : LoginError()
}
