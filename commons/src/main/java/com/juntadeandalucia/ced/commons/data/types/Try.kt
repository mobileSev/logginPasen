package com.juntadeandalucia.ced.commons.data.types

sealed class Try<out S> {

    data class Exception(val e: Throwable) : Try<Nothing>()

    data class Success<out S>(val s: S) : Try<S>()


    val isFailure get() = this is Exception
    val isSuccess get() = this is Success<S>


    companion object {

        operator fun <S> invoke(block: () -> S): Try<S> =
            try {
                Success(block())
            } catch (f: Throwable) {
                Exception(f)
            }

        suspend fun <S> invokeSuspend(block: suspend () -> S): Try<S> =
            try {
                Success(block())
            } catch (f: Throwable) {
                Exception(f)
            }

    }


    fun <T> fold(fnE: (Throwable) -> T, fnS: (S) -> T): T =
        when (this) {
            is Exception -> fnE(e)
            is Success -> fnS(s)
        }

    fun toEither(): Either<Throwable, S> = fold({ Either.Left(it) }, { Either.Right(it) })

    fun toSuccessValueOrNull(): S? = when (this) {
        is Exception -> null
        is Success -> s
    }

}
