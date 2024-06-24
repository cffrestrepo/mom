package com.functional.mom.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import com.functional.mom.databinding.HolderProductBinding
import com.functional.mom.presentation.adapters.viewholders.ProductViewHolder
import com.functional.mom.repository.models.ResultsModel

class ProductsAdapter(
    private val productSetOnClickListener: (product: ResultsModel) -> Unit,
    private val picasso: Picasso
) :
    ListAdapter<ResultsModel, ProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        HolderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false), picasso
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(getItem(position), productSetOnClickListener)


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsModel>() {
            override fun areItemsTheSame(oldItem: ResultsModel, newItem: ResultsModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ResultsModel, newItem: ResultsModel): Boolean =
                oldItem.title == newItem.title
        }
    }
}
