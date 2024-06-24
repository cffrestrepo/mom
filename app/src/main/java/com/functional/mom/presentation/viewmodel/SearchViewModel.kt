package com.functional.mom.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.functional.mom.presentation.events.SearchEvents
import com.functional.mom.presentation.states.SearchScreenStates
import com.functional.mom.usecases.FetchRemoteProductsUseCase
import com.functional.mom.usecases.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchRemoteProductsUseCase: FetchRemoteProductsUseCase,
    private val getAllProductUseCase: GetAllProductsUseCase
) :
    BaseViewModel<SearchEvents, SearchScreenStates>() {

    override fun manageEvent(event: SearchEvents) {
        when (event) {
            SearchEvents.InitEvent -> {
                historyProducts()
            }
            is SearchEvents.SearchEvent -> {
                searchProducts(event.query)
            }
            SearchEvents.InactiveNavigateToProductsEvent -> {
                setInactiveNavigateToProducts()
            }
        }
    }

    /***
     *  The last displayed products are loaded
     */
    private fun historyProducts() {
        viewModelScope.launch {
            val result = getAllProductUseCase.execute(Unit)
            result.fold(functionLeft = { _ ->
                // TODO() this event should be flagged in crashlitycs
            }, functionRight = { data ->
                setState(SearchScreenStates.HistoryProductsLoadedState(data))
            })
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            setState(SearchScreenStates.LoadingState(true))
            val result = fetchRemoteProductsUseCase.execute(query)
            result.fold(functionLeft = { error ->
                setState(SearchScreenStates.LoadingState(false))
                setState(SearchScreenStates.HandledErrorState(error))
            }, functionRight = { success ->
                setState(SearchScreenStates.LoadingState(false))
                setState(SearchScreenStates.NavigateToProductsState(success))
            })
        }
    }

    private fun setInactiveNavigateToProducts() {
        setState(SearchScreenStates.NavigateToProductsState(false))
    }
}
