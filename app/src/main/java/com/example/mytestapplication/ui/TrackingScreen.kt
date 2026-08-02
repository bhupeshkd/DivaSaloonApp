package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.TrackingAccent
import com.example.mytestapplication.ui.theme.TrackingBg
import com.example.mytestapplication.ui.theme.TrackingBody
import com.example.mytestapplication.ui.theme.TrackingCard
import com.example.mytestapplication.ui.theme.TrackingInactive
import com.example.mytestapplication.ui.theme.TrackingLive
import com.example.mytestapplication.ui.theme.TrackingLiveSoft
import com.example.mytestapplication.ui.theme.TrackingMapBg
import com.example.mytestapplication.ui.theme.TrackingRoute
import com.example.mytestapplication.ui.theme.TrackingSoft
import com.example.mytestapplication.ui.theme.TrackingStar
import com.example.mytestapplication.ui.theme.TrackingTitle
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class TimelineIcon {
    Check,
    Person,
    Car,
    Service,
    Done,
}

private data class TimelineStep(
    val title: String,
    val subtitle: String,
    val time: String,
    val icon: TimelineIcon,
    val done: Boolean,
    val active: Boolean = false,
)

@Composable
fun TrackingScreen(
    bookingId: String = "#8492",
    professionalName: String = "Elena R.",
    etaMinutes: Int = 14,
    serviceName: String = "Signature Swedish Massage",
    serviceDuration: String = "90 Minutes",
    dateTime: String = "Oct 24, 10:00 AM",
    location: String = "Home",
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onDownloadInvoice: () -> Unit = {},
    onRateServiceClick: () -> Unit = {},
) {
    val steps = listOf(
        TimelineStep(
            title = "Booking Confirmed",
            subtitle = "Your reservation was received.",
            time = "09:00 AM",
            icon = TimelineIcon.Check,
            done = true
        ),
        TimelineStep(
            title = "Professional Assigned",
            subtitle = "$professionalName will be your therapist.",
            time = "09:15 AM",
            icon = TimelineIcon.Person,
            done = true
        ),
        TimelineStep(
            title = "On the Way",
            subtitle = "Arriving in approx. $etaMinutes mins.",
            time = "Now",
            icon = TimelineIcon.Car,
            done = false,
            active = true
        ),
        TimelineStep(
            title = "Service Started",
            subtitle = "Relax and enjoy.",
            time = "--:--",
            icon = TimelineIcon.Service,
            done = false
        ),
        TimelineStep(
            title = "Completed",
            subtitle = "Please leave a review.",
            time = "--:--",
            icon = TimelineIcon.Done,
            done = false
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TrackingBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(TrackingCard)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBackClick)
                    .align(Alignment.CenterStart)
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                    val path = Path().apply {
                        moveTo(size.width * 0.58f, size.height * 0.18f)
                        lineTo(size.width * 0.28f, size.height * 0.5f)
                        lineTo(size.width * 0.58f, size.height * 0.82f)
                    }
                    drawPath(path, color = TrackingTitle, style = stroke)
                }
            }
            Text(
                text = "Booking $bookingId",
                color = WelcomeGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Live tracking card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = Color.Black.copy(alpha = 0.08f)
                    )
                    .clip(RoundedCornerShape(18.dp))
                    .background(TrackingCard)
            ) {
                // Decorative corner
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 20.dp, y = (-20).dp)
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(TrackingSoft.copy(alpha = 0.7f))
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(TrackingLive)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LIVE TRACKING",
                            color = TrackingLive,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Elena is on the way",
                        color = TrackingTitle,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Estimated arrival: $etaMinutes mins",
                        color = TrackingBody,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(TrackingSoft)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pro_elena),
                            contentDescription = professionalName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = professionalName,
                                color = TrackingTitle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "★ 4.9 (128 reviews)",
                                color = TrackingStar,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(TrackingAccent)
                                .clickable(onClick = onMessageClick)
                        ) {
                            MessageIcon(OtpWhite)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8E6E2))
                                .clickable(onClick = onCallClick)
                        ) {
                            PhoneIcon(TrackingTitle)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Map
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(TrackingMapBg)
            ) {
                TrackingMapCanvas()
                Image(
                    painter = painterResource(id = R.drawable.pro_elena),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 36.dp, y = (-18).dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(OtpWhite)
                        .padding(2.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Status",
                color = TrackingTitle,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(14.dp))

            steps.forEachIndexed { index, step ->
                TimelineRow(
                    step = step,
                    isLast = index == steps.lastIndex,
                    onClick = {
                        if (step.icon == TimelineIcon.Done) {
                            onRateServiceClick()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Booking Details",
                    color = TrackingTitle,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = bookingId,
                    color = TrackingBody,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(label = "Service", value = serviceName, subValue = serviceDuration)
            Spacer(modifier = Modifier.height(10.dp))
            DetailRow(label = "Date & Time", value = dateTime)
            Spacer(modifier = Modifier.height(10.dp))
            DetailRow(label = "Location", value = location)

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = onDownloadInvoice)
            ) {
                Canvas(modifier = Modifier.size(14.dp)) {
                    val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
                    drawRoundRect(
                        color = WelcomeGold,
                        topLeft = Offset(size.width * 0.22f, size.height * 0.12f),
                        size = Size(size.width * 0.56f, size.height * 0.76f),
                        cornerRadius = CornerRadius(2.dp.toPx()),
                        style = stroke
                    )
                    drawLine(
                        color = WelcomeGold,
                        start = Offset(size.width * 0.35f, size.height * 0.4f),
                        end = Offset(size.width * 0.65f, size.height * 0.4f),
                        strokeWidth = 1.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = WelcomeGold,
                        start = Offset(size.width * 0.35f, size.height * 0.58f),
                        end = Offset(size.width * 0.65f, size.height * 0.58f),
                        strokeWidth = 1.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Download Invoice",
                    color = WelcomeGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    subValue: String? = null,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = TrackingBody,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = value,
                color = TrackingTitle,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End
            )
            if (subValue != null) {
                Text(
                    text = subValue,
                    color = TrackingBody,
                    fontSize = 11.sp,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
private fun TimelineRow(
    step: TimelineStep,
    isLast: Boolean,
    onClick: () -> Unit = {},
) {
    val titleColor = if (step.done || step.active) TrackingTitle else TrackingBody
    val bodyColor = if (step.done || step.active) TrackingBody else TrackingInactive

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            step.active -> TrackingLive
                            step.done -> TrackingAccent
                            else -> TrackingInactive.copy(alpha = 0.45f)
                        }
                    )
            ) {
                TimelineGlyph(step.icon, OtpWhite)
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(42.dp)
                        .background(
                            if (step.done) TrackingAccent.copy(alpha = 0.35f)
                            else TrackingInactive.copy(alpha = 0.5f)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 8.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = step.title,
                        color = titleColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = step.subtitle,
                        color = bodyColor,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = step.time,
                    color = if (step.active) TrackingLive else bodyColor,
                    fontSize = 12.sp,
                    fontWeight = if (step.active) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun TimelineGlyph(
    icon: TimelineIcon,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(14.dp)) {
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (icon) {
            TimelineIcon.Check -> {
                val path = Path().apply {
                    moveTo(size.width * 0.18f, size.height * 0.52f)
                    lineTo(size.width * 0.42f, size.height * 0.72f)
                    lineTo(size.width * 0.82f, size.height * 0.28f)
                }
                drawPath(path, color = tint, style = stroke)
            }

            TimelineIcon.Person -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.16f,
                    center = Offset(size.width / 2f, size.height * 0.3f)
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.18f, size.height * 0.48f),
                    size = Size(size.width * 0.64f, size.height * 0.45f),
                    style = stroke
                )
            }

            TimelineIcon.Car -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.12f, size.height * 0.38f),
                    size = Size(size.width * 0.76f, size.height * 0.32f),
                    cornerRadius = CornerRadius(2.dp.toPx())
                )
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.28f, size.height * 0.22f),
                    size = Size(size.width * 0.44f, size.height * 0.2f),
                    cornerRadius = CornerRadius(2.dp.toPx())
                )
                drawCircle(tint, 1.8.dp.toPx(), Offset(size.width * 0.3f, size.height * 0.78f))
                drawCircle(tint, 1.8.dp.toPx(), Offset(size.width * 0.7f, size.height * 0.78f))
            }

            TimelineIcon.Service -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.28f,
                    center = Offset(size.width / 2f, size.height / 2f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.28f),
                    end = Offset(size.width * 0.5f, size.height * 0.55f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            TimelineIcon.Done -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.28f,
                    center = Offset(size.width / 2f, size.height / 2f),
                    style = stroke
                )
                val path = Path().apply {
                    moveTo(size.width * 0.3f, size.height * 0.5f)
                    lineTo(size.width * 0.45f, size.height * 0.65f)
                    lineTo(size.width * 0.72f, size.height * 0.35f)
                }
                drawPath(path, color = tint, style = stroke)
            }
        }
    }
}

