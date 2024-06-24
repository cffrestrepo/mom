package com.functional.mom.data.network

sealed class HandledError{
    data class NetworkConnection(val message: String, val code: Int) : HandledError()
    data class BadRequest(val message: String, val code: Int) : HandledError()
    data class UnAuthorized(val message: String, val code: Int) : HandledError()
    data class InternalServerError(val message: String, val code: Int) : HandledError()
    data class ResourceNotFound(val message: String, val code: Int) : HandledError()
    data class UnExpected(val message: String, val code: Int) : HandledError()
    data class Unknown(val message: String, val code: Int) : HandledError()
}