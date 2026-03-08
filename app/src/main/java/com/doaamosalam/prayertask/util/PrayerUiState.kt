package com.doaamosalam.prayertask.util

import com.doaamosalam.domain.model.nextprayer.NextPrayer
import com.doaamosalam.domain.model.prayerTime.PrayerTimes

data class PrayerUiState(
    val isLoading: Boolean = false,
    val prayerTimes: PrayerTimes? = null,
    val nextPrayer: NextPrayer? = null,
    val errorMessage: String? = null,
    val isOffline: Boolean = false
)
