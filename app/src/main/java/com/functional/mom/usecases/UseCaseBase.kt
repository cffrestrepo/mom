package com.functional.mom.usecases

abstract class UseCaseBase<out T, in X> {
    abstract suspend fun execute(params: X): T
}