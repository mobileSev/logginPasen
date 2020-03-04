package com.juntadeandalucia.ced.newipasen.routing

import android.content.Intent
import android.os.Parcelable
import com.juntadeandalucia.ced.newipasen.base.BaseActivity

class ActivityRouting<STATE : Parcelable, TRANSITION>(private val activity: BaseActivity<STATE, TRANSITION>) {

    fun navigate(direction: ActivityDirection) {
        activity.startActivity(Intent(activity, direction.destination).also { i ->
            direction.entryArg?.apply { i.putExtra(BaseActivity.ENTRY_ARG, this) }
        })
    }

}