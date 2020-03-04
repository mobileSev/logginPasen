package com.juntadeandalucia.ced.commons_android.security

import android.os.Handler

class SessionManager {

    companion object {

        private const val TIMER: Long = 60000 * 5
    }

    private val sessionHandler = Handler()

    private lateinit var sessionCounter: Runnable
    private var isSessionRunning: Boolean = false

    private var lastCallback: (() -> Unit)? = null


    fun startSession() {
        if (!isSessionRunning) {
            isSessionRunning = true
        }
    }

    fun stopSession() {
        isSessionRunning = false

        if (::sessionCounter.isInitialized) sessionHandler.removeCallbacks(sessionCounter)
    }


    fun refreshSession(onSessionFinished: (() -> Unit)? = null) {
        if (isSessionRunning) {
            if (::sessionCounter.isInitialized) sessionHandler.removeCallbacks(sessionCounter)

            if (onSessionFinished != null) {
                lastCallback = onSessionFinished
            }

            sessionCounter = Runnable {
                if (isSessionRunning) { //It handle internal thread running
                    isSessionRunning = false
                    lastCallback?.invoke()
                }
            }

            sessionHandler.postDelayed(sessionCounter, TIMER)
        }
    }

}