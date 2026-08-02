package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.CheckoutAccent
import com.example.mytestapplication.ui.theme.CheckoutAccentSoft
import com.example.mytestapplication.ui.theme.CheckoutBg
import com.example.mytestapplication.ui.theme.CheckoutBody
import com.example.mytestapplication.ui.theme.CheckoutButton
import com.example.mytestapplication.ui.theme.CheckoutCard
import com.example.mytestapplication.ui.theme.CheckoutDateSelected
import com.example.mytestapplication.ui.theme.CheckoutDivider
import com.example.mytestapplication.ui.theme.CheckoutLabel
import com.example.mytestapplication.ui.theme.CheckoutMapBg
import com.example.mytestapplication.ui.theme.CheckoutMapStreet
import com.example.mytestapplication.ui.theme.CheckoutPrice
import com.example.mytestapplication.ui.theme.CheckoutTimeDisabled
import com.example.mytestapplication.ui.theme.CheckoutTimeSelectedBg
import com.example.mytestapplication.ui.theme.CheckoutTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private data class BookingDate(
    val month: String,
    val day: String,
    val weekday: String,
)

private data class BookingTime(
    val label: String,
    val enabled: Boolean = true,
)

private enum class PaymentMethod {
    Card,
    Upi,
    Wallet,
}

