package com.juntadeandalucia.ced.domain.operations.login

data class LoginInput(
    val username : String,
    val password : String,
    val version : String
)
