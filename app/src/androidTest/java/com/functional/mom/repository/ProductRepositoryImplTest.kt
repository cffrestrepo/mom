package com.functional.mom.repository

import com.functional.mom.PERRO
import com.functional.mom.commons.Constants
import com.functional.mom.commons.Either
import com.functional.mom.createListProductEntity
import com.functional.mom.createListResultsModel
import com.functional.mom.createLooFor
import com.functional.mom.data.local.dao.ProductDao
import com.functional.mom.data.local.entities.ProductEntity
import com.functional.mom.data.network.HandledError
import com.functional.mom.data.remote.sources.ProductDataRemoteSource
import com.functional.mom.repository.mappers.ProductMapper
import com.functional.mom.repository.models.ResultsModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/***
 * Test class of [ProductRepositoryImpl]
 */
@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    @MockK
    lateinit var productSource: ProductDao

    @MockK
    lateinit var productDataRemoteSource: ProductDataRemoteSource

    @MockK
    lateinit var productMapper: ProductMapper

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    lateinit var productRepositoryImpl: ProductRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(
            this,
            relaxUnitFun = true
        )
        productRepositoryImpl =
            ProductRepositoryImpl(productSource, productDataRemoteSource, productMapper, dispatcher)
    }

    @After
    fun tearDown() = clearAllMocks()

    @Test
    fun `given-a-call-to-the-getall-method-when-its-response-is-successful-then-it-returns-a-list-of-resultsmodel`() =
        runBlocking {
            // GIVEN
            val listProductsEntity: List<ProductEntity> = createListProductEntity()
            val listResultsModel: List<ResultsModel> = createListResultsModel()

            coEvery { productSource.getAll() } returns listProductsEntity
            every { productMapper.productEntityToProductModel(any()) } returns listResultsModel

            // WHEN
            val result = productRepositoryImpl.getAll()

            // VERIFY
            assert(result is Either.Right)
            Assert.assertEquals((result as Either.Right).value, listResultsModel)

            coVerifySequence {
                productSource.getAll()
                productMapper.productEntityToProductModel(listProductsEntity)
            }
        }

    @Test
    fun `given-a-call-to-the-getall-method-when-its-response-is-failed-then-it-returns-a-handlederror`() =
        runBlocking {
            // GIVEN
            coEvery { productSource.getAll() }.throws(RuntimeException())

            // WHEN
            val result = productRepositoryImpl.getAll()

            // VERIFY
            assert(result is Either.Left)
            assert((result as Either.Left).error is HandledError)
        }

    @Test
    fun `given-a-call-to-insertAll-when-the-response-is-successful-returns-a-boolean`() =
        runBlocking {
            // GIVEN
            val products: List<ProductEntity> = createListProductEntity()
            coEvery { productSource.delete() } returns Unit
            coEvery { productSource.insert(any()) } returns Unit

            // WHEN
            val result = productRepositoryImpl.insertAll(products)

            // VERIFY
            assert(result is Either.Right)
            Assert.assertEquals((result as Either.Right).value, true)

            coVerifySequence {
                productSource.delete()
                productSource.insert(products)
            }
        }

    @Test
    fun `given-a-call-to-insertAll-when-the-response-is-failed-returns-a-handlederror`() =
        runBlocking {
            // GIVEN
            val products: List<ProductEntity> = createListProductEntity()
            coEvery { productSource.delete() }.throws(RuntimeException())

            // WHEN
            val result = productRepositoryImpl.insertAll(products)

            // VERIFY
            assert(result is Either.Left)
            assert((result as Either.Left).error is HandledError)
        }

    @Test
    fun `given-a-call-to-fetchRemote-when-the-call-fails-returns-a-handlederror`() =
        runBlocking {
            // GIVEN
            val query = PERRO
            val handledError = HandledError.UnExpected(
                Constants.UNEXPECTED_MESSAGE,
                code = Constants.UNEXPECTED
            )

            coEvery { productDataRemoteSource.getProductsBySearch(any()) } returns Either.Left(
                handledError
            )

            // WHEN
            val result = productRepositoryImpl.fetchRemote(query)

            // VERIFY
            assert(result is Either.Left)
            coVerifySequence {
                productDataRemoteSource.getProductsBySearch(query)
            }
        }

    @Test
    fun `given-a-call-to-fetchRemote-when-the-call-success-returns-a-null`() =
        runBlocking {
            // GIVEN
            val query = PERRO

            coEvery { productDataRemoteSource.getProductsBySearch(any()) } returns Either.Right(
                null
            )

            // WHEN
            val result = productRepositoryImpl.fetchRemote(query)

            // VERIFY
            assert(result is Either.Left)
            coVerifySequence {
                productDataRemoteSource.getProductsBySearch(query)
            }
        }

    @Test
    fun `given-a-call-to-fetchRemote-when-the-call-success-returns-a-lookFor`() =
        runBlocking {
            // GIVEN
            val query = PERRO
            val lookFor = createLooFor()
            val listProductEntity = createListProductEntity()

            coEvery { productDataRemoteSource.getProductsBySearch(any()) } returns Either.Right(
                lookFor
            )
            every { productMapper.productResponseToProductEntity(any()) } returns listProductEntity
            coEvery { productSource.delete() } returns Unit
            coEvery { productSource.insert(any()) } returns Unit

            // WHEN
            val result = productRepositoryImpl.fetchRemote(query)

            // VERIFY
            assert(result is Either.Right)

            coVerifySequence {
                productDataRemoteSource.getProductsBySearch(query)
                productMapper.productResponseToProductEntity(lookFor.results!!)
                productSource.delete()
                productSource.insert(listProductEntity)
            }
        }
}
