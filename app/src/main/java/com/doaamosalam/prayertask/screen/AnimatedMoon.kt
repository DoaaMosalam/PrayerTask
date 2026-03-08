package com.doaamosalam.prayertask.screen

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doaamosalam.prayertask.ui.theme.BadgeBg
import com.doaamosalam.prayertask.ui.theme.BorderGold
import com.doaamosalam.prayertask.ui.theme.DecorationBlue
import com.doaamosalam.prayertask.ui.theme.DecorationGreen
import com.doaamosalam.prayertask.ui.theme.DecorationOrange
import com.doaamosalam.prayertask.ui.theme.DecorationPink
import com.doaamosalam.prayertask.ui.theme.GoldLight
import com.doaamosalam.prayertask.ui.theme.GoldPrimary
import com.doaamosalam.prayertask.ui.theme.Natural_White
import kotlin.math.cos
import kotlin.math.sin

@Composable
 fun AnimatedMoon() {
    val infiniteTransition = rememberInfiniteTransition(label = "deco")

    val moonRotation by infiniteTransition.animateFloat(
        initialValue = -5f, targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "moon"
    )

    // أنوار تبرق
    val glow1 by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "g1"
    )
    val glow2 by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, delayMillis = 400, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "g2"
    )
    val glow3 by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, delayMillis = 200, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "g3"
    )
    val glow4 by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, delayMillis = 600, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ), label = "g4"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        contentAlignment = Alignment.Center
    ) {

        // ──  background  — Canvas ─────────────────────────────────────────
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            // ── circle ─────────────────────────────
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFD4AF37).copy(alpha = 0.25f),
                        Color.Transparent
                    ),
                    center = Offset(cx, cy),
                    radius = 130f
                ),
                radius = 130f,
                center = Offset(cx, cy)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFD060).copy(alpha = 0.15f),
                        Color.Transparent
                    ),
                    center = Offset(cx, cy),
                    radius = 80f
                ),
                radius = 80f,
                center = Offset(cx, cy)
            )

            // ── triangle  ─────────────────────────────────────────────────
            data class Tri(val x: Float, val y: Float, val size: Float, val color: Color)

            val triangles = listOf(

                Tri(cx - 90f, cy - 30f, 18f, DecorationPink),
                Tri(cx - 60f, cy + 25f, 14f, DecorationBlue),
                Tri(cx + 70f, cy - 20f, 16f, DecorationOrange),
                Tri(cx + 95f, cy + 20f, 12f, DecorationGreen),
                Tri(cx - 110f, cy + 15f, 10f, GoldPrimary),
                Tri(cx + 110f, cy - 10f, 11f, DecorationPink),
            )

            triangles.forEach { tri ->
                val path = Path().apply {
                    moveTo(tri.x, tri.y - tri.size)
                    lineTo(tri.x - tri.size, tri.y + tri.size)
                    lineTo(tri.x + tri.size, tri.y + tri.size)
                    close()
                }
                drawPath(path, color = tri.color.copy(alpha = 0.85f))
                drawPath(path, color = Color.White.copy(alpha = 0.2f), style = Stroke(1f))
            }

            // ── نجوم ────────────────────────────────────────────────────
            data class Star(val x: Float, val y: Float, val r: Float, val color: Color)

            val stars = listOf(
                Star(cx - 75f, cy - 45f, 6f, GoldLight),
                Star(cx + 80f, cy - 50f, 5f, Natural_White),
                Star(cx - 100f, cy + 35f, 4f, GoldLight),
                Star(cx + 105f, cy + 30f, 5f, Natural_White),
                Star(cx - 40f, cy - 55f, 3f, GoldPrimary),
                Star(cx + 45f, cy + 45f, 4f, GoldLight),
                Star(cx + 30f, cy - 50f, 3f, Natural_White),
                Star(cx - 120f, cy - 10f, 3f, BadgeBg),
            )

            stars.forEach { star ->
                // نجمة 4 أذرع
                for (angle in listOf(0f, 90f, 45f, 135f)) {
                    val rad = Math.toRadians(angle.toDouble())
                    drawLine(
                        color = star.color.copy(alpha = 0.9f),
                        start = Offset(
                            star.x - (star.r * cos(rad)).toFloat(),
                            star.y - (star.r * sin(rad)).toFloat()
                        ),
                        end = Offset(
                            star.x + (star.r * cos(rad)).toFloat(),
                            star.y + (star.r * sin(rad)).toFloat()
                        ),
                        strokeWidth = 1.5f
                    )
                }

                drawCircle(
                    color = star.color,
                    radius = 2f,
                    center = Offset(star.x, star.y)
                )
            }
        }

        // ── Light Canvas ──────────────────────────────────


        Box(
            modifier = Modifier
                .size(8.dp)
                .offset(x = (-75).dp, y = (-45).dp)
                .clip(CircleShape)
                .background(GoldLight.copy(alpha = glow1))
        )

        Box(
            modifier = Modifier
                .size(6.dp)
                .offset(x = 80.dp, y = (-50).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = glow2))
        )

        Box(
            modifier = Modifier
                .size(7.dp)
                .offset(x = (-100).dp, y = 35.dp)
                .clip(CircleShape)
                .background(BorderGold.copy(alpha = glow3))
        )
        Box(
            modifier = Modifier
                .size(6.dp)
                .offset(x = 105.dp, y = 30.dp)
                .clip(CircleShape)
                .background(GoldLight.copy(alpha = glow4))
        )

        Box(
            modifier = Modifier
                .size(5.dp)
                .offset(x = (-40).dp, y = (-55).dp)
                .clip(CircleShape)
                .background(Natural_White.copy(alpha = glow2))
        )

        Box(
            modifier = Modifier
                .size(5.dp)
                .offset(x = 45.dp, y = 45.dp)
                .clip(CircleShape)
                .background(DecorationPink.copy(alpha = glow1))
        )

        // ──  ───────────────────────────────────────────
        Text(
            text = "🌙",
            fontSize = 58.sp,
            modifier = Modifier.graphicsLayer { rotationZ = moonRotation }
        )
    }
}