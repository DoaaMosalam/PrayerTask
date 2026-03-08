package com.doaamosalam.prayertask.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.doaamosalam.domain.model.PrayerTime.PrayerTimes
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

//// ── Colors ────────────────────────────────────────────────────────────────────
//private val BgDark        = Color(0xFF0A0A18)
//private val BgNavy        = Color(0xFF0F0F20)
//private val CardBg        = Color(0xFF1A1826)
//private val CardNext      = Color(0xFF1E1B30)
//private val GoldPrimary   = Color(0xFFD4AF37)
//private val GoldLight     = Color(0xFFFFD060)
//private val TextWhite     = Color(0xFFEEEEEE)
//private val TextGray      = Color(0xFF7A7A9A)
//private val BorderGold    = Color(0xFFD4AF37)
//private val BadgeBg       = Color(0xFFD4AF37)

// ── Entry Point ───────────────────────────────────────────────────────────────
@Composable
fun PrayerScreen(
    viewModel: PrayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchPrayerTimes("Cairo", "Egypt")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BgDark, BgNavy, BgDark))
            )
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
                MainContent(prayerTimes = uiState.prayerTimes!!)
            }
        }
    }
}

// ── Main Content ──────────────────────────────────────────────────────────────
@Composable
private fun MainContent(prayerTimes: PrayerTimes) {

    // figure out which prayer is next
    val prayers = remember(prayerTimes) { buildPrayerList(prayerTimes) }
    val nextPrayer = remember(prayers) { findNextPrayer(prayers) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(52.dp))

        // ── Header ────────────────────────────────────────────────────────────
        Header()

        Spacer(Modifier.height(20.dp))

        // ── Next Prayer Card ──────────────────────────────────────────────────
        if (nextPrayer != null) {
            NextPrayerCard(prayer = nextPrayer)
        }

        Spacer(Modifier.height(28.dp))

        // ── Section label ─────────────────────────────────────────────────────
        Text(
            text = "PRAYER TIMES",
            color = TextGray,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(start = 2.dp, bottom = 10.dp)
        )

        // ── Prayer list ───────────────────────────────────────────────────────
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            prayers.forEach { prayer ->
                PrayerRow(
                    prayer = prayer,
                    isNext = prayer.nameEn == nextPrayer?.nameEn
                )
            }
        }

        Spacer(Modifier.height(40.dp))
    }
}

// ── Header ────────────────────────────────────────────────────────────────────

@Composable
private fun Header() {
    val today = remember {
        SimpleDateFormat("EEE, MMM d", Locale.ENGLISH).format(Date())
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = today, color = TextGray, fontSize = 13.sp)
        Text(
            text = "مواقيت الصلاة",
            color = GoldLight,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }

    Spacer(Modifier.height(10.dp))

    // Crescent moon
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "🌙", fontSize = 52.sp)
    }
}

// ── Next Prayer Card ──────────────────────────────────────────────────────────
@Composable
private fun NextPrayerCard(prayer: PrayerItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF221E38), Color(0xFF1A1730))
                )
            )
            .border(1.dp, GoldPrimary.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
            .padding(vertical = 24.dp, horizontal = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "الصلاة القادمة",
                color = GoldLight.copy(alpha = 0.7f),
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = prayer.nameAr,
                color = GoldLight,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = formatTo12Hour(prayer.time),
                color = TextWhite,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(20.dp))

            CountdownRow(targetTime = prayer.time)
        }
    }
}

// ── Countdown Row ─────────────────────────────────────────────────────────────
/**
 * Isolated composable — only this recomposes every second.
 * The parent [NextPrayerCard] and the prayer list are NOT recomposed.
 */
@Composable
private fun CountdownRow(targetTime: String) {
    // Tick every second
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(targetTime) {
        while (true) {
            kotlinx.coroutines.delay(1_000L)
            now = System.currentTimeMillis()
        }
    }

    val totalSeconds = remember(now, targetTime) {
        calculateRemainingSeconds(targetTime)
    }

    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TimeBox(value = h, label = "HRS")
        Colon()
        TimeBox(value = m, label = "MIN")
        Colon()
        TimeBox(value = s, label = "SEC")
    }
}

