package com.juntadeandalucia.ced.data


data class BaseResponse(
    val ESTADO: State,
    val RESULTADO: String?
)


data class State(
    val CODIGO: String,
    val DESCRIPCION: String?
)

