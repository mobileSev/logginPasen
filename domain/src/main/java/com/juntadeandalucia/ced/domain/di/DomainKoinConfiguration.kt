package com.juntadeandalucia.ced.domain.di

import com.juntadeandalucia.ced.domain.operations.login.CheckLogin
import org.koin.dsl.module

class DomainKoinConfiguration {

    fun getModule() = module{
        //User case
        factory { CheckLogin(get()) }
    }
}