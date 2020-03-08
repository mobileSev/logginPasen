package com.juntadeandalucia.ced.newipasen.operations.login

sealed class LoginViewTransition {

    object ToWelcome: LoginViewTransition()
}