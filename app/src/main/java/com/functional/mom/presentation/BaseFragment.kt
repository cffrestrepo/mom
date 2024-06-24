package com.functional.mom.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.functional.mom.R
import com.functional.mom.commons.PermissionManager
import com.functional.mom.commons.PermissionType
import com.functional.mom.data.network.HandledError
import com.functional.mom.presentation.events.SharedEvents
import com.functional.mom.presentation.states.SharedStates
import com.functional.mom.presentation.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    lateinit var viewModelShared: SharedViewModel
    lateinit var permissionManager: PermissionManager

    open fun successPermission(message: String) {}
    open fun errorPermission(message: String) {}

    fun observerBase() {
        viewModelShared = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModelShared.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SharedStates.requestPermimssionAllState -> {
                    viewModelShared.postEvent(SharedEvents.grantedPermimssionCallSuccessEvent)
                    permissionManager
                        .request(PermissionType.MandatoryForFeatureOne)
                        .rationale(requireContext().getString(R.string.action_permission_write_location))
                        .checkPermission { granted ->
                            if (granted) {
                                successPermission(requireContext().getString(R.string.action_permission_write_location_success))
                            } else {
                                errorPermission(requireContext().getString(R.string.action_permission_write_location_failed))
                            }
                        }
                }
                SharedStates.requestPermimssionCameraState -> {
                    viewModelShared.postEvent(SharedEvents.grantedPermimssionCallSuccessEvent)
                    permissionManager
                        .request(PermissionType.Camera)
                        .rationale(requireContext().getString(R.string.action_permission_camera_request))
                        .checkPermission { granted ->
                            if (granted) {
                                successPermission(requireContext().getString(R.string.action_permission_camera_success))
                            } else {
                                errorPermission(requireContext().getString(R.string.action_permission_camera_failed))
                            }
                        }
                }
                SharedStates.requestPermimssionLocationState -> {
                    viewModelShared.postEvent(SharedEvents.grantedPermimssionCallSuccessEvent)
                    permissionManager
                        .request(PermissionType.Location)
                        .rationale(requireContext().getString(R.string.action_permission_location_request))
                        .checkPermission { granted ->
                            if (granted) {
                                successPermission(requireContext().getString(R.string.action_permission_location_success))
                            } else {
                                errorPermission(requireContext().getString(R.string.action_permission_location_failed))
                            }
                        }
                }
                SharedStates.requestPermimssionStoreState -> {
                    viewModelShared.postEvent(SharedEvents.grantedPermimssionCallSuccessEvent)
                    permissionManager
                        .request(PermissionType.Storage)
                        .rationale(requireContext().getString(R.string.action_permission_storage_request))
                        .checkPermission { granted ->
                            if (granted) {
                                successPermission(requireContext().getString(R.string.action_permission_storage_success))
                            } else {
                                errorPermission(requireContext().getString(R.string.action_permission_storage_failed))
                            }
                        }
                }
                SharedStates.requestCallSuccessState -> {
                    // TODO() Permission request created
                }
            }
        }
    }

    protected fun handledError(handledError: HandledError) {
        val message = when (handledError) {
            is HandledError.BadRequest -> {
                // TODO send event to analytics, make flow decisions, retries, other navigation, etc.
                handledError.message + ": " + handledError.code
            }
            is HandledError.InternalServerError -> {
                // TODO send event to analytics, make flow decisions, retries, other navigation, etc.
                handledError.message + ": " + handledError.code
            }
            is HandledError.NetworkConnection -> {
                // TODO send event to analytics, make flow decisions, retries, other navigation, etc.
                handledError.message + ": " + handledError.code
            }
            is HandledError.ResourceNotFound -> {
                // TODO send event to analytics, make flow decisions, retries, other navigation, etc.
                handledError.message + ": " + handledError.code
            }
            is HandledError.UnAuthorized -> {
                // TODO send event to analytics, make flow decisions, retries, other navigation, etc.
                handledError.message + ": " + handledError.code
            }
            is HandledError.UnExpected -> {
                // TODO send event to analytics, make flow decisions, retries, other navigation, etc.
                handledError.message + ": " + handledError.code
            }
            is HandledError.Unknown -> {
                // TODO send event to analytics, make flow decisions, retries, other navigation, etc.
                handledError.message + ": " + handledError.code
            }
        }

        materialAlertDialogBuilder
            .setTitle(requireContext().getString(R.string.ups_title))
            .setMessage(message)
            .setPositiveButton(requireContext().getString(R.string.accept)) { _, _ -> }
            .show()
    }
}
