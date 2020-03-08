package com.juntadeandalucia.ced.newipasen.operations.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.juntadeandalucia.ced.domain.operations.login.CheckLogin
import com.juntadeandalucia.ced.domain.operations.login.CheckLoginError
import com.juntadeandalucia.ced.domain.operations.login.LoginInput
import com.juntadeandalucia.ced.newipasen.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(val checkLogin: CheckLogin) : BaseViewModel<LoginViewState, LoginViewTransition>() {

    private val _state : MutableLiveData<LoginViewState> =  MutableLiveData()
    val state : LiveData<LoginViewState> get() { return _state }

    private val _transition : MutableLiveData<LoginViewTransition> =  MutableLiveData()
    val transition : LiveData<LoginViewTransition> get() { return _transition }


    fun checkLogin(username: String, password: String, version: String) {

        viewModelScope.launch {
            _state.value = LoginViewState.Loading

            checkLogin(LoginInput(username, password, version)){
                it.fold(::handleError,::handleSuccess)
            }
        }
    }

    private fun handleError(loginError: CheckLoginError) {
        Log.e("Formacion", "Error en login")

        val msg = when (loginError) {
            is CheckLoginError.KnownError -> "Error conocido"
            else -> "Error"
        }

        _state.value = LoginViewState.Error(msg)
    }

    private fun handleSuccess(arg: Any) {
        _state.value = LoginViewState.Success
        _transition.value = LoginViewTransition.ToWelcome
    }

}