@Composable
private fun TrackingMapCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val street = Color(0xFFD8D4CE)
        for (i in 1..4) {
            drawLine(street, Offset(0f, size.height * (i / 5f)), Offset(size.width, size.height * (i / 5f)), 2.5.dp.toPx())
        }
        for (i in 1..5) {
            drawLine(street, Offset(size.width * (i / 6f), 0f), Offset(size.width * (i / 6f), size.height), 2.5.dp.toPx())
        }
        drawRect(
            color = Color.White.copy(alpha = 0.3f),
            topLeft = Offset(size.width * 0.1f, size.height * 0.15f),
            size = Size(size.width * 0.22f, size.height * 0.28f)
        )

        val route = Path().apply {
            moveTo(size.width * 0.22f, size.height * 0.78f)
            cubicTo(
                size.width * 0.35f, size.height * 0.55f,
                size.width * 0.48f, size.height * 0.7f,
                size.width * 0.58f, size.height * 0.42f
            )
            cubicTo(
                size.width * 0.65f, size.height * 0.28f,
                size.width * 0.72f, size.height * 0.32f,
                size.width * 0.78f, size.height * 0.22f
            )
        }
        drawPath(
            path = route,
            color = TrackingRoute,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )
        // Destination pin
        drawCircle(
            color = TrackingAccent,
            radius = 6.dp.toPx(),
            center = Offset(size.width * 0.22f, size.height * 0.78f)
        )
        drawCircle(
            color = OtpWhite,
            radius = 3.dp.toPx(),
            center = Offset(size.width * 0.22f, size.height * 0.78f)
        )
    }
}

