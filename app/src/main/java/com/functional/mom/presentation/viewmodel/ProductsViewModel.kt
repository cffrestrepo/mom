package com.functional.mom.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.functional.mom.presentation.events.ProductEvents
import com.functional.mom.presentation.states.ProductStates
import com.functional.mom.usecases.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * Test class [ProductsViewModelTest]
 */
@HiltViewModel
class ProductsViewModel @Inject constructor(private val getAllProductsUseCase: GetAllProductsUseCase) :
    BaseViewModel<ProductEvents, ProductStates>() {

    override fun manageEvent(event: ProductEvents) {
        when (event) {
            ProductEvents.initEvent -> {
                loadResultsOfSearch()
            }
        }
    }

    /***
     * Query the database, to get the search results
     */
    private fun loadResultsOfSearch() {
        viewModelScope.launch {
            setState(ProductStates.LoadingState(true))
            val result = getAllProductsUseCase.execute(Unit)
            result.fold(functionLeft = { error ->
                // TODO this event should be flagged in Crashlitycs
                setState(ProductStates.LoadingState(false))
                setState(ProductStates.HandledErrorState(error))
            }, functionRight = { data ->
                setState(ProductStates.LoadingState(false))
                setState(ProductStates.DataLoadedState(data))
            })
        }
    }
}
