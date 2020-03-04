package com.juntadeandalucia.ced.commons_android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout

class ExpandableLayout(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {

    private var measureHeight = 0

    var isCollapsed: Boolean = true
        private set

    init {
        orientation = LinearLayout.VERTICAL
        post {
            visibility = View.INVISIBLE
            collapse({}, 0)
        }
    }

    fun expand(callback: () -> Unit) {
        if (measureHeight == 0) measureHeight = this.measuredHeight

        isCollapsed = false
        val targetHeight = measureHeight

        layoutParams.height = 0
        visibility = View.VISIBLE

        val animation = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                requestLayout()
                callback()
            }

            override fun willChangeBounds(): Boolean = true

        }

        animation.duration =
            context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        startAnimation(animation)
    }


    fun collapse(callback: () -> Unit, duration: Int = -1) {
        isCollapsed = true

        val initialHeight = measuredHeight

        if (duration == 0) {
            layoutParams.height = 0
            visibility = View.GONE
            return
        }

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    visibility = View.GONE
                } else {
                    layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    requestLayout()
                }
                callback()
            }

            override fun willChangeBounds(): Boolean = true
        }

        animation.duration =
            context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        startAnimation(animation)
    }
}