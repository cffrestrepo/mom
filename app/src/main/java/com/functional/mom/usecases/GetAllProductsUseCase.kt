package com.functional.mom.usecases

import com.functional.mom.commons.Either
import com.functional.mom.data.network.HandledError
import com.functional.mom.repository.contracts.ProductRepositorySource
import com.functional.mom.repository.models.ResultsModel
import javax.inject.Inject

/***
 * Test class [GetAllProductsUseCaseTest]
 */
class GetAllProductsUseCase @Inject constructor(private val productRepository: ProductRepositorySource) :
    UseCaseBase<Either<HandledError, List<ResultsModel>>, Unit>() {
    override suspend fun execute(params: Unit): Either<HandledError, List<ResultsModel>> {
        return productRepository.getAll()
    }
}
