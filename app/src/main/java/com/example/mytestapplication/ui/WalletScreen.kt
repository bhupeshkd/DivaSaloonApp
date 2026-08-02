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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
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

private enum class WalletActionType {
    TopUp,
    Withdraw,
    Send,
}

private enum class WalletTxnIcon {
    Spa,
    Cashback,
    Shopping,
}

private enum class WalletNavType {
    Home,
    Bookings,
    Wallet,
    Profile,
}

private data class WalletAction(
    val title: String,
    val type: WalletActionType,
)

private data class WalletTransaction(
    val title: String,
    val time: String,
    val amount: String,
    val status: String,
    val icon: WalletTxnIcon,
)

private data class WalletNavItem(
    val title: String,
    val type: WalletNavType,
)

@Composable
fun WalletScreen(
    modifier: Modifier = Modifier,
    balance: String = "$1,240.50",
    avatarRes: Int = R.drawable.pro_elena,
    onMenuClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onActionClick: (String) -> Unit = {},
    onViewAllClick: () -> Unit = {},
    onTransactionClick: (String) -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    val actions = remember {
        listOf(
            WalletAction("Top Up", WalletActionType.TopUp),
            WalletAction("Withdraw", WalletActionType.Withdraw),
            WalletAction("Send", WalletActionType.Send),
        )
    }

    val transactions = remember {
        listOf(
            WalletTransaction(
                title = "Spa Day Payment",
                time = "Today, 2:30 PM",
                amount = "-$150.00",
                status = "Completed",
                icon = WalletTxnIcon.Spa,
            ),
            WalletTransaction(
                title = "Cashback Received",
                time = "Yesterday, 10:15 AM",
                amount = "+$25.00",
                status = "Completed",
                icon = WalletTxnIcon.Cashback,
            ),
            WalletTransaction(
                title = "Luxury Skincare Set",
                time = "Oct 24, 4:00 PM",
                amount = "-$85.50",
                status = "Completed",
                icon = WalletTxnIcon.Shopping,
            ),
        )
    }

    val navItems = remember {
        listOf(
            WalletNavItem("Home", WalletNavType.Home),
            WalletNavItem("Bookings", WalletNavType.Bookings),
            WalletNavItem("Wallet", WalletNavType.Wallet),
            WalletNavItem("Profile", WalletNavType.Profile),
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
                text = "My Wallet",
                color = Color(0xFF6B5A3E),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Manage your funds and transactions.",
                color = HomeSubtitle,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color(0xFF9C4DBB).copy(alpha = 0.25f),
                        spotColor = Color(0xFF9C4DBB).copy(alpha = 0.3f)
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF8E4EC6),
                                Color(0xFFB04AA8),
                                Color(0xFFE04E8A),
                            ),
                            start = Offset.Zero,
                            end = Offset(900f, 700f)
                        )
                    )
                    .padding(horizontal = 22.dp, vertical = 22.dp)
            ) {
                Text(
                    text = "TOTAL BALANCE",
                    color = OtpWhite.copy(alpha = 0.85f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = balance,
                    color = OtpWhite,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(22.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    actions.forEach { action ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { onActionClick(action.title) }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(OtpWhite)
                            ) {
                                WalletActionIcon(type = action.type)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = action.title,
                                color = OtpWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Recent Transactions",
                    color = Color(0xFF6B5A3E),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "View All",
                    color = HomeSubtitle,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable(onClick = onViewAllClick)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(OtpWhite.copy(alpha = 0f))
            ) {
                transactions.forEachIndexed { index, txn ->
                    WalletTransactionRow(
                        transaction = txn,
                        showDivider = index < transactions.lastIndex,
                        onClick = { onTransactionClick(txn.title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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
                val selected = item.type == WalletNavType.Wallet
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
                    WalletNavIcon(type = item.type, tint = tint)
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
private fun WalletTransactionRow(
    transaction: WalletTransaction,
    showDivider: Boolean,
    onClick: () -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 14.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF3EFE8))
            ) {
                WalletTxnIconCanvas(icon = transaction.icon)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    color = HomeTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = transaction.time,
                    color = HomeSubtitle,
                    fontSize = 12.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = transaction.amount,
                    color = HomeTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = transaction.status,
                    color = HomeSubtitle,
                    fontSize = 12.sp
                )
            }
        }
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFEDEAE5))
            )
        }
    }
}

