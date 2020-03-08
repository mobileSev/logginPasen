package com.juntadeandalucia.ced.data.operations.login

import com.juntadeandalucia.ced.data.remote.NoContentResponse
import com.juntadeandalucia.ced.data.remote.ParsedResponse
import com.juntadeandalucia.ced.data.remote.ResponseParse
import com.juntadeandalucia.ced.data.remote.ResponseParse.Companion.REQUEST_OP_USER_ERROR
import com.juntadeandalucia.ced.domain.operations.login.LoginError
import kotlin.reflect.KClass

class LoginRemoteDataSource(private val service : LoginService, private val parser : ResponseParse) {

    suspend fun checkLogin(request : LoginRequest) : ParsedResponse<LoginError, NoContentResponse>{
        val loginResponse =
            service.checkLogin(request.username, request.password, request.version).await()

            return parser.parseNoContentResponse(loginResponse, getKnownErrorClassesByErrorCodes())

    }

    private fun getKnownErrorClassesByErrorCodes(): Map<String, KClass<out LoginError>> = mapOf(
        REQUEST_OP_USER_ERROR to LoginError.UserIncorrect::class
    )

}
