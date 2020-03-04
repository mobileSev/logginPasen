package com.juntadeandalucia.ced.commons_android.view

import android.content.Context

class DimensionsManager(private val context: Context) {

    fun toDensityPixel(pixel: Float): Int {
        val density = context.resources.displayMetrics.density
        return (pixel * density).toInt()
    }
}