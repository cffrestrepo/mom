package com.functional.mom.data.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.functional.mom.commons.Constants.Companion.BAD_REQUEST
import com.functional.mom.commons.Constants.Companion.INTERNAL_SERVER
import com.functional.mom.commons.Constants.Companion.NET_WORK_CONNECTION
import com.functional.mom.commons.Constants.Companion.NOT_FOUND
import com.functional.mom.commons.Constants.Companion.UNAUTHORIZED
import com.functional.mom.commons.Constants.Companion.UNEXPECTED
import com.functional.mom.commons.Constants.Companion.UNKNOWN
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ErrorFactory @Inject constructor() {

    fun handledError(throwable: Throwable): HandledError {
        return when (throwable) {
            is IOException -> {
                HandledError.NetworkConnection(
                    "No hay conexión a internet", code = NET_WORK_CONNECTION
                )
            }
            is HttpException -> extractHttpExceptions(throwable)
            else -> HandledError.UnExpected(
                "Ups, ha ocurrido un error inesperado",
                code = UNEXPECTED
            )
        }
    }

    private fun extractHttpExceptions(httpException: HttpException): HandledError {
        val body = httpException.response()?.errorBody()
        val gson = GsonBuilder().create()

        val responseBody = gson.fromJson(body.toString(), JsonObject::class.java)
        val httpError = gson.fromJson(responseBody, HttpError::class.java)

        return when (httpError.errorCode) {
            BAD_REQUEST ->
                HandledError.BadRequest(httpError.errorMessage, code = BAD_REQUEST)

            INTERNAL_SERVER ->
                HandledError.InternalServerError(httpError.errorMessage, code = INTERNAL_SERVER)

            UNAUTHORIZED ->
                HandledError.UnAuthorized(httpError.errorMessage, code = UNAUTHORIZED)

            NOT_FOUND ->
                HandledError.ResourceNotFound(httpError.errorMessage, code = NOT_FOUND)

            else ->
                HandledError.Unknown(httpError.errorMessage, code = UNKNOWN)
        }
    }
}