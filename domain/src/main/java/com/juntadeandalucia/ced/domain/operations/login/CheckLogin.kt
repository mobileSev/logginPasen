package com.juntadeandalucia.ced.domain.operations.login

import com.juntadeandalucia.ced.commons.data.types.Either
import com.juntadeandalucia.ced.domain.base.BaseUseCase

class CheckLogin(private val repository: LoginRepository) : BaseUseCase<CheckLoginError,Unit, LoginInput>() {

    override suspend fun run(params: LoginInput): Either<CheckLoginError, Unit> {
        return repository.checkLogin(params)
    }
}