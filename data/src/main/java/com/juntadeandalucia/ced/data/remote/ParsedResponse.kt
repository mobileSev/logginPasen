package com.juntadeandalucia.ced.data.remote

import com.juntadeandalucia.ced.domain.RepositoryFailure

sealed class ParsedResponse<out KnowError, out Succes> {


    data class Success<out Success>(val success: Success) : ParsedResponse<Nothing, Success>()

    data class KnownError<out KnownError>(val knownError: KnownError) :
        ParsedResponse<KnownError, Nothing>()

    data class Failure(val failure: RepositoryFailure) : ParsedResponse<Nothing, Nothing>()
}
