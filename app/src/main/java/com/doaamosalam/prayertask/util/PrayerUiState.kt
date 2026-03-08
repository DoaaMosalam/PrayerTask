package com.doaamosalam.prayertask.util

import com.doaamosalam.domain.model.PrayerTime.PrayerTimes

data class PrayerUiState(
    val isLoading: Boolean = false,
    val prayerTimes: PrayerTimes? = null,
//    val nextPrayer: NextPrayer? = null,
    val errorMessage: String? = null,
    val isOffline: Boolean = false
)
