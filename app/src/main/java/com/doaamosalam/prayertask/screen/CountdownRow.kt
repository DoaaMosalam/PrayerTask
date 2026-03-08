package com.doaamosalam.prayertask.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doaamosalam.prayertask.ui.theme.GoldLight
import com.doaamosalam.prayertask.ui.theme.TextGray
import com.doaamosalam.prayertask.ui.theme.TextWhite


/**
 * Isolated composable — only this recomposes every second.
 * The parent [NextPrayerCard] and the prayer list are NOT recomposed.
 */
// ── Countdown Row ─────────────────────────────────────────────────────────────
@Composable
fun CountdownRow(remainingSeconds: Long) {

    var secondsLeft by remember(remainingSeconds) { mutableLongStateOf(remainingSeconds) }

    LaunchedEffect(remainingSeconds) {
        while (secondsLeft > 0) {
            kotlinx.coroutines.delay(1000)
            secondsLeft--
        }
    }

    val h = secondsLeft / 3600
    val m = (secondsLeft % 3600) / 60
    val s = secondsLeft % 60

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
                .background(androidx.compose.ui.graphics.Color(0xFF0A0A18))
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