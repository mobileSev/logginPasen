package com.juntadeandalucia.ced.newipasen.routing

import android.os.Parcelable

data class ActivityDirection(val destination: Class<*>, val entryArg: Parcelable? = null)