package com.juntadeandalucia.ced.data.remote

import com.juntadeandalucia.ced.commons.data.types.Either
import com.juntadeandalucia.ced.commons.json.JsonParser
import com.juntadeandalucia.ced.data.BaseResponse
import com.juntadeandalucia.ced.data.ErrorResponse
import com.juntadeandalucia.ced.data.InterceptorConnection
import com.juntadeandalucia.ced.data.State
import com.juntadeandalucia.ced.domain.RepositoryFailure
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.reflect.KClass

class ResponseParse(val jsonParser: JsonParser) {

    companion object {
        const val REQUEST_CORRECT = "C"
        const val REQUEST_ERROR = "E"
        const val REQUEST_NOTICE = "AV"
        const val REQUEST_ERROR_WITH_MESAGE = "OP"
        const val REQUEST_OP_PASSWORD_NOT_MODIFY = "OP-CLAVE_NO_CAMBIADA"
        const val REQUEST_OP_PASSWORD_EXPIRED = "OP-CLAVE_CADUCADA"
        const val REQUEST_OP_USER_IDEA = "OP-IDEA_NO_UTILIZADO"
        const val REQUEST_OP_FIRST_CONNECTION = "OP-PRIMERA_CONEXION"
        const val REQUEST_OP_DATA_RECCLA = "OP-DATOS_RECCLA"
        const val REQUEST_OP_USER_ERROR = "OP-USUARIO_INCORRECTO"
        const val REQUEST_OP_USER_DISABLE = "OP-USUARIO_DESACTIVADO"
        const val REQUEST_OP_USER_BLOQUED = "OP-USUARIO_BLOQUEADO"

        const val UNAUTHORIZED_ERROR_CODE = 404
        const val INVALID_LOGIN_PARAMETERS = 101
        const val INTERNAL_ERROR = 500
        const val INTERNAL_ERROR_2 = 502
        const val INTERNAL_ERROR_3 = 503
    }


    inline fun <reified KnownError : Any, reified Success> parse(
        response: Response<ResponseBody>,
        knownErrorKClassesByErrorCodes: Map<String, KClass<out KnownError>> = emptyMap()
    ): ParsedResponse<KnownError, Success> {
        if (response.isSuccessful) {
            val successBody: String = response.body()!!.string()
            val success: BaseResponse = jsonParser.fromJson(successBody, BaseResponse::class.java)

            if (success.ESTADO.CODIGO != REQUEST_CORRECT) {
                when (val either = parseErrorControl(successBody, knownErrorKClassesByErrorCodes)) {
                    is Either.Left -> return either.l
                    is Either.Right -> return either.r
                }

            } else if (success.RESULTADO != null) {
                return parseSuccess(success.RESULTADO)
            }

        }
            when (val either = parseError(response, knownErrorKClassesByErrorCodes)) {
                is Either.Left -> return either.l
                is Either.Right -> return either.r
            }


    }


    inline fun <reified KnownError : Any> parseNoContentResponse(
        response: Response<ResponseBody>,
        knownErrorKClassesByErrorCodes: Map<String, KClass<out KnownError>> = emptyMap()
    ): ParsedResponse<KnownError, NoContentResponse> {
        if (response.isSuccessful) {
            val successBody: String = response.body()!!.string()
            val success: BaseResponse = jsonParser.fromJson(successBody, BaseResponse::class.java)

            if (success.ESTADO.CODIGO != REQUEST_CORRECT) {
                when (val either = parseErrorControl(successBody, knownErrorKClassesByErrorCodes)) {
                    is Either.Left -> return either.l
                    is Either.Right -> return either.r
                }

            } else return ParsedResponse.Success(NoContentResponse())

        } else {
            when (val either = parseError(response, knownErrorKClassesByErrorCodes)) {
                is Either.Left -> return either.l
                is Either.Right -> return either.r
            }
        }
    }

    inline fun <reified Success> parseSuccess(successBody: String): ParsedResponse.Success<Success> {
        val success: Success = jsonParser.fromJson(successBody, Success::class.java)
        return ParsedResponse.Success(success)
    }

    inline fun <reified KnownError : Any> parseError(
        response: Response<ResponseBody>,
        knownErrorKClassesByErrorCodes: Map<String, KClass<out KnownError>>
    ): Either<ParsedResponse.Failure, ParsedResponse.KnownError<KnownError>> {
        val errorBody: String = response.errorBody()!!.string()
        val errorResponse: ErrorResponse = jsonParser.fromJson(errorBody, ErrorResponse::class.java)
        val errorCode: String = errorResponse.errorCode

        if (errorCode.toInt() == INVALID_LOGIN_PARAMETERS) {
            return Either.Left(ParsedResponse.Failure(RepositoryFailure.Unauthorized))
        } else if (errorCode.toInt() == INTERNAL_ERROR || errorCode.toInt() == INTERNAL_ERROR_2 || errorCode.toInt() == INTERNAL_ERROR_3) {
            return Either.Left(ParsedResponse.Failure(RepositoryFailure.ServerError))
        }

        val knownErrorClass: KClass<out KnownError> =
            knownErrorKClassesByErrorCodes[errorCode] ?: throw Exception()
        val objectInstance: KnownError? = knownErrorClass.objectInstance
        if (objectInstance != null) {
            return Either.Right(ParsedResponse.KnownError(objectInstance))
        }
        val knownError: KnownError = jsonParser.fromJson(errorBody, knownErrorClass.java)
        return Either.Right(ParsedResponse.KnownError(knownError))
    }


    inline fun <reified KnownError : Any> parseErrorControl(
        response: String,
        knownErrorKClassesByErrorCodes: Map<String, KClass<out KnownError>>
    ): Either<ParsedResponse.Failure, ParsedResponse.KnownError<KnownError>> {
        val errorBody: String = response
        val errorResponse: BaseResponse = jsonParser.fromJson(errorBody, BaseResponse::class.java)
        val errorCode: String = errorResponse.ESTADO.CODIGO


        val knownErrorClass: KClass<out KnownError> =
            knownErrorKClassesByErrorCodes[errorCode] ?: throw Exception()
        val objectInstance: KnownError? = knownErrorClass.objectInstance
        if (objectInstance != null) {
            return Either.Right(ParsedResponse.KnownError(objectInstance))
        }
        val knownError: KnownError = jsonParser.fromJson(errorBody, knownErrorClass.java)
        return Either.Right(ParsedResponse.KnownError(knownError))
    }


}