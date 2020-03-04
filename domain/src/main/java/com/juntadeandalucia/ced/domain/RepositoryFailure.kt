package com.juntadeandalucia.ced.domain

sealed class RepositoryFailure {

    object NoInternet : RepositoryFailure()

    object Unauthorized : RepositoryFailure()

    object ServerError : RepositoryFailure()

    object Unknown : RepositoryFailure()

}