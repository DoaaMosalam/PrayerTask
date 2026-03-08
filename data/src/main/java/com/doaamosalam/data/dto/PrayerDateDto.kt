package com.doaamosalam.data.dto

import com.google.gson.annotations.SerializedName

data class PrayerDateDto(
    @SerializedName("readable") val readable: String,
    @SerializedName("hijri") val hijri: HijriDateDto
)
