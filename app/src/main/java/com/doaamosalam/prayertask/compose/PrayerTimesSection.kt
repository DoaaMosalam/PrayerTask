package com.doaamosalam.prayertask.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doaamosalam.prayertask.ui.screen.PrayerItem
import com.doaamosalam.prayertask.ui.screen.PrayerRow
import com.doaamosalam.prayertask.ui.theme.TextGray

@Composable
fun PrayerTimesSection(
    prayers: List<PrayerItem>,
    nextPrayerName: String?
) {
    Text(
        text = "PRAYER TIMES",
        color = TextGray,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        modifier = Modifier.padding(start = 2.dp, bottom = 10.dp)
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        prayers.forEach { prayer ->
            PrayerRow(
                prayer = prayer,
                isNext = prayer.nameEn == nextPrayerName
            )
        }
    }
}