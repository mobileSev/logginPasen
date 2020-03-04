package com.juntadeandalucia.ced.newipasen

import android.app.Application
import com.juntadeandalucia.ced.commons.di.CommonKoinConfiguration
import com.juntadeandalucia.ced.commons_android.di.CommonAndroidKoinConfiguration
import com.juntadeandalucia.ced.data.di.DataKoinConfiguration
import com.juntadeandalucia.ced.domain.di.DomainKoinConfiguration
import com.juntadeandalucia.ced.newipasen.di.PresentationKoinConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IpasenApp : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IpasenApp)
            modules(
                listOf(
                    CommonKoinConfiguration().getModule(),
                    CommonAndroidKoinConfiguration(BuildConfig.FLAVOR).getModule(),
                    DataKoinConfiguration(BuildConfig.BASE_URL).getModule(),
                    DomainKoinConfiguration().getModule(),
                    PresentationKoinConfiguration().getModule()
                )
            )
        }
    }
}