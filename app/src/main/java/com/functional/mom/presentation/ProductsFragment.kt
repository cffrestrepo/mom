package com.functional.mom.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.functional.mom.R
import com.functional.mom.commons.Constants
import com.functional.mom.commons.Constants.Companion.GREEN_BACK
import com.functional.mom.commons.Constants.Companion.RED_BACK
import com.functional.mom.databinding.FragmentProductsBinding
import com.functional.mom.presentation.adapters.ProductsAdapter
import com.functional.mom.presentation.events.ProductEvents
import com.functional.mom.commons.Extensions.withColor
import com.functional.mom.commons.PermissionManager
import com.functional.mom.presentation.states.ProductStates
import com.functional.mom.presentation.viewmodel.ProductsViewModel
import com.functional.mom.repository.models.ResultsModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : BaseFragment() {

    @Inject
    lateinit var picasso: Picasso
    private lateinit var binding: FragmentProductsBinding
    lateinit var viewModel: ProductsViewModel
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(productSetOnClickListener = ::goToProductDetail, picasso = picasso)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        permissionManager = PermissionManager.from(this)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observerBase()
        observer()
        initEvent()
    }

    private fun initEvent() {
        viewModel.postEvent(ProductEvents.initEvent)
    }

    private fun observer() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProductStates.DataLoadedState -> {
                    populateAdapter(state.data)
                }
                is ProductStates.HandledErrorState -> {
                    handledError(state.error)
                }
                is ProductStates.LoadingState -> {
                    binding.progressIndicator.isVisible = state.isVisible
                }
            }
        }
    }

    private fun initViews() {
        with(binding.recyclerviewProducts) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }
    }

    private fun populateAdapter(data: List<ResultsModel>) {
        productsAdapter.submitList(data)
    }

    private fun goToProductDetail(product: ResultsModel) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.PRODUCT, product)
        findNavController().navigate(R.id.action_ProductsFragment_to_DetailFragment, bundle)
    }

    override fun successPermission(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .withColor(Color.parseColor(GREEN_BACK))
            .show()
    }

    override fun errorPermission(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .withColor(Color.parseColor(RED_BACK))
            .show()
    }
}
