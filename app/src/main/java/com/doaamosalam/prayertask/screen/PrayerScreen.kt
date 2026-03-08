package com.doaamosalam.prayertask.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.doaamosalam.domain.model.nextprayer.NextPrayer
import com.doaamosalam.domain.model.prayerTime.PrayerTimes
import com.doaamosalam.prayertask.compose.PrayerTimesSection
import com.doaamosalam.prayertask.screen.CountdownRow
import com.doaamosalam.prayertask.screen.PrayerItem
import com.doaamosalam.prayertask.ui.theme.BadgeBg
import com.doaamosalam.prayertask.ui.theme.BgDark
import com.doaamosalam.prayertask.ui.theme.BgNavy
import com.doaamosalam.prayertask.ui.theme.BorderGold
import com.doaamosalam.prayertask.ui.theme.CardBg
import com.doaamosalam.prayertask.ui.theme.GoldLight
import com.doaamosalam.prayertask.ui.theme.GoldPrimary
import com.doaamosalam.prayertask.ui.theme.TextGray
import com.doaamosalam.prayertask.ui.theme.TextWhite
import com.doaamosalam.prayertask.viewModel.PrayerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import com.doaamosalam.prayertask.R
import com.doaamosalam.prayertask.screen.AnimatedMoon
import com.doaamosalam.prayertask.ui.theme.DecorationBlue
import com.doaamosalam.prayertask.ui.theme.DecorationGreen
import com.doaamosalam.prayertask.ui.theme.DecorationOrange
import com.doaamosalam.prayertask.ui.theme.DecorationPink
import com.doaamosalam.prayertask.ui.theme.Natural_White
import kotlin.math.cos
import kotlin.math.sin

// ── Entry Point ───────────────────────────────────────────────────────────────
@Composable
fun PrayerScreen(
    viewModel: PrayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isArabic by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.fetchPrayerTimes("Cairo", "Egypt")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BgDark, BgNavy, BgDark)))
    ) {
        when {
            uiState.isLoading -> LoadingContent()

            uiState.errorMessage != null && uiState.prayerTimes == null -> {
                ErrorContent(
                    message = uiState.errorMessage!!,
                    onRetry = { viewModel.fetchPrayerTimes("Cairo", "Egypt") }
                )
            }

            uiState.prayerTimes != null -> {
                MainContent(
                    prayerTimes = uiState.prayerTimes!!,
                    nextPrayer = uiState.nextPrayer,
                    isArabic = isArabic,
                    onLanguageToggle = { isArabic = !isArabic }
                )
            }
        }
    }
}

// ── Main Content ──────────────────────────────────────────────────────────────
@Composable
private fun MainContent(
    prayerTimes: PrayerTimes,
    nextPrayer: NextPrayer?,
    isArabic: Boolean,
    onLanguageToggle: () -> Unit
) {
    val prayers = remember(prayerTimes) { buildPrayerList(prayerTimes) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(52.dp))
        Header(isArabic = isArabic, onLanguageToggle = onLanguageToggle)
        Spacer(Modifier.height(20.dp))
        nextPrayer?.let { NextPrayerCard(nextPrayer = it, isArabic = isArabic) }
        Spacer(Modifier.height(28.dp))
        PrayerTimesSection(
            prayers = prayers,
            nextPrayerName = nextPrayer?.name,
            isArabic = isArabic
        )
        Spacer(Modifier.height(40.dp))
    }
}

// ── Header ────────────────────────────────────────────────────────────────────
@Composable
private fun Header(isArabic: Boolean, onLanguageToggle: () -> Unit) {
    val today = remember {
        SimpleDateFormat("EEE, MMM d", Locale.ENGLISH).format(Date())
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Language Toggle - left
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(CardBg)
                .border(1.dp, GoldPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                .padding(2.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (!isArabic) GoldPrimary else Color.Transparent)
                        .clickable { if (isArabic) onLanguageToggle() }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "EN",
                        color = if (!isArabic) BgDark else TextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isArabic) GoldPrimary else Color.Transparent)
                        .clickable { if (!isArabic) onLanguageToggle() }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "AR",
                        color = if (isArabic) BgDark else TextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Title - right
        Text(
            text = if (isArabic) "مواقيت الصلاة" else "Prayer Times",
            color = GoldLight,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }

    Spacer(Modifier.height(10.dp))
//    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//        Text(text = "🌙", fontSize = 52.sp)
//    }
    AnimatedMoon()
}

