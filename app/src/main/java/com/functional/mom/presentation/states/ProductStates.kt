package com.functional.mom.presentation.states

import com.functional.mom.data.network.HandledError
import com.functional.mom.repository.models.ResultsModel

sealed class ProductStates {
    data class LoadingState(val isVisible: Boolean) : ProductStates()
    data class DataLoadedState(val data: List<ResultsModel>) : ProductStates()
    data class HandledErrorState(val error: HandledError) : ProductStates()
}