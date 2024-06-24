package com.functional.mom.data.remote.response

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("city_name")
    val city_name: String = ""
)
