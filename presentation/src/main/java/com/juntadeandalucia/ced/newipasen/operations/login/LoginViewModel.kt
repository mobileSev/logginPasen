package com.juntadeandalucia.ced.newipasen.operations.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.juntadeandalucia.ced.domain.operations.login.CheckLogin
import com.juntadeandalucia.ced.domain.operations.login.LoginInput
import com.juntadeandalucia.ced.newipasen.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(val checkLogin: CheckLogin) : BaseViewModel<LoginViewState, LoginViewTransition>() {


    private val _state : MutableLiveData<LoginViewState> =  MutableLiveData()

    val state : LiveData<LoginViewState> get() {
        return _state
    }

    fun doLogin(username: String, password: String, version: String) {

        viewModelScope.launch {
            _state.value = LoginViewState.Loading

            checkLogin.invoke(LoginInput(username, password, version))
        }

        checkLogin.launch {

        }

    }


}