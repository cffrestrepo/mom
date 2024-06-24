package com.functional.mom.presentation.states

sealed class SharedStates {
    object requestPermimssionCameraState : SharedStates()
    object requestPermimssionStoreState : SharedStates()
    object requestPermimssionLocationState : SharedStates()
    object requestPermimssionAllState : SharedStates()
    object requestCallSuccessState : SharedStates()
}