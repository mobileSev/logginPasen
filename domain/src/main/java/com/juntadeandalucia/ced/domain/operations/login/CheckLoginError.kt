package com.juntadeandalucia.ced.domain.operations.login

import com.juntadeandalucia.ced.domain.RepositoryFailure

sealed class CheckLoginError {
    data class Repository(val error : RepositoryFailure) : CheckLoginError()
    data class KnownError (val error : LoginError) : CheckLoginError()
}
