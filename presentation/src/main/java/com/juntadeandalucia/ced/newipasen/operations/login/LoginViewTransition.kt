package com.juntadeandalucia.ced.newipasen.operations.login

sealed class LoginViewTransition {

    data class ToWelcome(val msg: String)
}