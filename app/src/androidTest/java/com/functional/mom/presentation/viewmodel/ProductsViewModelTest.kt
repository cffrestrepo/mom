package com.functional.mom.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.functional.mom.commons.Either
import com.functional.mom.createListResultsModel
import com.functional.mom.presentation.events.ProductEvents
import com.functional.mom.presentation.states.ProductStates
import com.functional.mom.repository.models.ResultsModel
import com.functional.mom.usecases.GetAllProductsUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/***
 * Test class of [ProductsViewModel]
 */
@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getAllProductsUseCase: GetAllProductsUseCase

    @MockK
    private lateinit var stateObserver: Observer<ProductStates>

    lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(
            this,
            relaxUnitFun = true
        )
        viewModel = ProductsViewModel(getAllProductsUseCase)
        viewModel.apply {
            screenState.observeForever(stateObserver)
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
        with(viewModel) {
            screenState.removeObserver(stateObserver)
        }
    }

    @Test
    fun `given-the-initEvent-event-the-previous-results-are-loaded-when-successful-the-status-is-updated-to-information-loaded`() =
        runBlocking {
            // GIVEN
            val event = ProductEvents.initEvent
            val data: List<ResultsModel> = createListResultsModel()
            coEvery { getAllProductsUseCase.execute(Unit) } returns Either.Right(data)

            // WHEN
            viewModel.postEvent(event)

            // THEN
            coVerifySequence {
                stateObserver.onChanged(ProductStates.LoadingState(true))
                getAllProductsUseCase.execute(Unit)
                stateObserver.onChanged(ProductStates.LoadingState(false))
                stateObserver.onChanged(ProductStates.DataLoadedState(data))
            }
        }
}
