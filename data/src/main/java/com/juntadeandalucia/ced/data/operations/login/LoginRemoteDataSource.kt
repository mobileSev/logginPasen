package com.juntadeandalucia.ced.data.operations.login

import com.juntadeandalucia.ced.data.remote.NoContentResponse
import com.juntadeandalucia.ced.data.remote.ParsedResponse
import com.juntadeandalucia.ced.data.remote.ResponseParse
import com.juntadeandalucia.ced.domain.operations.login.CheckLoginError
import com.juntadeandalucia.ced.domain.operations.login.LoginError

class LoginRemoteDataSource(val service : LoginService, val parser : ResponseParse) {

    suspend fun doLogin(request : LoginRequest) : ParsedResponse<LoginError, NoContentResponse>{
        val loginResponse =
            service.doLogin(request.username, request.password, request.version).await()

        return parser.parse(loginResponse)
    }

}