@Composable
private fun WalletActionIcon(type: WalletActionType) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val tint = Color(0xFF8E4EC6)
        val stroke = 2.2.dp.toPx()
        when (type) {
            WalletActionType.TopUp -> {
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.2f),
                    end = Offset(size.width * 0.5f, size.height * 0.8f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.2f, size.height * 0.5f),
                    end = Offset(size.width * 0.8f, size.height * 0.5f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
            }

            WalletActionType.Withdraw -> {
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.18f),
                    end = Offset(size.width * 0.5f, size.height * 0.72f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.72f),
                    end = Offset(size.width * 0.3f, size.height * 0.52f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.72f),
                    end = Offset(size.width * 0.7f, size.height * 0.52f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.28f, size.height * 0.82f),
                    end = Offset(size.width * 0.72f, size.height * 0.82f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
            }

            WalletActionType.Send -> {
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.82f),
                    end = Offset(size.width * 0.5f, size.height * 0.28f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.28f),
                    end = Offset(size.width * 0.3f, size.height * 0.48f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.28f),
                    end = Offset(size.width * 0.7f, size.height * 0.48f),
                    strokeWidth = stroke,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
private fun WalletTxnIconCanvas(icon: WalletTxnIcon) {
    Canvas(modifier = Modifier.size(22.dp)) {
        val tint = Color(0xFF6B5A3E)
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (icon) {
            WalletTxnIcon.Spa -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.12f,
                    center = Offset(size.width * 0.5f, size.height * 0.55f)
                )
                for (i in 0 until 5) {
                    val angle = Math.toRadians((-90.0 + i * 72.0))
                    val cx = size.width * 0.5f + (size.minDimension * 0.22f * Math.cos(angle)).toFloat()
                    val cy = size.height * 0.48f + (size.minDimension * 0.22f * Math.sin(angle)).toFloat()
                    drawCircle(
                        color = tint,
                        radius = size.minDimension * 0.13f,
                        center = Offset(cx, cy),
                        style = stroke
                    )
                }
            }

            WalletTxnIcon.Cashback -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.36f,
                    center = Offset(size.width / 2f, size.height / 2f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.28f),
                    end = Offset(size.width * 0.5f, size.height * 0.72f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawArc(
                    color = tint,
                    startAngle = 200f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.34f, size.height * 0.34f),
                    size = Size(size.width * 0.32f, size.height * 0.2f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.34f, size.height * 0.48f),
                    size = Size(size.width * 0.32f, size.height * 0.2f),
                    style = stroke
                )
            }

            WalletTxnIcon.Shopping -> {
                val bag = Path().apply {
                    moveTo(size.width * 0.28f, size.height * 0.38f)
                    lineTo(size.width * 0.72f, size.height * 0.38f)
                    lineTo(size.width * 0.68f, size.height * 0.82f)
                    lineTo(size.width * 0.32f, size.height * 0.82f)
                    close()
                }
                drawPath(bag, color = tint, style = stroke)
                drawArc(
                    color = tint,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.36f, size.height * 0.18f),
                    size = Size(size.width * 0.28f, size.height * 0.28f),
                    style = stroke
                )
            }
        }
    }
}

@Composable
private fun WalletNavIcon(
    type: WalletNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            WalletNavType.Home -> {
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

            WalletNavType.Bookings -> {
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

            WalletNavType.Wallet -> {
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

            WalletNavType.Profile -> {
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
private fun WalletScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        WalletScreen()
    }
}
