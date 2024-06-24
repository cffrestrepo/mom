package com.functional.mom.data.remote

import com.functional.mom.PERRO
import com.functional.mom.commons.Constants
import com.functional.mom.commons.Either
import com.functional.mom.createLooFor
import com.functional.mom.data.network.ErrorFactory
import com.functional.mom.data.network.HandledError
import com.functional.mom.data.remote.response.LookFor
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/***
 * Test class of [ProductDataRemoteImpl]
 */
@ExperimentalCoroutinesApi
class ProductDataRemoteImplTest {

    @MockK
    private lateinit var retrofitServicesInterface: RetrofitServicesInterface

    @MockK
    private lateinit var errorFactory: ErrorFactory

    private lateinit var productDataRemoteImpl: ProductDataRemoteImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(
            this,
            relaxUnitFun = true
        )
        productDataRemoteImpl = ProductDataRemoteImpl(retrofitServicesInterface, errorFactory)
    }

    @After
    fun tearDown() = clearAllMocks()

    @Test
    fun `Given-a-call-to-getProductsBySearch-When-this-answer-is-correct-Then-it-gives-us-a-Either-Right-of-type-LookFor-model`() =
        runBlocking {
            // GIVEN
            val looFor = createLooFor()
            val query = PERRO
            val mockedCall = mockk<retrofit2.Call<LookFor>>()

            coEvery {
                retrofitServicesInterface.getProductsBySearch(query)!!.enqueue(any())
            } answers {
                val callback = args[0] as retrofit2.Callback<LookFor>
                val response = retrofit2.Response.success(200, looFor)

                callback.onResponse(mockedCall, response)
            }

            // WHEN
            val result = productDataRemoteImpl.getProductsBySearch(query)

            // VERIFY
            assert(result is Either.Right)

            verifyOrder {
                retrofitServicesInterface.getProductsBySearch(query)
            }
        }

    @Test
    fun `Given-a-call-to-getProductsBySearch-When-this-answer-is-correct-Then-it-gives-us-a-Either-Left`() =
        runBlocking {
            // GIVEN
            val query = PERRO
            val mockedCall = mockk<retrofit2.Call<LookFor>>()
            val handledError = HandledError.UnExpected(
                Constants.UNEXPECTED_MESSAGE,
                code = Constants.UNEXPECTED
            )

            coEvery {
                retrofitServicesInterface.getProductsBySearch(query)!!.enqueue(any())
            } answers {
                val callback = args[0] as retrofit2.Callback<LookFor>
                val response: Response<Throwable> = retrofit2.Response.error<Throwable>(
                    500,
                    ResponseBody.create(null, "Error in the server")
                )

                callback.onFailure(mockedCall, response.body() ?: Exception())
            }

            every { errorFactory.handledError(any()) } returns handledError

            // WHEN
            val result = productDataRemoteImpl.getProductsBySearch(query)

            // VERIFY
            assert(result is Either.Left)

            verifyOrder {
                retrofitServicesInterface.getProductsBySearch(query)
                errorFactory.handledError(any())
            }
        }
}
