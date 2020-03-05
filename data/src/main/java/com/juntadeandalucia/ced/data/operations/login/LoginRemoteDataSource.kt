package com.juntadeandalucia.ced.data.operations.login

import com.juntadeandalucia.ced.data.remote.NoContentResponse
import com.juntadeandalucia.ced.data.remote.ParsedResponse
import com.juntadeandalucia.ced.data.remote.ResponseParse
import com.juntadeandalucia.ced.data.remote.ResponseParse.Companion.REQUEST_OP_USER_BLOQUED
import com.juntadeandalucia.ced.data.remote.ResponseParse.Companion.REQUEST_OP_USER_ERROR
import com.juntadeandalucia.ced.domain.operations.login.CheckLoginError
import com.juntadeandalucia.ced.domain.operations.login.LoginError
import okhttp3.Response
import okhttp3.ResponseBody
import java.lang.Exception
import kotlin.reflect.KClass

class LoginRemoteDataSource(val service : LoginService, val parser : ResponseParse) {

    suspend fun doLogin(request : LoginRequest) : ParsedResponse<LoginError, NoContentResponse>{
        val loginResponse =
            service.doLogin(request.username, request.password, request.version).await()

            return parser.parseNoContentResponse(loginResponse, getKnownErrorClassesByErrorCodes())

    }

    private fun getKnownErrorClassesByErrorCodes(): Map<String, KClass<out LoginError>> = mapOf(
        REQUEST_OP_USER_ERROR to LoginError.UserIncorrect::class
    )

}
