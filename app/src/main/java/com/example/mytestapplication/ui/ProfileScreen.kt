package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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

private enum class ProfileMenuIcon {
    EditProfile,
    SavedAddress,
    PaymentMethods,
    BookingHistory,
    Favorites,
    Coupons,
    Referral,
    HelpCenter,
    Faqs,
    PrivacyPolicy,
}

private enum class ProfileNavType {
    Home,
    Services,
    Bookings,
    Profile,
}

private data class ProfileMenuItem(
    val title: String,
    val icon: ProfileMenuIcon,
)

private data class ProfileMenuSection(
    val title: String,
    val items: List<ProfileMenuItem>,
)

private data class ProfileNavItem(
    val title: String,
    val type: ProfileNavType,
)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userName: String = "Alexandra V.",
    membership: String = "Gold Member",
    avatarUrl: String? = null,
    avatarRes: Int = R.drawable.pro_elena,
    onMenuClick: () -> Unit = {},
    onHeaderProfileClick: () -> Unit = {},
    onEditAvatarClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    val sections = remember {
        listOf(
            ProfileMenuSection(
                title = "Account",
                items = listOf(
                    ProfileMenuItem("Edit Profile", ProfileMenuIcon.EditProfile),
                    ProfileMenuItem("Saved Address", ProfileMenuIcon.SavedAddress),
                    ProfileMenuItem("Payment Methods", ProfileMenuIcon.PaymentMethods),
                )
            ),
            ProfileMenuSection(
                title = "Activity",
                items = listOf(
                    ProfileMenuItem("Booking History", ProfileMenuIcon.BookingHistory),
                    ProfileMenuItem("Favorites", ProfileMenuIcon.Favorites),
                    ProfileMenuItem("Coupons", ProfileMenuIcon.Coupons),
                    ProfileMenuItem("Referral", ProfileMenuIcon.Referral),
                )
            ),
            ProfileMenuSection(
                title = "Support & Info",
                items = listOf(
                    ProfileMenuItem("Help Center", ProfileMenuIcon.HelpCenter),
                    ProfileMenuItem("FAQs", ProfileMenuIcon.Faqs),
                    ProfileMenuItem("Privacy Policy", ProfileMenuIcon.PrivacyPolicy),
                )
            ),
        )
    }

    val navItems = remember {
        listOf(
            ProfileNavItem("Home", ProfileNavType.Home),
            ProfileNavItem("Services", ProfileNavType.Services),
            ProfileNavItem("Bookings", ProfileNavType.Bookings),
            ProfileNavItem("Profile", ProfileNavType.Profile),
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
                        val y1 = size.height * 0.28f
                        val y2 = size.height * 0.5f
                        val y3 = size.height * 0.72f
                        drawLine(
                            color = HomeTitle,
                            start = Offset(size.width * 0.15f, y1),
                            end = Offset(size.width * 0.85f, y1),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = HomeTitle,
                            start = Offset(size.width * 0.15f, y2),
                            end = Offset(size.width * 0.85f, y2),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = HomeTitle,
                            start = Offset(size.width * 0.15f, y3),
                            end = Offset(size.width * 0.85f, y3),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                    }
                }

                Text(
                    text = "DIVA",
                    color = WelcomeGold,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 2.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFFE5E1DB), CircleShape)
                        .clickable(onClick = onHeaderProfileClick)
                ) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        val stroke = Stroke(
                            width = 1.6.dp.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                        drawCircle(
                            color = HomeSubtitle,
                            radius = size.minDimension * 0.16f,
                            center = Offset(size.width / 2f, size.height * 0.32f),
                            style = stroke
                        )
                        drawArc(
                            color = HomeSubtitle,
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

            Spacer(modifier = Modifier.height(16.dp))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Box {
                    RemoteImage(
                        imageUrl = avatarUrl,
                        fallbackRes = avatarRes,
                        contentDescription = userName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(88.dp)
                            .shadow(4.dp, CircleShape)
                            .clip(CircleShape)
                            .border(3.dp, OtpWhite, CircleShape)
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(30.dp)
                            .shadow(2.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(0xFF6B5A3E))
                            .clickable(onClick = onEditAvatarClick)
                    ) {
                        Canvas(modifier = Modifier.size(14.dp)) {
                            val stroke = Stroke(
                                width = 1.5.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                            val body = Path().apply {
                                moveTo(size.width * 0.22f, size.height * 0.72f)
                                lineTo(size.width * 0.22f, size.height * 0.48f)
                                lineTo(size.width * 0.62f, size.height * 0.18f)
                                lineTo(size.width * 0.82f, size.height * 0.38f)
                                lineTo(size.width * 0.42f, size.height * 0.72f)
                                close()
                            }
                            drawPath(body, color = OtpWhite, style = stroke)
                            drawLine(
                                color = OtpWhite,
                                start = Offset(size.width * 0.55f, size.height * 0.22f),
                                end = Offset(size.width * 0.75f, size.height * 0.42f),
                                strokeWidth = 1.5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                            drawLine(
                                color = OtpWhite,
                                start = Offset(size.width * 0.18f, size.height * 0.82f),
                                end = Offset(size.width * 0.48f, size.height * 0.82f),
                                strokeWidth = 1.5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = userName,
                color = HomeTitle,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (membership.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF0E6D4))
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                if (membership.contains("Member", ignoreCase = true)) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    // Crown
                    val stroke = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                    val crown = Path().apply {
                        moveTo(size.width * 0.12f, size.height * 0.72f)
                        lineTo(size.width * 0.12f, size.height * 0.42f)
                        lineTo(size.width * 0.32f, size.height * 0.58f)
                        lineTo(size.width * 0.5f, size.height * 0.22f)
                        lineTo(size.width * 0.68f, size.height * 0.58f)
                        lineTo(size.width * 0.88f, size.height * 0.42f)
                        lineTo(size.width * 0.88f, size.height * 0.72f)
                        close()
                    }
                    drawPath(crown, color = WelcomeGold)
                    drawPath(crown, color = WelcomeGold, style = stroke)
                    drawLine(
                        color = WelcomeGold,
                        start = Offset(size.width * 0.12f, size.height * 0.82f),
                        end = Offset(size.width * 0.88f, size.height * 0.82f),
                        strokeWidth = 1.4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = membership,
                    color = WelcomeGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
            }
            }

            Spacer(modifier = Modifier.height(18.dp))

            sections.forEach { section ->
                Text(
                    text = section.title.uppercase(),
                    color = HomeSubtitle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(bottom = 6.dp, start = 2.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = Color.Black.copy(alpha = 0.04f),
                            spotColor = Color.Black.copy(alpha = 0.06f)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(OtpWhite)
                ) {
                    section.items.forEachIndexed { index, item ->
                        ProfileMenuRow(
                            title = item.title,
                            icon = item.icon,
                            showDivider = index < section.items.lastIndex,
                            onClick = { onItemClick(item.title) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, Color(0xFFE05A5A).copy(alpha = 0.45f), RoundedCornerShape(14.dp))
                    .background(OtpWhite)
                    .clickable(onClick = onLogoutClick)
                    .padding(vertical = 14.dp)
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val stroke = Stroke(
                        width = 1.7.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                    val door = Path().apply {
                        moveTo(size.width * 0.42f, size.height * 0.18f)
                        lineTo(size.width * 0.18f, size.height * 0.18f)
                        lineTo(size.width * 0.18f, size.height * 0.82f)
                        lineTo(size.width * 0.42f, size.height * 0.82f)
                    }
                    drawPath(door, color = Color(0xFFE05A5A), style = stroke)
                    drawLine(
                        color = Color(0xFFE05A5A),
                        start = Offset(size.width * 0.42f, size.height * 0.5f),
                        end = Offset(size.width * 0.82f, size.height * 0.5f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color(0xFFE05A5A),
                        start = Offset(size.width * 0.68f, size.height * 0.34f),
                        end = Offset(size.width * 0.82f, size.height * 0.5f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color(0xFFE05A5A),
                        start = Offset(size.width * 0.68f, size.height * 0.66f),
                        end = Offset(size.width * 0.82f, size.height * 0.5f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Logout",
                    color = Color(0xFFE05A5A),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
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
                val selected = item.type == ProfileNavType.Profile
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
                    ProfileNavIcon(type = item.type, tint = tint)
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
private fun ProfileMenuRow(
    title: String,
    icon: ProfileMenuIcon,
    showDivider: Boolean,
    onClick: () -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(22.dp)
            ) {
                ProfileMenuIconCanvas(icon = icon)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                color = HomeTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Canvas(modifier = Modifier.size(12.dp)) {
                drawLine(
                    color = Color(0xFFC8C4BE),
                    start = Offset(size.width * 0.3f, size.height * 0.2f),
                    end = Offset(size.width * 0.7f, size.height * 0.5f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color(0xFFC8C4BE),
                    start = Offset(size.width * 0.3f, size.height * 0.8f),
                    end = Offset(size.width * 0.7f, size.height * 0.5f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 52.dp, end = 16.dp)
                    .height(1.dp)
                    .background(Color(0xFFF0EEEA))
            )
        }
    }
}

@Composable
private fun ProfileMenuIconCanvas(icon: ProfileMenuIcon) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        val tint = Color(0xFF6B5A3E)
        when (icon) {
            ProfileMenuIcon.EditProfile -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.14f,
                    center = Offset(size.width * 0.5f, size.height * 0.3f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.22f, size.height * 0.48f),
                    size = Size(size.width * 0.56f, size.height * 0.42f),
                    style = stroke
                )
            }

            ProfileMenuIcon.SavedAddress -> {
                val pin = Path().apply {
                    moveTo(size.width * 0.5f, size.height * 0.9f)
                    quadraticTo(
                        size.width * 0.12f,
                        size.height * 0.55f,
                        size.width * 0.5f,
                        size.height * 0.12f
                    )
                    quadraticTo(
                        size.width * 0.88f,
                        size.height * 0.55f,
                        size.width * 0.5f,
                        size.height * 0.9f
                    )
                    close()
                }
                drawPath(pin, color = tint, style = stroke)
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.1f,
                    center = Offset(size.width * 0.5f, size.height * 0.38f),
                    style = stroke
                )
            }

            ProfileMenuIcon.PaymentMethods -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.12f, size.height * 0.28f),
                    size = Size(size.width * 0.76f, size.height * 0.5f),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.12f, size.height * 0.42f),
                    end = Offset(size.width * 0.88f, size.height * 0.42f),
                    strokeWidth = 1.6.dp.toPx()
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.22f, size.height * 0.6f),
                    end = Offset(size.width * 0.42f, size.height * 0.6f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            ProfileMenuIcon.BookingHistory -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.38f,
                    center = Offset(size.width / 2f, size.height / 2f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.28f),
                    end = Offset(size.width * 0.5f, size.height * 0.52f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.52f),
                    end = Offset(size.width * 0.68f, size.height * 0.62f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            ProfileMenuIcon.Favorites -> {
                val heart = Path().apply {
                    moveTo(size.width * 0.5f, size.height * 0.82f)
                    cubicTo(
                        size.width * 0.18f, size.height * 0.58f,
                        size.width * 0.12f, size.height * 0.32f,
                        size.width * 0.32f, size.height * 0.22f
                    )
                    cubicTo(
                        size.width * 0.42f, size.height * 0.16f,
                        size.width * 0.5f, size.height * 0.28f,
                        size.width * 0.5f, size.height * 0.36f
                    )
                    cubicTo(
                        size.width * 0.5f, size.height * 0.28f,
                        size.width * 0.58f, size.height * 0.16f,
                        size.width * 0.68f, size.height * 0.22f
                    )
                    cubicTo(
                        size.width * 0.88f, size.height * 0.32f,
                        size.width * 0.82f, size.height * 0.58f,
                        size.width * 0.5f, size.height * 0.82f
                    )
                    close()
                }
                drawPath(heart, color = tint, style = stroke)
            }

            ProfileMenuIcon.Coupons -> {
                val tag = Path().apply {
                    moveTo(size.width * 0.18f, size.height * 0.42f)
                    lineTo(size.width * 0.18f, size.height * 0.22f)
                    lineTo(size.width * 0.55f, size.height * 0.22f)
                    lineTo(size.width * 0.85f, size.height * 0.52f)
                    lineTo(size.width * 0.55f, size.height * 0.82f)
                    lineTo(size.width * 0.18f, size.height * 0.82f)
                    close()
                }
                drawPath(tag, color = tint, style = stroke)
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.07f,
                    center = Offset(size.width * 0.34f, size.height * 0.4f)
                )
            }

            ProfileMenuIcon.Referral -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.12f,
                    center = Offset(size.width * 0.38f, size.height * 0.3f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.16f, size.height * 0.42f),
                    size = Size(size.width * 0.44f, size.height * 0.4f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.72f, size.height * 0.28f),
                    end = Offset(size.width * 0.72f, size.height * 0.52f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.6f, size.height * 0.4f),
                    end = Offset(size.width * 0.84f, size.height * 0.4f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            ProfileMenuIcon.HelpCenter -> {
                drawArc(
                    color = tint,
                    startAngle = 200f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.2f, size.height * 0.22f),
                    size = Size(size.width * 0.6f, size.height * 0.55f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.28f, size.height * 0.7f),
                    end = Offset(size.width * 0.28f, size.height * 0.82f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.28f, size.height * 0.82f),
                    end = Offset(size.width * 0.48f, size.height * 0.82f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            ProfileMenuIcon.Faqs -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.38f,
                    center = Offset(size.width / 2f, size.height / 2f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 200f,
                    sweepAngle = 220f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.32f, size.height * 0.22f),
                    size = Size(size.width * 0.36f, size.height * 0.36f),
                    style = stroke
                )
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.05f,
                    center = Offset(size.width * 0.5f, size.height * 0.72f)
                )
            }

            ProfileMenuIcon.PrivacyPolicy -> {
                val shield = Path().apply {
                    moveTo(size.width * 0.5f, size.height * 0.12f)
                    lineTo(size.width * 0.82f, size.height * 0.28f)
                    lineTo(size.width * 0.82f, size.height * 0.52f)
                    quadraticTo(
                        size.width * 0.82f,
                        size.height * 0.78f,
                        size.width * 0.5f,
                        size.height * 0.9f
                    )
                    quadraticTo(
                        size.width * 0.18f,
                        size.height * 0.78f,
                        size.width * 0.18f,
                        size.height * 0.52f
                    )
                    lineTo(size.width * 0.18f, size.height * 0.28f)
                    close()
                }
                drawPath(shield, color = tint, style = stroke)
            }
        }
    }
}

@Composable
private fun ProfileNavIcon(
    type: ProfileNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            ProfileNavType.Home -> {
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

            ProfileNavType.Services -> {
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

            ProfileNavType.Bookings -> {
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

            ProfileNavType.Profile -> {
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
private fun ProfileScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        ProfileScreen()
    }
}
