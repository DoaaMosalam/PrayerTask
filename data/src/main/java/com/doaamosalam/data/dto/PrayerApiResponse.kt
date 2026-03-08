package com.doaamosalam.data.dto


import com.google.gson.annotations.SerializedName

data class PrayerApiResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: PrayerTimeResponse
)