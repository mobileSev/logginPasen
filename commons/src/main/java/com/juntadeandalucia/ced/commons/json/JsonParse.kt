package com.juntadeandalucia.ced.commons.json

interface JsonParser {

    fun <T> fromJson(json: String, type: Class<T>): T

    fun <T : Any> toJson(value: T): String

}
