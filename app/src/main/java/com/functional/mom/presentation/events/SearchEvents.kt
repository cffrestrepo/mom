package com.functional.mom.presentation.events

sealed class SearchEvents {
    data class SearchEvent(val query: String) : SearchEvents()
    object InitEvent : SearchEvents()
    object InactiveNavigateToProductsEvent : SearchEvents()
}
