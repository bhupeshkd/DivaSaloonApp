package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.ScheduleAccent
import com.example.mytestapplication.ui.theme.ScheduleBg
import com.example.mytestapplication.ui.theme.ScheduleBody
import com.example.mytestapplication.ui.theme.ScheduleBorder
import com.example.mytestapplication.ui.theme.ScheduleCard
import com.example.mytestapplication.ui.theme.ScheduleChipBg
import com.example.mytestapplication.ui.theme.ScheduleNavInactive
import com.example.mytestapplication.ui.theme.ScheduleStar
import com.example.mytestapplication.ui.theme.ScheduleTitle
import com.example.mytestapplication.ui.theme.WelcomeGold

private data class ScheduleDate(
    val weekday: String,
    val day: String,
)

private enum class ScheduleNavType {
    Explore,
    Bookings,
    Inbox,
    Profile,
}

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onViewAllProfessionals: () -> Unit = {},
    onContinueToPayment: (dateLabel: String, timeLabel: String) -> Unit = { _, _ -> },
    onNavClick: (String) -> Unit = {},
) {
    val dates = remember {
        listOf(
            ScheduleDate("MON", "12"),
            ScheduleDate("TUE", "13"),
            ScheduleDate("WED", "14"),
            ScheduleDate("THU", "15"),
            ScheduleDate("FRI", "16"),
            ScheduleDate("SAT", "17"),
        )
    }
    val morningSlots = remember { listOf("09:00 AM", "10:00 AM", "11:30 AM") }
    val afternoonSlots = remember { listOf("01:00 PM", "02:30 PM", "04:00 PM") }

    var selectedDateIndex by remember { mutableIntStateOf(2) }
    var selectedTime by remember { mutableStateOf("02:30 PM") }
    var professionalSelected by remember { mutableStateOf(true) }
    var selectedNav by remember { mutableIntStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScheduleBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onMenuClick)
                ) {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        val y = listOf(0.25f, 0.5f, 0.75f)
                        y.forEach {
                            drawLine(
                                color = ScheduleTitle,
                                start = Offset(size.width * 0.15f, size.height * it),
                                end = Offset(size.width * 0.85f, size.height * it),
                                strokeWidth = 1.8.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
                Text(
                    text = "DIVA",
                    color = WelcomeGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.pro_sarah),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onProfileClick)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Schedule",
                color = ScheduleTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Select a date and time for your appointment.",
                color = ScheduleBody,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "October 2023",
                    color = ScheduleTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onCalendarClick)
                ) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
                        drawRoundRect(
                            color = ScheduleAccent,
                            topLeft = Offset(size.width * 0.15f, size.height * 0.2f),
                            size = Size(size.width * 0.7f, size.height * 0.65f),
                            cornerRadius = CornerRadius(2.dp.toPx()),
                            style = stroke
                        )
                        drawLine(
                            color = ScheduleAccent,
                            start = Offset(size.width * 0.3f, size.height * 0.12f),
                            end = Offset(size.width * 0.3f, size.height * 0.3f),
                            strokeWidth = 1.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = ScheduleAccent,
                            start = Offset(size.width * 0.7f, size.height * 0.12f),
                            end = Offset(size.width * 0.7f, size.height * 0.3f),
                            strokeWidth = 1.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                dates.forEachIndexed { index, date ->
                    DateCard(
                        date = date,
                        selected = selectedDateIndex == index,
                        onClick = { selectedDateIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TimeSectionHeader(title = "Morning", isSun = true)
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                morningSlots.forEach { slot ->
                    TimeChip(
                        label = slot,
                        selected = selectedTime == slot,
                        onClick = { selectedTime = slot },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            TimeSectionHeader(title = "Afternoon", isSun = true)
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                afternoonSlots.forEach { slot ->
                    TimeChip(
                        label = slot,
                        selected = selectedTime == slot,
                        onClick = { selectedTime = slot },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Select Professional",
                    color = ScheduleTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "VIEW ALL",
                    color = ScheduleAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.clickable(onClick = onViewAllProfessionals)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ProfessionalCard(
                selected = professionalSelected,
                onClick = { professionalSelected = true }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val date = dates[selectedDateIndex]
                    onContinueToPayment("Oct ${date.day}", selectedTime)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ScheduleAccent,
                    contentColor = OtpWhite
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    text = "Continue to Payment",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Canvas(modifier = Modifier.size(16.dp)) {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                    val path = Path().apply {
                        moveTo(size.width * 0.2f, size.height * 0.5f)
                        lineTo(size.width * 0.78f, size.height * 0.5f)
                        moveTo(size.width * 0.55f, size.height * 0.28f)
                        lineTo(size.width * 0.78f, size.height * 0.5f)
                        lineTo(size.width * 0.55f, size.height * 0.72f)
                    }
                    drawPath(path, color = OtpWhite, style = stroke)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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
            listOf(
                "Explore" to ScheduleNavType.Explore,
                "Bookings" to ScheduleNavType.Bookings,
                "Inbox" to ScheduleNavType.Inbox,
                "Profile" to ScheduleNavType.Profile,
            ).forEachIndexed { index, (title, type) ->
                val selected = selectedNav == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            selectedNav = index
                            onNavClick(title)
                        }
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .then(
                                if (selected) {
                                    Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(ScheduleAccent)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        ScheduleNavIcon(
                            type = type,
                            tint = if (selected) OtpWhite else ScheduleNavInactive
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = title,
                        color = if (selected) ScheduleTitle else ScheduleNavInactive,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun DateCard(
    date: ScheduleDate,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(58.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) ScheduleAccent else ScheduleCard)
            .then(
                if (!selected) {
                    Modifier.border(1.dp, ScheduleChipBg, RoundedCornerShape(14.dp))
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = date.weekday,
            color = if (selected) OtpWhite.copy(alpha = 0.85f) else ScheduleBody,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.4.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = date.day,
            color = if (selected) OtpWhite else ScheduleTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TimeSectionHeader(
    title: String,
    isSun: Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(16.dp)) {
            if (isSun) {
                val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
                drawCircle(
                    color = ScheduleAccent,
                    radius = size.minDimension * 0.22f,
                    center = Offset(size.width / 2f, size.height / 2f)
                )
                for (i in 0 until 8) {
                    val angle = Math.toRadians(i * 45.0)
                    val cos = Math.cos(angle).toFloat()
                    val sin = Math.sin(angle).toFloat()
                    drawLine(
                        color = ScheduleAccent,
                        start = Offset(
                            size.width / 2f + cos * size.minDimension * 0.32f,
                            size.height / 2f + sin * size.minDimension * 0.32f
                        ),
                        end = Offset(
                            size.width / 2f + cos * size.minDimension * 0.45f,
                            size.height / 2f + sin * size.minDimension * 0.45f
                        ),
                        strokeWidth = 1.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = ScheduleTitle,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun TimeChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) ScheduleAccent else ScheduleChipBg)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = label,
            color = if (selected) OtpWhite else ScheduleTitle,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProfessionalCard(
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.5.dp,
                color = if (selected) WelcomeGold else ScheduleBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .background(ScheduleCard)
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        if (selected) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(22.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(WelcomeGold)
            ) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    val stroke = Stroke(
                        width = 1.8.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                    val path = Path().apply {
                        moveTo(size.width * 0.15f, size.height * 0.52f)
                        lineTo(size.width * 0.4f, size.height * 0.72f)
                        lineTo(size.width * 0.85f, size.height * 0.28f)
                    }
                    drawPath(path, color = OtpWhite, style = stroke)
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.pro_elena),
                contentDescription = "Elena Rostova",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Elena Rostova",
                    color = ScheduleTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Master Stylist",
                    color = ScheduleBody,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "★ 4.9 (124 reviews)",
                    color = ScheduleStar,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ScheduleNavIcon(
    type: ScheduleNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            ScheduleNavType.Explore -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.28f,
                    center = Offset(size.width * 0.42f, size.height * 0.42f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.58f, size.height * 0.58f),
                    end = Offset(size.width * 0.82f, size.height * 0.82f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            ScheduleNavType.Bookings -> {
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

            ScheduleNavType.Inbox -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.12f, size.height * 0.22f),
                    size = Size(size.width * 0.76f, size.height * 0.5f),
                    cornerRadius = CornerRadius(3.dp.toPx()),
                    style = stroke
                )
                val path = Path().apply {
                    moveTo(size.width * 0.12f, size.height * 0.28f)
                    lineTo(size.width * 0.5f, size.height * 0.52f)
                    lineTo(size.width * 0.88f, size.height * 0.28f)
                }
                drawPath(path, color = tint, style = stroke)
            }

            ScheduleNavType.Profile -> {
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
private fun ScheduleScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        ScheduleScreen()
    }
}
