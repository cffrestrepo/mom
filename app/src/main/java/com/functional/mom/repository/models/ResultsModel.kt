package com.functional.mom.repository.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsModel
    (
    val title: String = "",
    val thumbnail: String = "",
    val price: String = "",
    val nickname: String = "",
    val address: String = "",
    val city_name: String = ""
) : Parcelable
