package com.functional.mom.presentation.events

sealed class SharedEvents {
    object grantedPermimssionCameraEvent : SharedEvents()
    object grantedPermimssionStoreEvent : SharedEvents()
    object grantedPermimssionLocationEvent : SharedEvents()
    object grantedPermimssionAllEvent : SharedEvents()
    object grantedPermimssionCallSuccessEvent : SharedEvents()
}