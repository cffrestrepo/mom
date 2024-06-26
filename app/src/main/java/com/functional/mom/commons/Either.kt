package com.functional.mom.commons

sealed class Either<out ErrorType, out ResultType> {
    class Left<ErrorType>(val error: ErrorType) : Either<ErrorType, Nothing>()
    class Right<ResultType>(val value: ResultType) : Either<Nothing, ResultType>()

    fun <T> fold(functionLeft: (ErrorType) -> T, functionRight: (ResultType) -> T): T {
        return when (this) {
            is Left -> functionLeft(error)
            is Right -> functionRight(value)
        }
    }

    suspend fun <T> foldSuspendable(
        functionLeft: suspend (ErrorType) -> T,
        functionRight: suspend (ResultType) -> T
    ): T {
        return when (this) {
            is Left -> functionLeft(error)
            is Right -> functionRight(value)
        }
    }
}
