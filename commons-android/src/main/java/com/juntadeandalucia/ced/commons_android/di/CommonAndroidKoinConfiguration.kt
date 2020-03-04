package com.juntadeandalucia.ced.commons_android.di

import com.juntadeandalucia.ced.commons_android.NetworkManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


class CommonAndroidKoinConfiguration(private val environment: String) {

    fun getModule() = module{

        single { NetworkManager(androidContext()) }

    }
}