package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.R
import com.example.mytestapplication.ui.theme.HomeAccent
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeNavActiveBg
import com.example.mytestapplication.ui.theme.HomeNavInactive
import com.example.mytestapplication.ui.theme.HomeSkeletonDivider
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class ErrorNavType {
    Home,
    Services,
    Bookings,
    Profile,
}

@Composable
fun ConnectionErrorScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    onOfflineBookingsClick: () -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    var selectedNav by remember { mutableIntStateOf(0) }

    val navItems = remember {
        listOf(
            "Home" to ErrorNavType.Home,
            "Services" to ErrorNavType.Services,
            "Bookings" to ErrorNavType.Bookings,
            "Profile" to ErrorNavType.Profile,
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HomeBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.weight(1f)
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round)
                    drawCircle(
                        color = WelcomeGold,
                        radius = size.minDimension * 0.18f,
                        center = Offset(size.width / 2f, size.height * 0.36f)
                    )
                    val pin = Path().apply {
                        moveTo(size.width * 0.5f, size.height * 0.92f)
                        quadraticTo(
                            size.width * 0.1f,
                            size.height * 0.55f,
                            size.width * 0.5f,
                            size.height * 0.12f
                        )
                        quadraticTo(
                            size.width * 0.9f,
                            size.height * 0.55f,
                            size.width * 0.5f,
                            size.height * 0.92f
                        )
                        close()
                    }
                    drawPath(pin, color = WelcomeGold, style = stroke)
                }
            }

            Text(
                text = "DIVA",
                color = WelcomeGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onNotificationClick)
                ) {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round)
                        drawPath(
                            path = Path().apply {
                                moveTo(size.width * 0.2f, size.height * 0.42f)
                                quadraticTo(
                                    size.width * 0.2f,
                                    size.height * 0.18f,
                                    size.width * 0.5f,
                                    size.height * 0.18f
                                )
                                quadraticTo(
                                    size.width * 0.8f,
                                    size.height * 0.18f,
                                    size.width * 0.8f,
                                    size.height * 0.42f
                                )
                                lineTo(size.width * 0.8f, size.height * 0.62f)
                                lineTo(size.width * 0.92f, size.height * 0.72f)
                                lineTo(size.width * 0.08f, size.height * 0.72f)
                                lineTo(size.width * 0.2f, size.height * 0.62f)
                                close()
                            },
                            color = WelcomeGold,
                            style = stroke
                        )
                        drawArc(
                            color = WelcomeGold,
                            startAngle = 20f,
                            sweepAngle = 140f,
                            useCenter = false,
                            topLeft = Offset(size.width * 0.35f, size.height * 0.72f),
                            size = Size(size.width * 0.3f, size.height * 0.2f),
                            style = stroke
                        )
                    }
                }
            }
        }

        HorizontalDivider(thickness = 1.dp, color = HomeSkeletonDivider)

        // Center content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(168.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_promo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.45f))
                )
                NoWifiIcon(
                    tint = WelcomeGold,
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Oops, connection lost.",
                color = HomeTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Something went wrong while fetching the latest offers. Please check your connection and try again.",
                color = HomeSubtitle,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onRetryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeAccent,
                    contentColor = OtpWhite
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                    drawArc(
                        color = OtpWhite,
                        startAngle = 40f,
                        sweepAngle = 280f,
                        useCenter = false,
                        topLeft = Offset(size.width * 0.12f, size.height * 0.12f),
                        size = Size(size.width * 0.76f, size.height * 0.76f),
                        style = stroke
                    )
                    val tip = Path().apply {
                        moveTo(size.width * 0.72f, size.height * 0.08f)
                        lineTo(size.width * 0.88f, size.height * 0.28f)
                        lineTo(size.width * 0.62f, size.height * 0.3f)
                        close()
                    }
                    drawPath(tip, color = OtpWhite)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Retry",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Go to Offline Bookings",
                color = WelcomeGold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable(onClick = onOfflineBookingsClick)
            )
        }

        // Bottom nav
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(OtpWhite)
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            navItems.forEachIndexed { index, (title, type) ->
                val selected = selectedNav == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            selectedNav = index
                            onNavClick(title)
                        }
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .then(
                                if (selected) {
                                    Modifier
                                        .clip(CircleShape)
                                        .background(HomeNavActiveBg)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        ErrorNavIcon(
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
private fun NoWifiIcon(
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val stroke = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        val cx = size.width / 2f
        val cy = size.height * 0.58f

        // WiFi arcs
        drawArc(
            color = tint,
            startAngle = 210f,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = Offset(cx - size.width * 0.38f, cy - size.height * 0.38f),
            size = Size(size.width * 0.76f, size.height * 0.76f),
            style = stroke
        )
        drawArc(
            color = tint,
            startAngle = 210f,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = Offset(cx - size.width * 0.26f, cy - size.height * 0.26f),
            size = Size(size.width * 0.52f, size.height * 0.52f),
            style = stroke
        )
        drawArc(
            color = tint,
            startAngle = 210f,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = Offset(cx - size.width * 0.14f, cy - size.height * 0.14f),
            size = Size(size.width * 0.28f, size.height * 0.28f),
            style = stroke
        )
        drawCircle(
            color = tint,
            radius = 5.dp.toPx(),
            center = Offset(cx, cy + size.height * 0.02f)
        )

        // Slash
        drawLine(
            color = tint,
            start = Offset(size.width * 0.22f, size.height * 0.78f),
            end = Offset(size.width * 0.78f, size.height * 0.22f),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun ErrorNavIcon(
    type: ErrorNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            ErrorNavType.Home -> {
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

            ErrorNavType.Services -> {
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

            ErrorNavType.Bookings -> {
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

            ErrorNavType.Profile -> {
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
private fun ConnectionErrorScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        ConnectionErrorScreen()
    }
}