//

// ── Next Prayer Card ──────────────────────────────────────────────────────────
@Composable
private fun NextPrayerCard(nextPrayer: NextPrayer, isArabic: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(listOf(Color(0xFF221E38), Color(0xFF1A1730)))
            )
            .border(1.dp, GoldPrimary.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
            .padding(vertical = 24.dp, horizontal = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isArabic) stringResource(R.string.next_prayer) else stringResource(R.string.prayer_times_en),
                color = GoldLight.copy(alpha = 0.7f),
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = if (isArabic) nextPrayer.nameArabic else nextPrayer.name,
                color = GoldLight,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = formatTo12Hour(nextPrayer.time),
                color = TextWhite,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(20.dp))

            CountdownRow(remainingSeconds = nextPrayer.remainingSeconds)
        }
    }
}

// ── Prayer Row ────────────────────────────────────────────────────────────────
@Composable
fun PrayerRow(prayer: PrayerItem, isNext: Boolean, isArabic: Boolean) {
    val bg = if (isNext) Color(0xFF22203A) else CardBg
    val border = if (isNext) BorderGold else Color.Transparent
    val timeColor = if (isNext) GoldLight else TextWhite

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: time
            Column {
                if (isNext) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(BadgeBg)
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (isArabic) stringResource(R.string.next) else "Next",
                            color = BgDark,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.height(3.dp))
                }

                val formatted = formatTo12Hour(prayer.time)
                val parts = formatted.split(" ")
                Text(
                    text = parts.getOrElse(0) { formatted },
                    color = timeColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = parts.getOrElse(1) { "" },
                    color = TextGray,
                    fontSize = 11.sp
                )
            }

            // Right: icon + name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (isArabic) prayer.nameAr else prayer.nameEn,
                        color = timeColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = if (isArabic) prayer.nameEn else prayer.nameAr,
                        color = TextGray,
                        fontSize = 11.sp,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (isNext) GoldPrimary.copy(alpha = 0.15f) else BgDark
                        )
                ) {
                    Text(text = prayer.icon, fontSize = 22.sp)
                }
            }
        }
    }
}

// ── Loading ───────────────────────────────────────────────────────────────────
@Composable
private fun LoadingContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = GoldLight, strokeWidth = 3.dp)
            Spacer(Modifier.height(14.dp))
            Text("جاري التحميل...", color = TextGray, fontSize = 14.sp)
        }
    }
}

// ── Error ─────────────────────────────────────────────────────────────────────
@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("⚠️", fontSize = 48.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                "حدث خطأ ما", color = TextWhite, fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(message, color = TextGray, fontSize = 12.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onRetry,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
            ) {
                Text("إعادة المحاولة", color = BgDark, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

private fun buildPrayerList(pt: PrayerTimes) = listOf(
    PrayerItem("Fajr", "الفجر", pt.fajr, "🌅"),
    PrayerItem("Dhuhr", "الظهر", pt.dhuhr, "☀️"),
    PrayerItem("Asr", "العصر", pt.asr, "🌤"),
    PrayerItem("Maghrib", "المغرب", pt.maghrib, "🌇"),
    PrayerItem("Isha", "العشاء", pt.isha, "🌙")
)

private fun formatTo12Hour(time24: String): String {
    return runCatching {
        val parts = time24.take(5).split(":")
        val h = parts[0].toInt()
        val m = parts[1].toInt()
        val amPm = if (h < 12) "AM" else "PM"
        val h12 = when {
            h == 0 -> 12; h > 12 -> h - 12; else -> h
        }
        String.format("%02d:%02d %s", h12, m, amPm)
    }.getOrDefault(time24)
}


@Preview(showBackground = true)
@Composable
fun PrayerScreenPreview() {
    MainContent(
        prayerTimes = PrayerTimes(
            fajr = "04:47",
            dhuhr = "12:05",
            asr = "15:26",
            maghrib = "17:58",
            isha = "19:15",
            date = "2024-06-01",
            hijriDate = "1445-11-12",
            timezone = "Africa/Cairo"
        ),
        nextPrayer = NextPrayer(
            name = "Dhuhr",
            nameArabic = "الظهر",
            time = "12:05",
            remainingSeconds = 3600
        ),
        isArabic = true,
        onLanguageToggle = {}
    )
}