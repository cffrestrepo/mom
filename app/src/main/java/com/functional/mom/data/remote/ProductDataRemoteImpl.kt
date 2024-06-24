package com.functional.mom.data.remote

import com.functional.mom.commons.Either
import com.functional.mom.data.network.ErrorFactory
import com.functional.mom.data.network.HandledError
import com.functional.mom.data.remote.response.LookFor
import com.functional.mom.data.remote.sources.ProductDataRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/***
 * Test class [ProductDataRemoteImplTest]
 */
class ProductDataRemoteImpl @Inject constructor(
    private val retrofitServicesInterface: RetrofitServicesInterface,
    private val errorFactory: ErrorFactory
) :
    ProductDataRemoteSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getProductsBySearch(query: String): Either<HandledError, LookFor?> {
        return suspendCancellableCoroutine { continuation ->
            val call: Call<LookFor>? =
                retrofitServicesInterface.getProductsBySearch(query)

            call?.enqueue(object : Callback<LookFor> {
                override fun onFailure(call: Call<LookFor>, throwable: Throwable) {
                    val handledError: HandledError = errorFactory.handledError(throwable)
                    continuation.resume(Either.Left(handledError), onCancellation = { })
                }

                override fun onResponse(call: Call<LookFor>, response: Response<LookFor>) {
                    continuation.resume(Either.Right(response.body()), onCancellation = { })
                }
            })
        }
    }
}
