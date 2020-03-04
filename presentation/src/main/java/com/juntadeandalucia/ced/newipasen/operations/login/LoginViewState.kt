package com.juntadeandalucia.ced.newipasen.operations.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class LoginViewState : Parcelable {
    @Parcelize
    data class Error(val error : String) : LoginViewState()

    @Parcelize
    object Login : LoginViewState()

    @Parcelize
    object Loading : LoginViewState()
}