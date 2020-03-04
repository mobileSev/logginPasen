package com.juntadeandalucia.ced.data

import com.juntadeandalucia.ced.data.remote.ResponseParse
import okhttp3.*


class InterceptorConnection(private val responseParser: ResponseParse) : Interceptor {

    companion object {
        private const val ACCEPT_KEY = "Accept-Charset"
        private const val UTF_8 = "utf-8"
        private const val APPLICATION_JSON = "application/json"

        const val UNAUTHORIZED_ERROR_CODE = 404
        const val INVALID_LOGIN_PARAMETERS = 101
        const val INTERNAL_ERROR = 500
        const val INTERNAL_ERROR_2 = 502
        const val INTERNAL_ERROR_3 = 503
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request = request.newBuilder()
            .addHeader(ACCEPT_KEY, UTF_8)
            .build()
        val response: Response = chain.proceed(newRequest)

        /*val processResponse: BaseResponse?= response.body()?.string()?.let {
            responseParser.jsonParser.fromJson(it, BaseResponse::class.java)
        }

        processResponse?.let { if(it.RESULTADO!=null && it.ESTADO.CODIGO == "C")
            generateNewResponse(it, response)
        }*/

        return response
    }



}