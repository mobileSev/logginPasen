package com.juntadeandalucia.ced.data.operations.login

import com.juntadeandalucia.ced.data.remote.RemoteDataSourceExecutor
import com.juntadeandalucia.ced.domain.operations.login.LoginInput
import com.juntadeandalucia.ced.domain.operations.login.LoginRepository

class LoginRepositoryImpl(val dataSource: LoginRemoteDataSource, val executor: RemoteDataSourceExecutor) :
    LoginRepository {
    override suspend fun checkLogin(input : LoginInput) {
            executor.invoke {
                dataSource.doLogin(
                    LoginRequest(
                        input.username,
                        input.password,
                        input.version
                    )
                )
            }
    }

}
