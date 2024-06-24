package com.functional.mom.data.remote.response

import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("nickname")
    val nickname: String = ""
)
