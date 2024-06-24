package com.functional.mom.data.remote.sources

import com.functional.mom.commons.Either
import com.functional.mom.data.network.HandledError
import com.functional.mom.data.remote.response.LookFor

interface ProductDataRemoteSource {

    suspend fun getProductsBySearch(query: String): Either<HandledError, LookFor?>
}
