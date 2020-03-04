package com.juntadeandalucia.ced.commons.di

import com.juntadeandalucia.ced.commons.json.JsonParser
import com.juntadeandalucia.ced.commons.json.MoshiJsonParser
import com.juntadeandalucia.ced.commons.text.FieldValidator
import com.juntadeandalucia.ced.commons.text.StringFormatter
import org.koin.dsl.module

class CommonKoinConfiguration {

    fun getModule() = module {

        single { FieldValidator() }
        single { StringFormatter() }

        single<JsonParser> { MoshiJsonParser() }
    }
}