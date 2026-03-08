package com.doaamosalam.domain.model

data class NextPrayer(
    val name: String,
    val nameArabic: String,
    val time: String,
    val remainingSeconds: Long
)