@Composable
private fun TimeBox(value: Long, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(66.dp, 58.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF0A0A18))
        ) {
            Text(
                text = String.format("%02d", value),
                color = TextWhite,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(5.dp))
        Text(text = label, color = TextGray, fontSize = 10.sp, letterSpacing = 1.sp)
    }
}

@Composable
private fun Colon() {
    Text(
        text = ":",
        color = GoldLight,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .offset(y = (-6).dp)
    )
}

// ── Prayer Row ────────────────────────────────────────────────────────────────
@Composable
private fun PrayerRow(prayer: PrayerItem, isNext: Boolean) {
    val bg     = if (isNext) Color(0xFF22203A) else CardBg
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
            // ── Left: time ────────────────────────────────────────────────────
            Column {
                if (isNext) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(BadgeBg)
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "▶ التالية",
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

            // ── Right: icon + name ────────────────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = prayer.nameAr,
                        color = timeColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = prayer.nameEn,
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
                            if (isNext) GoldPrimary.copy(alpha = 0.15f)
                            else BgDark
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
            Text("جارٍ التحميل...", color = TextGray, fontSize = 14.sp)
        }
    }
}

// ── Error ─────────────────────────────────────────────────────────────────────
@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("⚠️", fontSize = 48.sp)
            Spacer(Modifier.height(16.dp))
            Text("تعذّر تحميل مواقيت الصلاة", color = TextWhite, fontSize = 16.sp,
                textAlign = TextAlign.Center)
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
data class PrayerItem(
    val nameEn: String,
    val nameAr: String,
    val time: String,
    val icon: String
)

private fun buildPrayerList(pt: PrayerTimes) = listOf(
    PrayerItem("Fajr",    "الفجر",   pt.fajr,    "🌅"),
    PrayerItem("Dhuhr",   "الظهر",   pt.dhuhr,   "☀️"),
    PrayerItem("Asr",     "العصر",   pt.asr,     "🌤"),
    PrayerItem("Maghrib", "المغرب",  pt.maghrib, "🌇"),
    PrayerItem("Isha",    "العشاء",  pt.isha,    "🌙")
)

private fun findNextPrayer(prayers: List<PrayerItem>): PrayerItem? {
    val cal = java.util.Calendar.getInstance()
    val nowMinutes = cal.get(java.util.Calendar.HOUR_OF_DAY) * 60 +
            cal.get(java.util.Calendar.MINUTE)

    return prayers.firstOrNull { prayer ->
        runCatching {
            val parts = prayer.time.take(5).split(":")
            val prayerMinutes = parts[0].toInt() * 60 + parts[1].toInt()
            prayerMinutes > nowMinutes
        }.getOrDefault(false)
    } ?: prayers.firstOrNull()
}

private fun calculateRemainingSeconds(time24: String): Long {
    return runCatching {
        val cal = java.util.Calendar.getInstance()
        val nowSeconds = cal.get(java.util.Calendar.HOUR_OF_DAY) * 3600 +
                cal.get(java.util.Calendar.MINUTE) * 60 +
                cal.get(java.util.Calendar.SECOND)

        val parts = time24.take(5).split(":")
        val targetSeconds = parts[0].toInt() * 3600 + parts[1].toInt() * 60

        val diff = targetSeconds - nowSeconds
        if (diff < 0) (86400 + diff).toLong() else diff.toLong()
    }.getOrDefault(0L)
}
private fun formatTo12Hour(time24: String): String {
    return runCatching {
        val parts = time24.take(5).split(":")
        val h = parts[0].toInt(); val m = parts[1].toInt()
        val amPm = if (h < 12) "AM" else "PM"
        val h12 = when { h == 0 -> 12; h > 12 -> h - 12; else -> h }
        String.format("%02d:%02d %s", h12, m, amPm)
    }.getOrDefault(time24)
}
@Preview(showBackground = true)
@Composable
fun PrayerScreenPreview() {
    MainContent(
        prayerTimes = PrayerTimes(
            fajr = "05:00",
            dhuhr = "12:30",
            asr = "15:45",
            maghrib = "18:20",
            isha = "19:50",
            date = "01 Jan 2024",
            hijriDate = "17 Jumada I 1445",
            timezone = "Africa/Cairo"
        )
    )
}