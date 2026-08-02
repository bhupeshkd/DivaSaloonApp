package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.BookingSuccessAmount
import com.example.mytestapplication.ui.theme.BookingSuccessAmountLabel
import com.example.mytestapplication.ui.theme.BookingSuccessButton
import com.example.mytestapplication.ui.theme.BookingSuccessCard
import com.example.mytestapplication.ui.theme.BookingSuccessCheck
import com.example.mytestapplication.ui.theme.BookingSuccessGlow
import com.example.mytestapplication.ui.theme.BookingSuccessHalo
import com.example.mytestapplication.ui.theme.BookingSuccessScrim
import com.example.mytestapplication.ui.theme.BookingSuccessTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite

@Composable
fun BookingSuccessScreen(
    amount: String = "$249.99",
    modifier: Modifier = Modifier,
    onTrackBookingClick: () -> Unit = {},
    onBackToHomeClick: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(BookingSuccessScrim)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Dot grid background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val step = 18.dp.toPx()
            var y = step
            while (y < size.height) {
                var x = step
                while (x < size.width) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.08f),
                        radius = 1.2.dp.toPx(),
                        center = Offset(x, y)
                    )
                    x += step
                }
                y += step
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = Color.Black.copy(alpha = 0.35f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(BookingSuccessCard)
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            ReceiptGraphic(amount = amount)

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Booking Confirmed!",
                color = BookingSuccessTitle,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(
                    text = "Amount: ",
                    color = BookingSuccessAmountLabel,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = amount,
                    color = BookingSuccessAmount,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = onTrackBookingClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BookingSuccessButton,
                    contentColor = OtpWhite
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    text = "Track Booking",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Back to Home",
                color = BookingSuccessAmount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable(onClick = onBackToHomeClick)
            )
        }
    }
}

@Composable
private fun ReceiptGraphic(amount: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        // Soft beige halo
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        BookingSuccessHalo.copy(alpha = 0.95f),
                        BookingSuccessHalo.copy(alpha = 0.35f),
                        Color.Transparent
                    ),
                    center = Offset(size.width / 2f, size.height * 0.48f),
                    radius = size.minDimension * 0.55f
                ),
                radius = size.minDimension * 0.55f,
                center = Offset(size.width / 2f, size.height * 0.48f)
            )
        }

        // Receipt card
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(130.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(14.dp),
                    ambientColor = Color.Black.copy(alpha = 0.12f)
                )
                .clip(RoundedCornerShape(14.dp))
                .background(OtpWhite)
                .padding(horizontal = 14.dp, vertical = 18.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(72.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                BookingSuccessGlow.copy(alpha = 0.95f),
                                BookingSuccessGlow.copy(alpha = 0.35f),
                                Color.Transparent
                            ),
                            center = center,
                            radius = size.minDimension * 0.5f
                        ),
                        radius = size.minDimension * 0.5f,
                        center = center
                    )
                    drawCircle(
                        color = BookingSuccessCheck.copy(alpha = 0.15f),
                        radius = size.minDimension * 0.32f,
                        center = center,
                        style = Stroke(width = 8.dp.toPx())
                    )
                }
                Canvas(modifier = Modifier.size(40.dp)) {
                    val stroke = Stroke(
                        width = 5.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                    val check = Path().apply {
                        moveTo(size.width * 0.18f, size.height * 0.52f)
                        lineTo(size.width * 0.42f, size.height * 0.74f)
                        lineTo(size.width * 0.84f, size.height * 0.28f)
                    }
                    drawPath(check, color = BookingSuccessCheck, style = stroke)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Payment Successful",
                color = BookingSuccessTitle,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = amount,
                color = Color(0xFF9A9A9A),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }

        // Soft under shadow for receipt
        Canvas(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .size(width = 100.dp, height = 16.dp)
        ) {
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                ),
                topLeft = Offset.Zero,
                size = Size(size.width, size.height)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF1A1A1A)
@Composable
private fun BookingSuccessScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        BookingSuccessScreen()
    }
}