@Composable
fun CheckoutScreen(
    service: ServiceInfo = ServiceCatalog.bridalMakeup,
    modifier: Modifier = Modifier,
    addressTitle: String = "Home Address",
    addressLine: String = "123 Luxury Lane, Apt 4B, Manhattan, NY 10001",
    walletBalance: String = "24.50",
    onBackClick: () -> Unit = {},
    onEditLocationClick: () -> Unit = {},
    onConfirmBookingClick: () -> Unit = {},
) {
    val dates = remember {
        listOf(
            BookingDate("OCT", "14", "Mon"),
            BookingDate("OCT", "15", "Tue"),
            BookingDate("OCT", "16", "Wed"),
            BookingDate("OCT", "17", "Thu"),
        )
    }
    val times = remember {
        listOf(
            BookingTime("09:00 AM"),
            BookingTime("10:00 AM"),
            BookingTime("11:00 AM"),
        )
    }

    var selectedDateIndex by remember { mutableIntStateOf(1) }
    var selectedTimeIndex by remember { mutableIntStateOf(1) }
    var selectedPayment by remember { mutableStateOf(PaymentMethod.Card) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CheckoutBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CheckoutTopBar(onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Complete Booking",
                color = CheckoutTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Review details and secure your appointment.",
                color = CheckoutBody,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            ServiceSummaryCard(service = service)

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Service Location",
                    color = CheckoutTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onEditLocationClick)
                ) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
                        drawRoundRect(
                            color = CheckoutAccent,
                            topLeft = Offset(size.width * 0.18f, size.height * 0.12f),
                            size = Size(size.width * 0.64f, size.height * 0.76f),
                            cornerRadius = CornerRadius(2.dp.toPx()),
                            style = stroke
                        )
                        drawLine(
                            color = CheckoutAccent,
                            start = Offset(size.width * 0.72f, size.height * 0.18f),
                            end = Offset(size.width * 0.88f, size.height * 0.02f),
                            strokeWidth = 1.5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Edit",
                        color = CheckoutAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LocationCard(
                addressTitle = addressTitle,
                addressLine = addressLine
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Select Date & Time",
                color = CheckoutTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(end = 4.dp)
            ) {
                itemsIndexed(dates) { index, date ->
                    DateChip(
                        date = date,
                        selected = selectedDateIndex == index,
                        onClick = { selectedDateIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Morning",
                color = CheckoutTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                times.forEachIndexed { index, time ->
                    TimeChip(
                        label = time.label,
                        selected = selectedTimeIndex == index,
                        enabled = time.enabled,
                        onClick = {
                            if (time.enabled) selectedTimeIndex = index
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Payment Method",
                color = CheckoutTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            PaymentOptionCard(
                selected = selectedPayment == PaymentMethod.Card,
                onClick = { selectedPayment = PaymentMethod.Card },
                title = "Credit/Debit Card",
                subtitle = "**** **** **** 4242",
                icon = { CardPaymentIcon() }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PaymentOptionCard(
                selected = selectedPayment == PaymentMethod.Upi,
                onClick = { selectedPayment = PaymentMethod.Upi },
                title = "UPI",
                subtitle = "GPay, PhonePe, Paytm",
                icon = { UpiPaymentIcon() }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PaymentOptionCard(
                selected = selectedPayment == PaymentMethod.Wallet,
                onClick = { selectedPayment = PaymentMethod.Wallet },
                title = "DIVA Wallet",
                subtitle = "Balance: $${walletBalance}",
                icon = { WalletPaymentIcon() }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        CheckoutFooter(
            totalAmount = service.price,
            onConfirmClick = onConfirmBookingClick
        )
    }
}

@Composable
private fun CheckoutTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CheckoutCard)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
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
                drawPath(path = path, color = CheckoutTitle, style = stroke)
            }
        }

        Text(
            text = "DIVA",
            color = WelcomeGold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            letterSpacing = 1.5.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Canvas(modifier = Modifier.size(12.dp)) {
                val stroke = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
                drawRoundRect(
                    color = CheckoutBody,
                    topLeft = Offset(size.width * 0.2f, size.height * 0.42f),
                    size = Size(size.width * 0.6f, size.height * 0.46f),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                    style = stroke
                )
                drawArc(
                    color = CheckoutBody,
                    startAngle = 200f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.28f, size.height * 0.12f),
                    size = Size(size.width * 0.44f, size.height * 0.42f),
                    style = stroke
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Checkout",
                color = CheckoutBody,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ServiceSummaryCard(service: ServiceInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(CheckoutCard)
            .padding(14.dp)
    ) {
        Image(
            painter = painterResource(id = service.imageRes),
            contentDescription = service.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = service.title,
                    color = CheckoutTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = service.price,
                    color = CheckoutPrice,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = service.shortDescription,
                color = CheckoutBody,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    val stroke = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
                    drawCircle(
                        color = CheckoutBody,
                        radius = size.minDimension * 0.4f,
                        center = Offset(size.width / 2f, size.height / 2f),
                        style = stroke
                    )
                    drawLine(
                        color = CheckoutBody,
                        start = Offset(size.width * 0.5f, size.height * 0.28f),
                        end = Offset(size.width * 0.5f, size.height * 0.52f),
                        strokeWidth = 1.4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = CheckoutBody,
                        start = Offset(size.width * 0.5f, size.height * 0.52f),
                        end = Offset(size.width * 0.68f, size.height * 0.64f),
                        strokeWidth = 1.4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = service.duration,
                    color = CheckoutBody,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun LocationCard(
    addressTitle: String,
    addressLine: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(CheckoutCard)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(CheckoutMapBg)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val street = CheckoutMapStreet
                // Horizontal streets
                for (i in 1..4) {
                    val y = size.height * (i / 5f)
                    drawLine(
                        color = street,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 3.dp.toPx()
                    )
                }
                // Vertical streets
                for (i in 1..5) {
                    val x = size.width * (i / 6f)
                    drawLine(
                        color = street,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        strokeWidth = 3.dp.toPx()
                    )
                }
                // Soft blocks
                drawRect(
                    color = Color.White.copy(alpha = 0.35f),
                    topLeft = Offset(size.width * 0.12f, size.height * 0.18f),
                    size = Size(size.width * 0.22f, size.height * 0.28f)
                )
                drawRect(
                    color = Color.White.copy(alpha = 0.28f),
                    topLeft = Offset(size.width * 0.58f, size.height * 0.45f),
                    size = Size(size.width * 0.26f, size.height * 0.32f)
                )
            }

            // Location pin
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(36.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val pin = Path().apply {
                        moveTo(size.width * 0.5f, size.height * 0.92f)
                        quadraticTo(
                            size.width * 0.08f,
                            size.height * 0.55f,
                            size.width * 0.5f,
                            size.height * 0.12f
                        )
                        quadraticTo(
                            size.width * 0.92f,
                            size.height * 0.55f,
                            size.width * 0.5f,
                            size.height * 0.92f
                        )
                        close()
                    }
                    drawPath(pin, color = CheckoutAccent)
                    drawCircle(
                        color = OtpWhite,
                        radius = size.minDimension * 0.16f,
                        center = Offset(size.width * 0.5f, size.height * 0.38f)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(14.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CheckoutAccentSoft)
            ) {
                Canvas(modifier = Modifier.size(16.dp)) {
                    val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
                    drawRoundRect(
                        color = CheckoutAccent,
                        topLeft = Offset(size.width * 0.18f, size.height * 0.38f),
                        size = Size(size.width * 0.64f, size.height * 0.48f),
                        cornerRadius = CornerRadius(2.dp.toPx()),
                        style = stroke
                    )
                    drawLine(
                        color = CheckoutAccent,
                        start = Offset(size.width * 0.18f, size.height * 0.38f),
                        end = Offset(size.width * 0.5f, size.height * 0.12f),
                        strokeWidth = 1.6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = CheckoutAccent,
                        start = Offset(size.width * 0.82f, size.height * 0.38f),
                        end = Offset(size.width * 0.5f, size.height * 0.12f),
                        strokeWidth = 1.6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = addressTitle,
                    color = CheckoutTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = addressLine,
                    color = CheckoutBody,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
private fun DateChip(
    date: BookingDate,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(64.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) CheckoutDateSelected else CheckoutCard)
            .then(
                if (!selected) {
                    Modifier.border(1.dp, CheckoutDivider, RoundedCornerShape(14.dp))
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = date.month,
            color = if (selected) OtpWhite.copy(alpha = 0.8f) else CheckoutBody,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = date.day,
            color = if (selected) OtpWhite else CheckoutTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = date.weekday,
            color = if (selected) OtpWhite.copy(alpha = 0.85f) else CheckoutBody,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun TimeChip(
    label: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val bg = when {
        selected -> CheckoutTimeSelectedBg
        else -> CheckoutCard
    }
    val border = when {
        selected -> CheckoutAccent
        !enabled -> CheckoutDivider
        else -> CheckoutDivider
    }
    val textColor = when {
        !enabled -> CheckoutTimeDisabled
        selected -> CheckoutAccent
        else -> CheckoutTitle
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(10.dp))
            .then(
                if (enabled) Modifier.clickable(onClick = onClick) else Modifier
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
        )
    }
}

@Composable
private fun PaymentOptionCard(
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (selected) 2.dp else 1.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) CheckoutAccentSoft else CheckoutCard)
            .then(
                if (selected) {
                    Modifier.border(1.dp, CheckoutAccent.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
                } else {
                    Modifier.border(1.dp, CheckoutDivider, RoundedCornerShape(14.dp))
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 14.dp)
    ) {
        // Radio
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = 1.6.dp,
                    color = if (selected) CheckoutAccent else CheckoutBody,
                    shape = CircleShape
                )
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(CheckoutAccent)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (selected) OtpWhite.copy(alpha = 0.7f) else CheckoutBg)
        ) {
            icon()
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = CheckoutTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = CheckoutBody,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun CardPaymentIcon() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(
            color = CheckoutAccent,
            topLeft = Offset(size.width * 0.08f, size.height * 0.22f),
            size = Size(size.width * 0.84f, size.height * 0.58f),
            cornerRadius = CornerRadius(2.5.dp.toPx()),
            style = stroke
        )
        drawLine(
            color = CheckoutAccent,
            start = Offset(size.width * 0.08f, size.height * 0.42f),
            end = Offset(size.width * 0.92f, size.height * 0.42f),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            color = CheckoutAccent,
            start = Offset(size.width * 0.2f, size.height * 0.62f),
            end = Offset(size.width * 0.42f, size.height * 0.62f),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun UpiPaymentIcon() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(
            color = CheckoutAccent,
            topLeft = Offset(size.width * 0.12f, size.height * 0.12f),
            size = Size(size.width * 0.76f, size.height * 0.76f),
            cornerRadius = CornerRadius(2.dp.toPx()),
            style = stroke
        )
        drawRoundRect(
            color = CheckoutAccent,
            topLeft = Offset(size.width * 0.28f, size.height * 0.28f),
            size = Size(size.width * 0.28f, size.height * 0.28f),
            cornerRadius = CornerRadius(1.dp.toPx())
        )
        drawCircle(
            color = CheckoutAccent,
            radius = 1.4.dp.toPx(),
            center = Offset(size.width * 0.7f, size.height * 0.35f)
        )
        drawCircle(
            color = CheckoutAccent,
            radius = 1.4.dp.toPx(),
            center = Offset(size.width * 0.7f, size.height * 0.55f)
        )
        drawCircle(
            color = CheckoutAccent,
            radius = 1.4.dp.toPx(),
            center = Offset(size.width * 0.52f, size.height * 0.7f)
        )
    }
}

@Composable
private fun WalletPaymentIcon() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(
            color = CheckoutAccent,
            topLeft = Offset(size.width * 0.1f, size.height * 0.22f),
            size = Size(size.width * 0.8f, size.height * 0.56f),
            cornerRadius = CornerRadius(2.5.dp.toPx()),
            style = stroke
        )
        drawRoundRect(
            color = CheckoutAccent,
            topLeft = Offset(size.width * 0.55f, size.height * 0.38f),
            size = Size(size.width * 0.35f, size.height * 0.24f),
            cornerRadius = CornerRadius(1.5.dp.toPx()),
            style = stroke
        )
        drawCircle(
            color = CheckoutAccent,
            radius = 1.6.dp.toPx(),
            center = Offset(size.width * 0.68f, size.height * 0.5f)
        )
    }
}

@Composable
private fun CheckoutFooter(
    totalAmount: String,
    onConfirmClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CheckoutCard)
    ) {
        HorizontalDivider(color = CheckoutDivider, thickness = 1.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "TOTAL AMOUNT",
                    color = CheckoutLabel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = totalAmount,
                    color = CheckoutPrice,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }

            Button(
                onClick = onConfirmClick,
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CheckoutButton,
                    contentColor = OtpWhite
                ),
                contentPadding = PaddingValues(horizontal = 20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    text = "Confirm Booking",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Canvas(modifier = Modifier.size(14.dp)) {
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
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CheckoutScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        CheckoutScreen()
    }
}
