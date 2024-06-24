package com.functional.mom.data.remote

import com.functional.mom.data.remote.response.LookFor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitServicesInterface {
    @Headers("Content-Type: application/json")
    @GET("sites/MLA/search")
    fun getProductsBySearch(@Query("q") query: String): Call<LookFor>?
}