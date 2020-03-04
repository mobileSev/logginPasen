package com.juntadeandalucia.ced.data

import com.juntadeandalucia.ced.commons.test.mock
import com.juntadeandalucia.ced.commons_android.NetworkManager
import com.juntadeandalucia.ced.data.remote.ParsedResponse
import com.juntadeandalucia.ced.data.remote.RemoteDataSourceExecutor
import com.juntadeandalucia.ced.domain.RepositoryFailure
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

class RemoteDataSourceExecutorTest {

    @Mock
    private lateinit var networkManager : NetworkManager
    private lateinit var remoteDataSourceExecutor: RemoteDataSourceExecutor


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
         remoteDataSourceExecutor = RemoteDataSourceExecutor(networkManager)
    }

    @Test

    fun `It returns no Internet failure if there is no Internet connection`()= runBlocking {
        //Given
        Mockito.`when`(networkManager.isThereConnectionToInternet()).thenReturn(false)

        val block: suspend () -> ParsedResponse<Unit, Unit> =
            mock()

        //When
        val result: ParsedResponse<Unit, Unit> = remoteDataSourceExecutor(block)

        //Then
        Assert.assertEquals(ParsedResponse.Failure(RepositoryFailure.NoInternet), result)

    }


    @Test
    fun `It return Unknown failure  if the function throws exceptio`() = runBlocking {
        Mockito.`when`(networkManager.isThereConnectionToInternet()).thenReturn(true)

        val block : suspend () -> ParsedResponse<Unit, Unit> = {throw  Exception()}

        //When

        val result: ParsedResponse<Unit, Unit> = remoteDataSourceExecutor(block)

        //Then

        Assert.assertEquals(ParsedResponse.Failure(RepositoryFailure.Unknown), result)

    }

    @Test
    fun `It return Succes failure if if the block works correctly`() = runBlocking {

        Mockito.`when`(networkManager.isThereConnectionToInternet()).thenReturn(true)

        val block : suspend () -> ParsedResponse<Unit, Unit> = {ParsedResponse.Success(Unit)}

        // When
        val result: ParsedResponse<Unit, Unit> = remoteDataSourceExecutor(block)


        // Then
        Assert.assertEquals(ParsedResponse.Success(Unit), result)
    }
}