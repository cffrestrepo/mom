package com.functional.mom.presentation.states

import com.functional.mom.data.network.HandledError
import com.functional.mom.repository.models.ResultsModel

sealed class SearchScreenStates {
    data class LoadingState(val isVisible: Boolean) : SearchScreenStates()
    data class NavigateToProductsState(val data: Boolean) : SearchScreenStates()
    data class HandledErrorState(val error: HandledError) : SearchScreenStates()

    data class HistoryProductsLoadedState(val data: List<ResultsModel>) : SearchScreenStates()
}
