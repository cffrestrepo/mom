package com.functional.mom.repository.contracts

import com.functional.mom.commons.Either
import com.functional.mom.data.local.entities.ProductEntity
import com.functional.mom.data.network.HandledError
import com.functional.mom.repository.models.ResultsModel

interface ProductRepositorySource {

    suspend fun getAll(): Either<HandledError, List<ResultsModel>>
    suspend fun fetchRemote(query: String): Either<HandledError, Boolean>
    suspend fun insertAll(product: List<ProductEntity>): Either<HandledError, Boolean>
}