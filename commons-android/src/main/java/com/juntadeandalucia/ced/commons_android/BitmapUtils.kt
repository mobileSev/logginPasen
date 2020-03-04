package com.juntadeandalucia.ced.commons_android

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class BitmapUtils {

    fun base64ToBitmap(base64: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}
