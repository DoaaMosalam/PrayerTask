package com.doaamosalam.data.dto

import com.google.gson.annotations.SerializedName

data class PrayerTimeResponse(
    @SerializedName("timings") val timings: TimingsDto,
    @SerializedName("date") val date: PrayerDateDto,
    @SerializedName("meta") val meta: PrayerMetaDto
)
