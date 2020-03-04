package com.juntadeandalucia.ced.commons_android.view

import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.juntadeandalucia.ced.commons_android.R

private const val DISABLED_ALPHA = 0.3F
private const val ENABLED_ALPHA = 1F
private const val DURATION = 200L

fun View.setAllEnabled(enabled: Boolean, applyAlpha: Boolean = true) {
    if (applyAlpha) setAlpha(enabled)
    setAllChildrenEnabled(enabled)
}

private fun View.setAlpha(enabled: Boolean) {
    when (enabled) {
        true -> setAlphaAnimation(EnabledAlphaAnimation(), true) { elevation = 0f }
        false -> setAlphaAnimation(DisabledAlphaAnimation(), false) { elevation = resources.getDimensionPixelSize(
            R.dimen.view_elevation).toFloat() }
    }
}

private fun View.setAlphaAnimation(anim: AlphaAnimation, atStart: Boolean, callback: () -> Unit) {
    if (animation == null || animation.javaClass != anim.javaClass) {
        startAnimation(anim.apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                    // do nothing
                }
                override fun onAnimationEnd(p0: Animation?) {
                    if (!atStart) callback()
                }
                override fun onAnimationStart(p0: Animation?) {
                    if (atStart) callback
                }
            })
        })
    }
}

private fun View.setAllChildrenEnabled(enabled: Boolean) {
    isEnabled = enabled
    if (this is RecyclerView) this.isLayoutFrozen = !enabled
    if (this is ViewGroup) children.forEach { it.setAllChildrenEnabled(enabled) }
}


private class EnabledAlphaAnimation : AlphaAnimation(DISABLED_ALPHA, ENABLED_ALPHA) {
    init {
        duration = DURATION
        fillAfter = true
    }
}

private class DisabledAlphaAnimation : AlphaAnimation(ENABLED_ALPHA, DISABLED_ALPHA) {
    init {
        duration = DURATION
        fillAfter = true
    }
}