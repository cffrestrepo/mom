package com.functional.mom.usecases

import com.functional.mom.commons.Either
import com.functional.mom.data.network.HandledError
import com.functional.mom.repository.contracts.ProductRepositorySource
import javax.inject.Inject

/***
 * Test class of [FetchRemoteProductsUseCaseTest]
 */
class FetchRemoteProductsUseCase @Inject constructor(private val productRepository: ProductRepositorySource) :
    UseCaseBase<Either<HandledError, Boolean>, String>() {
    override suspend fun execute(params: String): Either<HandledError, Boolean> {
        return productRepository.fetchRemote(params)
    }
}
