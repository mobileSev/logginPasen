package com.juntadeandalucia.ced.data.operations.login

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("educacion/seneca/seneca/jsp/pasendroid/login")
    fun checkLogin(
        @Field("USUARIO") username: String,
        @Field("CLAVE") password: String,
        @Field("p") version: String
    ): Deferred<Response<ResponseBody>>

}
