package com.juntadeandalucia.ced.data.operations.login

import com.juntadeandalucia.ced.domain.operations.login.LoginInput

data class LoginRequest(val username : String, val password : String, val version : String){
}