@Composable
private fun MessageIcon(tint: Color) {
    Canvas(modifier = Modifier.size(16.dp)) {
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(
            color = tint,
            topLeft = Offset(size.width * 0.12f, size.height * 0.18f),
            size = Size(size.width * 0.76f, size.height * 0.52f),
            cornerRadius = CornerRadius(3.dp.toPx()),
            style = stroke
        )
        val path = Path().apply {
            moveTo(size.width * 0.28f, size.height * 0.68f)
            lineTo(size.width * 0.28f, size.height * 0.88f)
            lineTo(size.width * 0.48f, size.height * 0.68f)
        }
        drawPath(path, color = tint, style = stroke)
    }
}

@Composable
private fun PhoneIcon(tint: Color) {
    Canvas(modifier = Modifier.size(16.dp)) {
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        val path = Path().apply {
            moveTo(size.width * 0.3f, size.height * 0.22f)
            lineTo(size.width * 0.42f, size.height * 0.18f)
            lineTo(size.width * 0.5f, size.height * 0.35f)
            lineTo(size.width * 0.4f, size.height * 0.42f)
            quadraticTo(
                size.width * 0.55f, size.height * 0.62f,
                size.width * 0.72f, size.height * 0.7f
            )
            lineTo(size.width * 0.78f, size.height * 0.58f)
            lineTo(size.width * 0.92f, size.height * 0.68f)
            lineTo(size.width * 0.82f, size.height * 0.82f)
            quadraticTo(
                size.width * 0.45f, size.height * 0.78f,
                size.width * 0.3f, size.height * 0.22f
            )
        }
        drawPath(path, color = tint, style = stroke)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TrackingScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        TrackingScreen()
    }
}
