package com.functional.mom.data.remote.response

import com.google.gson.annotations.SerializedName

data class LookFor(
    @SerializedName("query")
    val query: String = "",
    @SerializedName("results")
    val results: List<Results>? = null
)
