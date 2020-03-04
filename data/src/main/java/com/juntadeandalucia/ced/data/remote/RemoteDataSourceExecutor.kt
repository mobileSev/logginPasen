package com.juntadeandalucia.ced.data.remote

import com.juntadeandalucia.ced.commons.data.types.Try
import com.juntadeandalucia.ced.commons_android.NetworkManager
import com.juntadeandalucia.ced.domain.RepositoryFailure

class RemoteDataSourceExecutor(private val networkManager: NetworkManager) {

    suspend operator fun <KnownError, Success> invoke(block: suspend () -> ParsedResponse<KnownError, Success>): ParsedResponse<KnownError, Success> {

        if (!networkManager.isThereConnectionToInternet()) {
            return ParsedResponse.Failure(RepositoryFailure.NoInternet)
        }

        when (val result = Try.invokeSuspend { block() }) {
            is Try.Exception -> return ParsedResponse.Failure(RepositoryFailure.Unknown)
            is Try.Success -> return result.s
        }
    }

}