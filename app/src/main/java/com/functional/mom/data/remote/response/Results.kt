package com.functional.mom.data.remote.response

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("seller")
    val seller: Seller? = null,
    @SerializedName("address")
    val address: Address? = null
)
