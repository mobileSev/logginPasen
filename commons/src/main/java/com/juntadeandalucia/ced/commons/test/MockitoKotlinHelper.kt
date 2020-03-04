package com.juntadeandalucia.ced.commons.test

import org.mockito.ArgumentCaptor
import org.mockito.Mockito


// Returns Mockito.eq() as nullable type to avoid java.lang.IllegalStateException when null is returned.
// Generic T is nullable because implicitly bounded by Any?.
fun <T> eq(obj: T): T = Mockito.eq<T>(obj)

// Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when null is returned.
fun <T> any(): T = Mockito.any<T>()

// Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException when null is returned.
fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

// Helper function for creating an argumentCaptor in kotlin.
inline fun <reified T : Any> argumentCaptor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)

// [mrezapue, 06/02/2020] Added by us to avoid warning "Unchecked cast" and simplify our code.
inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
