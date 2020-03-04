package com.juntadeandalucia.ced.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.juntadeandalucia.ced.data.InterceptorConnection
import com.juntadeandalucia.ced.data.operations.login.LoginRemoteDataSource
import com.juntadeandalucia.ced.domain.operations.login.LoginRepository
import com.juntadeandalucia.ced.data.operations.login.LoginRepositoryImpl
import com.juntadeandalucia.ced.data.operations.login.LoginService
import com.juntadeandalucia.ced.data.remote.RemoteDataSourceExecutor
import com.juntadeandalucia.ced.data.remote.ResponseParse
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class DataKoinConfiguration(private val baseUrl: String) {

    companion object {
        private const val CONNECT_TIMEOUT = 15L
        private const val READ_TIMEOUT = 15L
    }

    fun getModule() = module {

        //Repository
        single<LoginRepository> { LoginRepositoryImpl(get(), get()) }

        // Remote data sources
        single { RemoteDataSourceExecutor(get()) }
        single { LoginRemoteDataSource(get(), get()) }


        // Retrofit
        single { SSLConfig(BuildConfig.FLAVOR, androidContext()) }
        single { InterceptorConnection(get()) }
        single(named("juntaClient")) { createOkHttpClient(get(), get()) }
        single(named("retrofit")) { createRetrofit(get(named("juntaClient"))) }

        //All called of retrofit
        single { createRetrofitImplementation<LoginService>(get(named("retrofit"))) }

        // Response parsers
        single { ResponseParse(get()) }

    }


    private fun createOkHttpClient(
        sslConfig: SSLConfig,
        interceptorConnection: InterceptorConnection
    ): OkHttpClient {
        val certificateSha256 = sslConfig.extractCertificate()

        val interceptorLog: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }


        return if (certificateSha256 != null) {
            OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .certificatePinner(buildCertificatePinner(certificateSha256))
                .addInterceptor(interceptorConnection)
                .addInterceptor(interceptorLog)
                .build()
        } else {
            OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptorConnection)
                .addInterceptor(interceptorLog)
                .build()
        }
    }

    private fun buildCertificatePinner(sha256: String): CertificatePinner {
        return CertificatePinner.Builder()
            .add(baseUrl, sha256)
            .build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, baseUrl)

    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


    private inline fun <reified T> createRetrofitImplementation(retrofit: Retrofit): T =
        retrofit.create(T::class.java)


}