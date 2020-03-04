package com.juntadeandalucia.ced.commons_android

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.juntadeandalucia.ced.commons.text.StringFormatter

fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(applicationWindowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

fun FragmentActivity.hideKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun View.removeKeyboardOnTouch(fragment: FragmentActivity?, callback: (() -> Unit)? = null) {
    setOnTouchListener { _, _ ->
        fragment?.hideKeyboard()
        callback?.invoke()
        false
    }
}

fun Fragment.createLoading(): AlertDialog? {
    return this.activity?.let {
        val builder = AlertDialog.Builder(it)
        builder.setView(R.layout.progress_lay)
        builder.setCancelable(false)
        builder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}

fun Fragment.formatString(stringResId: Int, vararg values: String): String {
    return StringFormatter().replaceSymbolWith(getString(stringResId), "%@", values.asList())
}

fun Application.formatString(stringResId: Int, vararg values: String): String {
    return StringFormatter().replaceSymbolWith(getString(stringResId), "%@", values.asList())
}

fun Context.formatString(stringResId: Int, vararg values: String): String {
    return StringFormatter().replaceSymbolWith(getString(stringResId), "%@", values.asList())
}

fun ProgressBar.animProgress(from: Int = 0, to: Int) {
    if (to >= 0) {
        val animator = ObjectAnimator.ofInt(this, "progress", from, to)
        animator.duration = 2000 * (to - from).toLong() / 100
        animator.interpolator = DecelerateInterpolator()
        animator.start()
        invalidate()
    }
}

fun Fragment.navigateTo(@IdRes navActionResId: Int, bundle: Bundle, popUpTo: Int? = null, inclusive: Boolean = false) {
    try {
        if (popUpTo != null) {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.anim_enter_from_left)
                .setExitAnim(R.anim.anim_exit_to_left)
                .setPopEnterAnim(R.anim.anim_pop_from_right)
                .setPopExitAnim(R.anim.anim_pop_to_right)
                .setPopUpTo(popUpTo, inclusive)
                .build()

            findNavController().navigate(navActionResId, bundle, navOptions)
        } else {
            findNavController().navigate(navActionResId, bundle)
        }
    } catch (ignored: Exception) {
        // Navigation library has a bug which crash if same navActionResId is passed too quickly so we implemented this try / catch
    }
}

fun Context.openBrowser(uri: Uri) {
    val browserIntent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(browserIntent)
}