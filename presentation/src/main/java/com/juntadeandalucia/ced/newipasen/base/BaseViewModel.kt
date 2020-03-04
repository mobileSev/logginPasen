package com.juntadeandalucia.ced.newipasen.base

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juntadeandalucia.ced.commons_android.SingleLiveEvent

abstract class BaseViewModel <STATE: Parcelable, TRANSITION >(): ViewModel(){

    protected val viewState = MutableLiveData<STATE>()
    protected val viewTransition = SingleLiveEvent<TRANSITION>()

    fun getViewState() = viewState as LiveData<STATE>
    fun getViewTransition() = viewTransition as LiveData<TRANSITION>


    fun loadState(state: STATE) {
        viewState.value = state
    }
}