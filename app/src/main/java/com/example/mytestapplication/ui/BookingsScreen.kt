package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeNavInactive
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class BookingStatus {
    Upcoming,
    Completed,
    Cancelled,
}

private enum class BookingsNavType {
    Home,
    Bookings,
    Wallet,
    Profile,
}

private data class BookingItem(
    val title: String,
    val location: String,
    val date: String,
    val time: String,
    val status: BookingStatus,
    val imageRes: Int,
)

private data class BookingsNavItem(
    val title: String,
    val type: BookingsNavType,
)

@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    avatarRes: Int = R.drawable.pro_elena,
    onMenuClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onRescheduleClick: (String) -> Unit = {},
    onCancelClick: (String) -> Unit = {},
    onRebookClick: (String) -> Unit = {},
    onReceiptClick: (String) -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    var selectedFilter by remember { mutableIntStateOf(0) }
    val filters = remember { listOf("Upcoming", "Completed", "Cancelled") }

    val allBookings = remember {
        listOf(
            BookingItem(
                title = "Bridal Makeup Trial",
                location = "DIVA Beauty Atelier - SoHo",
                date = "Oct 24, 2024",
                time = "10:00 AM - 12:00 PM",
                status = BookingStatus.Upcoming,
                imageRes = R.drawable.service_bridal,
            ),
            BookingItem(
                title = "Signature Facial",
                location = "Glow Spa - Upper East Side",
                date = "Oct 18, 2024",
                time = "2:00 PM - 3:00 PM",
                status = BookingStatus.Completed,
                imageRes = R.drawable.cat_facial,
            ),
            BookingItem(
                title = "Deep Tissue Massage",
                location = "Serenity Spa - Chelsea",
                date = "Oct 10, 2024",
                time = "11:00 AM - 12:00 PM",
                status = BookingStatus.Cancelled,
                imageRes = R.drawable.cat_massage,
            ),
        )
    }

    val filtered = remember(selectedFilter, allBookings) {
        val status = when (selectedFilter) {
            0 -> BookingStatus.Upcoming
            1 -> BookingStatus.Completed
            else -> BookingStatus.Cancelled
        }
        allBookings.filter { it.status == status }
    }

    // Design mock shows both upcoming + completed under Upcoming; keep that showcase feel
    // when Upcoming is selected by including completed beneath upcoming.
    val displayList = remember(selectedFilter, allBookings, filtered) {
        if (selectedFilter == 0) {
            allBookings.filter {
                it.status == BookingStatus.Upcoming || it.status == BookingStatus.Completed
            }
        } else {
            filtered
        }
    }

    val navItems = remember {
        listOf(
            BookingsNavItem("Home", BookingsNavType.Home),
            BookingsNavItem("Bookings", BookingsNavType.Bookings),
            BookingsNavItem("Wallet", BookingsNavType.Wallet),
            BookingsNavItem("Profile", BookingsNavType.Profile),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HomeBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

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
                        val stroke = 1.8.dp.toPx()
                        listOf(0.28f, 0.5f, 0.72f).forEach { y ->
                            drawLine(
                                color = HomeTitle,
                                start = Offset(size.width * 0.15f, size.height * y),
                                end = Offset(size.width * 0.85f, size.height * y),
                                strokeWidth = stroke,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }

                Text(
                    text = "DIVA",
                    color = WelcomeGold,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, Color(0xFFE5E1DB), CircleShape)
                        .clickable(onClick = onProfileClick)
                ) {
                    Image(
                        painter = painterResource(id = avatarRes),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Bookings",
                color = HomeTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Manage your beauty appointments.",
                color = HomeSubtitle,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                filters.forEachIndexed { index, label ->
                    val selected = selectedFilter == index
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(if (selected) Color(0xFF6B5A3E) else Color(0xFFF0EEEC))
                            .clickable { selectedFilter = index }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = label,
                            color = if (selected) OtpWhite else HomeSubtitle,
                            fontSize = 13.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (displayList.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp)
                ) {
                    Text(
                        text = "No ${filters[selectedFilter].lowercase()} bookings",
                        color = HomeSubtitle,
                        fontSize = 14.sp
                    )
                }
            } else {
                displayList.forEach { booking ->
                    BookingCard(
                        booking = booking,
                        onRescheduleClick = { onRescheduleClick(booking.title) },
                        onCancelClick = { onCancelClick(booking.title) },
                        onRebookClick = { onRebookClick(booking.title) },
                        onReceiptClick = { onReceiptClick(booking.title) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(OtpWhite)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            navItems.forEach { item ->
                val selected = item.type == BookingsNavType.Bookings
                val tint = if (selected) OtpWhite else HomeNavInactive
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) Color(0xFF6B5A3E) else Color.Transparent)
                        .clickable { onNavClick(item.title) }
                        .padding(
                            horizontal = if (selected) 14.dp else 10.dp,
                            vertical = 8.dp
                        )
                ) {
                    BookingsNavIcon(type = item.type, tint = tint)
                    if (selected) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.title,
                            color = OtpWhite,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingCard(
    booking: BookingItem,
    onRescheduleClick: () -> Unit,
    onCancelClick: () -> Unit,
    onRebookClick: () -> Unit,
    onReceiptClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = Color.Black.copy(alpha = 0.06f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(OtpWhite)
    ) {
        Box {
            Image(
                painter = painterResource(id = booking.imageRes),
                contentDescription = booking.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            val badgeBg = when (booking.status) {
                BookingStatus.Upcoming -> Color(0xFFB08D57)
                BookingStatus.Completed -> Color(0xFFE8E4DF)
                BookingStatus.Cancelled -> Color(0xFFFDECEC)
            }
            val badgeFg = when (booking.status) {
                BookingStatus.Upcoming -> OtpWhite
                BookingStatus.Completed -> HomeSubtitle
                BookingStatus.Cancelled -> Color(0xFFE57373)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(badgeBg)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = booking.status.name,
                    color = badgeFg,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = booking.title,
                color = HomeTitle,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(
                        color = HomeSubtitle,
                        radius = size.minDimension * 0.12f,
                        center = Offset(size.width * 0.5f, size.height * 0.52f)
                    )
                    for (i in 0 until 5) {
                        val angle = Math.toRadians((-90.0 + i * 72.0))
                        val cx = size.width * 0.5f + (size.minDimension * 0.22f * Math.cos(angle)).toFloat()
                        val cy = size.height * 0.48f + (size.minDimension * 0.22f * Math.sin(angle)).toFloat()
                        drawCircle(
                            color = HomeSubtitle,
                            radius = size.minDimension * 0.12f,
                            center = Offset(cx, cy),
                            style = Stroke(width = 1.2.dp.toPx())
                        )
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = booking.location,
                    color = HomeSubtitle,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F3F0))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
                    drawRoundRect(
                        color = Color(0xFF6B5A3E),
                        topLeft = Offset(size.width * 0.15f, size.height * 0.25f),
                        size = Size(size.width * 0.7f, size.height * 0.62f),
                        cornerRadius = CornerRadius(2.dp.toPx()),
                        style = stroke
                    )
                    drawLine(
                        color = Color(0xFF6B5A3E),
                        start = Offset(size.width * 0.15f, size.height * 0.42f),
                        end = Offset(size.width * 0.85f, size.height * 0.42f),
                        strokeWidth = 1.5.dp.toPx()
                    )
                    drawLine(
                        color = Color(0xFF6B5A3E),
                        start = Offset(size.width * 0.32f, size.height * 0.15f),
                        end = Offset(size.width * 0.32f, size.height * 0.32f),
                        strokeWidth = 1.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color(0xFF6B5A3E),
                        start = Offset(size.width * 0.68f, size.height * 0.15f),
                        end = Offset(size.width * 0.68f, size.height * 0.32f),
                        strokeWidth = 1.5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = booking.date,
                        color = HomeTitle,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = booking.time,
                        color = HomeSubtitle,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            when (booking.status) {
                BookingStatus.Upcoming -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .border(1.5.dp, WelcomeGold, RoundedCornerShape(22.dp))
                                .clickable(onClick = onRescheduleClick)
                        ) {
                            Text(
                                text = "Reschedule",
                                color = WelcomeGold,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFF0D0D0), RoundedCornerShape(12.dp))
                                .background(Color(0xFFFDF6F6))
                                .clickable(onClick = onCancelClick)
                        ) {
                            Canvas(modifier = Modifier.size(14.dp)) {
                                drawLine(
                                    color = Color(0xFFE05A5A),
                                    start = Offset(size.width * 0.15f, size.height * 0.15f),
                                    end = Offset(size.width * 0.85f, size.height * 0.85f),
                                    strokeWidth = 2.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                                drawLine(
                                    color = Color(0xFFE05A5A),
                                    start = Offset(size.width * 0.85f, size.height * 0.15f),
                                    end = Offset(size.width * 0.15f, size.height * 0.85f),
                                    strokeWidth = 2.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                    }
                }

                BookingStatus.Completed -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .background(Color(0xFF6B5A3E))
                                .clickable(onClick = onRebookClick)
                        ) {
                            Text(
                                text = "Rebook",
                                color = OtpWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.5.dp, WelcomeGold, RoundedCornerShape(12.dp))
                                .clickable(onClick = onReceiptClick)
                        ) {
                            Canvas(modifier = Modifier.size(16.dp)) {
                                val stroke = Stroke(
                                    width = 1.5.dp.toPx(),
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                                drawRoundRect(
                                    color = WelcomeGold,
                                    topLeft = Offset(size.width * 0.22f, size.height * 0.12f),
                                    size = Size(size.width * 0.56f, size.height * 0.76f),
                                    cornerRadius = CornerRadius(1.5.dp.toPx()),
                                    style = stroke
                                )
                                drawLine(
                                    color = WelcomeGold,
                                    start = Offset(size.width * 0.34f, size.height * 0.38f),
                                    end = Offset(size.width * 0.66f, size.height * 0.38f),
                                    strokeWidth = 1.5.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                                drawLine(
                                    color = WelcomeGold,
                                    start = Offset(size.width * 0.34f, size.height * 0.52f),
                                    end = Offset(size.width * 0.66f, size.height * 0.52f),
                                    strokeWidth = 1.5.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                                drawLine(
                                    color = WelcomeGold,
                                    start = Offset(size.width * 0.34f, size.height * 0.66f),
                                    end = Offset(size.width * 0.56f, size.height * 0.66f),
                                    strokeWidth = 1.5.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                    }
                }

                BookingStatus.Cancelled -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(Color(0xFF6B5A3E))
                            .clickable(onClick = onRebookClick)
                    ) {
                        Text(
                            text = "Book Again",
                            color = OtpWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingsNavIcon(
    type: BookingsNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            BookingsNavType.Home -> {
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

            BookingsNavType.Bookings -> {
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

            BookingsNavType.Wallet -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.14f, size.height * 0.28f),
                    size = Size(size.width * 0.72f, size.height * 0.5f),
                    cornerRadius = CornerRadius(2.5.dp.toPx()),
                    style = stroke
                )
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.52f, size.height * 0.42f),
                    size = Size(size.width * 0.34f, size.height * 0.22f),
                    cornerRadius = CornerRadius(1.5.dp.toPx()),
                    style = stroke
                )
            }

            BookingsNavType.Profile -> {
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
private fun BookingsScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        BookingsScreen()
    }
}
