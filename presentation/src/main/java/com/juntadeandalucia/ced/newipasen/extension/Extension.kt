package com.juntadeandalucia.ced.newipasen.extension

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun String.toast(context: Context?) {
    context?.let {
        Toast.makeText(it, this, Toast.LENGTH_SHORT).show()
    }
}