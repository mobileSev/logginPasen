package com.juntadeandalucia.ced.data.operations.login

import com.juntadeandalucia.ced.commons.data.types.Either
import com.juntadeandalucia.ced.data.remote.NoContentResponse
import com.juntadeandalucia.ced.data.remote.ParsedResponse
import com.juntadeandalucia.ced.data.remote.RemoteDataSourceExecutor
import com.juntadeandalucia.ced.domain.operations.login.CheckLoginError
import com.juntadeandalucia.ced.domain.operations.login.LoginError
import com.juntadeandalucia.ced.domain.operations.login.LoginInput
import com.juntadeandalucia.ced.domain.operations.login.LoginRepository

class LoginRepositoryImpl(private val dataSource: LoginRemoteDataSource, val executor: RemoteDataSourceExecutor) :
    LoginRepository {
    override suspend fun checkLogin(input : LoginInput) : Either<CheckLoginError,Unit> {
        val request = LoginRequest(
                        input.username,
                        input.password,
                        input.version)

        val responseForParsing : ParsedResponse<LoginError,NoContentResponse> = executor { dataSource.checkLogin(request) }

        return when (responseForParsing){
            is ParsedResponse.Success -> Either.Right(Unit)
            is ParsedResponse.KnownError -> Either.Left(CheckLoginError.KnownError(responseForParsing.knownError))
            is ParsedResponse.Failure -> Either.Left(CheckLoginError.Repository(responseForParsing.failure))
        }
    }

}
