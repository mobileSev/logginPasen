package com.juntadeandalucia.ced.newipasen.base

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.juntadeandalucia.ced.newipasen.R
import com.juntadeandalucia.ced.newipasen.routing.ActivityRouting

abstract class BaseActivity<STATE : Parcelable, TRANSITION> : AppCompatActivity() {

    companion object {
        const val ENTRY_ARG = "entryArg"
    }

    protected abstract val viewModel: BaseViewModel<STATE, TRANSITION>?

    protected val routing by lazy { ActivityRouting(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())


        savedInstanceState?.getParcelable<STATE>("state")?.apply { viewModel?.loadState(this) }

    }


    abstract fun getLayout(): Int

}