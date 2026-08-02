package com.example.mytestapplication.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeNavActiveBg
import com.example.mytestapplication.ui.theme.HomeNavInactive
import com.example.mytestapplication.ui.theme.HomeSkeletonBase
import com.example.mytestapplication.ui.theme.HomeSkeletonCard
import com.example.mytestapplication.ui.theme.HomeSkeletonDivider
import com.example.mytestapplication.ui.theme.HomeSkeletonHighlight
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class SkeletonNavType {
    Home,
    Services,
    Bookings,
    Profile,
}

@Composable
fun HomeSkeletonScreen(
    modifier: Modifier = Modifier,
) {
    val shimmerBrush = rememberShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HomeBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "DIVA",
                color = WelcomeGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 14.dp)
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = HomeSkeletonDivider
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            // Search bar skeleton
            SkeletonBlock(
                brush = shimmerBrush,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(14.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Banner skeleton
            SkeletonBlock(
                brush = shimmerBrush,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(18.dp))
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Category circles
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(4) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        SkeletonBlock(
                            brush = shimmerBrush,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        SkeletonBlock(
                            brush = shimmerBrush,
                            modifier = Modifier
                                .width(42.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Service cards
            repeat(3) { index ->
                ServiceCardSkeleton(brush = shimmerBrush)
                if (index < 2) {
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Bottom navigation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(OtpWhite)
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            listOf(
                "Home" to SkeletonNavType.Home,
                "Services" to SkeletonNavType.Services,
                "Bookings" to SkeletonNavType.Bookings,
                "Profile" to SkeletonNavType.Profile,
            ).forEachIndexed { index, (title, type) ->
                val selected = index == 0
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .then(
                                if (selected) {
                                    Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(HomeNavActiveBg)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        SkeletonNavIcon(
                            type = type,
                            tint = if (selected) WelcomeGold else HomeNavInactive
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = title,
                        color = if (selected) HomeTitle else HomeNavInactive,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceCardSkeleton(brush: Brush) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(HomeSkeletonCard)
            .padding(12.dp)
    ) {
        SkeletonBlock(
            brush = brush,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(14.dp))
        )
        Spacer(modifier = Modifier.height(12.dp))
        SkeletonBlock(
            brush = brush,
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        SkeletonBlock(
            brush = brush,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(10.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(6.dp))
        SkeletonBlock(
            brush = brush,
            modifier = Modifier
                .fillMaxWidth(0.55f)
                .height(10.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SkeletonBlock(
                brush = brush,
                modifier = Modifier
                    .width(64.dp)
                    .height(18.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.weight(1f))
            SkeletonBlock(
                brush = brush,
                modifier = Modifier
                    .width(88.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}

@Composable
private fun SkeletonBlock(
    brush: Brush,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(brush)
    )
}

@Composable
private fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "home_shimmer")
    val shimmerX by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_x"
    )

    return Brush.linearGradient(
        colors = listOf(
            HomeSkeletonBase,
            HomeSkeletonHighlight,
            HomeSkeletonBase,
        ),
        start = Offset(shimmerX - 200f, 0f),
        end = Offset(shimmerX + 200f, 200f)
    )
}

@Composable
private fun SkeletonNavIcon(
    type: SkeletonNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            SkeletonNavType.Home -> {
                val path = Path().apply {
                    moveTo(size.width * 0.5f, size.height * 0.18f)
                    lineTo(size.width * 0.15f, size.height * 0.48f)
                    lineTo(size.width * 0.15f, size.height * 0.85f)
                    lineTo(size.width * 0.85f, size.height * 0.85f)
                    lineTo(size.width * 0.85f, size.height * 0.48f)
                    close()
                }
                drawPath(path, color = tint, style = stroke)
            }

            SkeletonNavType.Services -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.12f,
                    center = Offset(size.width * 0.5f, size.height * 0.52f)
                )
                for (i in 0 until 5) {
                    val angle = Math.toRadians((-90.0 + i * 72.0))
                    val cx = size.width * 0.5f + (size.minDimension * 0.22f * Math.cos(angle)).toFloat()
                    val cy = size.height * 0.48f + (size.minDimension * 0.22f * Math.sin(angle)).toFloat()
                    drawCircle(
                        color = tint,
                        radius = size.minDimension * 0.14f,
                        center = Offset(cx, cy),
                        style = stroke
                    )
                }
            }

            SkeletonNavType.Bookings -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.18f, size.height * 0.22f),
                    size = Size(size.width * 0.64f, size.height * 0.62f),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.3f, size.height * 0.18f),
                    end = Offset(size.width * 0.3f, size.height * 0.3f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.7f, size.height * 0.18f),
                    end = Offset(size.width * 0.7f, size.height * 0.3f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            SkeletonNavType.Profile -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.16f,
                    center = Offset(size.width / 2f, size.height * 0.32f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.2f, size.height * 0.48f),
                    size = Size(size.width * 0.6f, size.height * 0.45f),
                    style = stroke
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeSkeletonScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        HomeSkeletonScreen()
    }
}
