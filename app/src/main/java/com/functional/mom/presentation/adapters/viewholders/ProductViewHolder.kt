package com.functional.mom.presentation.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.functional.mom.R
import com.functional.mom.commons.Constants.Companion.HTTP
import com.functional.mom.commons.Constants.Companion.HTTPS
import com.functional.mom.databinding.HolderProductBinding
import com.functional.mom.repository.models.ResultsModel

class ProductViewHolder(private val binding: HolderProductBinding, private val picasso: Picasso) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: ResultsModel,
        productSetOnClickListener: (product: ResultsModel) -> Unit
    ) {
        initViews(data)
        initListener(data, productSetOnClickListener)
    }

    private fun initViews(data: ResultsModel) {
        with(binding) {
            textTitleDes.text = data.title
            textTitleTextTitleNickNameDes.text = data.nickname
            textTitleCityDes.text = data.city_name
            loadImage(data.thumbnail)
        }
    }

    private fun loadImage(url: String) {
        val urlThumbnail = url.replace(HTTP, HTTPS)

        picasso.load(urlThumbnail)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.search_image)
            .error(R.drawable.broken_image).into(binding.imgIcon)
    }

    private fun initListener(
        data: ResultsModel,
        productSetOnClickListener: (product: ResultsModel) -> Unit
    ) {
        binding.cardView.setOnClickListener {
            productSetOnClickListener(data)
        }
    }
}
