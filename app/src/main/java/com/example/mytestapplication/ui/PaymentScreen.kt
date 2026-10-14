package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.PaymentAccent
import com.example.mytestapplication.ui.theme.PaymentBg
import com.example.mytestapplication.ui.theme.PaymentBody
import com.example.mytestapplication.ui.theme.PaymentBorder
import com.example.mytestapplication.ui.theme.PaymentCard
import com.example.mytestapplication.ui.theme.PaymentFieldBg
import com.example.mytestapplication.ui.theme.PaymentSelectedBorder
import com.example.mytestapplication.ui.theme.PaymentTitle
import java.util.Locale
import kotlin.math.roundToInt

private enum class PayMethod {
    Cash,
}

@Composable
fun PaymentScreen(
    service: ServiceInfo = ServiceSamples.preview,
    scheduleLabel: String = "Tomorrow, 10:00 AM",
    taxRate: Double = 0.1041667,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onViewAllCoupons: () -> Unit = {},
    onConfirmPayClick: (String) -> Unit = {},
) {
    var coupon by remember { mutableStateOf("") }
    var appliedDiscount by remember { mutableStateOf(0.0) }
    var selectedPay by remember { mutableStateOf(PayMethod.Cash) }

    val subtotal = remember(service.price) { parseMoney(service.price) }
    val taxes = remember(subtotal, taxRate) {
        ((subtotal * taxRate) * 100.0).roundToInt() / 100.0
    }
    val total = remember(subtotal, taxes, appliedDiscount) {
        (subtotal + taxes - appliedDiscount).coerceAtLeast(0.0)
    }
    val totalLabel = remember(total) { formatMoney(total) }
    val durationLabel = service.duration.replace("mins", "min").replace(" minutes", " min")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PaymentBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PaymentCard)
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
                    drawPath(path, color = PaymentTitle, style = stroke)
                }
            }
            Text(
                text = "Payment",
                color = PaymentTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
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

            // Booking summary
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color.Black.copy(alpha = 0.06f)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(PaymentCard)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Booking Summary",
                    color = PaymentAccent,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ServiceImage(
                        service = service,
                        contentDescription = service.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = service.title,
                            color = PaymentAccent,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "$scheduleLabel • $durationLabel",
                            color = PaymentBody,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = PaymentBorder, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                PriceRow("Subtotal", formatMoney(subtotal))
                Spacer(modifier = Modifier.height(8.dp))
                PriceRow("Taxes & Fees", formatMoney(taxes))
                if (appliedDiscount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    PriceRow("Discount", "-${formatMoney(appliedDiscount)}")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total Amount",
                        color = PaymentTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = totalLabel,
                        color = PaymentAccent,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Coupon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Coupon Code",
                    color = PaymentTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "View All",
                    color = PaymentAccent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(onClick = onViewAllCoupons)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PaymentFieldBg)
                        .padding(horizontal = 12.dp)
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
                        drawRoundRect(
                            color = PaymentBody,
                            topLeft = Offset(size.width * 0.1f, size.height * 0.2f),
                            size = Size(size.width * 0.8f, size.height * 0.6f),
                            cornerRadius = CornerRadius(2.dp.toPx()),
                            style = stroke
                        )
                        drawLine(
                            color = PaymentBody,
                            start = Offset(size.width * 0.35f, size.height * 0.2f),
                            end = Offset(size.width * 0.35f, size.height * 0.8f),
                            strokeWidth = 1.5.dp.toPx()
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = coupon,
                        onValueChange = { coupon = it },
                        singleLine = true,
                        textStyle = TextStyle(color = PaymentTitle, fontSize = 14.sp),
                        cursorBrush = SolidColor(PaymentAccent),
                        decorationBox = { inner ->
                            if (coupon.isEmpty()) {
                                Text(
                                    text = "Enter promo code",
                                    color = PaymentBody,
                                    fontSize = 14.sp
                                )
                            }
                            inner()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PaymentAccent)
                        .clickable {
                            appliedDiscount = if (coupon.isNotBlank()) {
                                ((subtotal * 0.1) * 100.0).roundToInt() / 100.0
                            } else {
                                0.0
                            }
                        }
                        .padding(horizontal = 18.dp)
                ) {
                    Text(
                        text = "Apply",
                        color = OtpWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Payment Method",
                color = PaymentTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))

            PayMethodCard(
                selected = selectedPay == PayMethod.Cash,
                title = "Cash at Venue",
                subtitle = "Pay when the professional arrives",
                onClick = { selectedPay = PayMethod.Cash },
                icon = { CashIcon() }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PayMethodCard(
                selected = false,
                enabled = false,
                title = "Online Payment",
                subtitle = "UPI, cards & wallets — coming soon",
                onClick = {},
                icon = { OnlineSoonIcon() }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { onConfirmPayClick(totalLabel) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .height(54.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PaymentAccent,
                contentColor = OtpWhite
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = "Confirm Booking $totalLabel",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun PriceRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = PaymentBody,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = PaymentTitle,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun PayMethodCard(
    selected: Boolean,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    enabled: Boolean = true,
) {
    val alpha = if (enabled) 1f else 0.55f
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = Color.Black.copy(alpha = 0.04f)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(PaymentCard.copy(alpha = alpha))
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) PaymentSelectedBorder else PaymentBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 14.dp, vertical = 14.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(PaymentFieldBg)
        ) {
            icon()
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = PaymentTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = PaymentBody,
                fontSize = 12.sp
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = 1.6.dp,
                    color = if (selected) PaymentAccent else PaymentBody,
                    shape = CircleShape
                )
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(PaymentAccent)
                )
            }
        }
    }
}

@Composable
private fun OnlineSoonIcon() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(
            color = PaymentAccent,
            topLeft = Offset(size.width * 0.12f, size.height * 0.3f),
            size = Size(size.width * 0.76f, size.height * 0.46f),
            cornerRadius = CornerRadius(2.dp.toPx()),
            style = stroke
        )
        drawLine(
            color = PaymentAccent,
            start = Offset(size.width * 0.2f, size.height * 0.42f),
            end = Offset(size.width * 0.8f, size.height * 0.42f),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun CashIcon() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(
            color = PaymentAccent,
            topLeft = Offset(size.width * 0.08f, size.height * 0.28f),
            size = Size(size.width * 0.84f, size.height * 0.48f),
            cornerRadius = CornerRadius(2.dp.toPx()),
            style = stroke
        )
        drawCircle(
            color = PaymentAccent,
            radius = size.minDimension * 0.14f,
            center = Offset(size.width / 2f, size.height / 2f),
            style = stroke
        )
    }
}

private fun parseMoney(value: String): Double =
    value.replace("₹", "").replace("$", "").replace(",", "").trim().toDoubleOrNull() ?: 0.0

private fun formatMoney(value: Double): String =
    String.format(Locale.US, "₹%.2f", value)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PaymentScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        PaymentScreen()
    }
}
