package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.PaymentSuccessAmount
import com.example.mytestapplication.ui.theme.PaymentSuccessBg
import com.example.mytestapplication.ui.theme.PaymentSuccessCheck
import com.example.mytestapplication.ui.theme.PaymentSuccessGlow
import com.example.mytestapplication.ui.theme.PaymentSuccessTitle
import kotlinx.coroutines.delay

private const val PaymentSuccessHoldSeconds = 6

@Composable
fun PaymentSuccessScreen(
    amount: String = "$249.99",
    modifier: Modifier = Modifier,
    holdSeconds: Int = PaymentSuccessHoldSeconds,
    onFinished: () -> Unit = {},
) {
    var secondsLeft by remember { mutableIntStateOf(holdSeconds) }

    LaunchedEffect(holdSeconds) {
        secondsLeft = holdSeconds
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft -= 1
        }
        onFinished()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(PaymentSuccessBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 32.dp)
    ) {
        PaymentSuccessCheckIcon()

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Payment Successful",
            color = PaymentSuccessTitle,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.04f),
                    offset = Offset(0f, 2f),
                    blurRadius = 4f
                )
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Amount: $amount",
            color = PaymentSuccessAmount,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = if (secondsLeft > 0) {
                "Redirecting in ${secondsLeft}s"
            } else {
                "Redirecting..."
            },
            color = PaymentSuccessAmount,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PaymentSuccessCheckIcon() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height * 0.46f)

            // Soft purple radial glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        PaymentSuccessGlow.copy(alpha = 0.95f),
                        PaymentSuccessGlow.copy(alpha = 0.45f),
                        PaymentSuccessGlow.copy(alpha = 0.12f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = size.minDimension * 0.48f
                ),
                radius = size.minDimension * 0.48f,
                center = center
            )

            // Soft ring
            drawCircle(
                color = PaymentSuccessCheck.copy(alpha = 0.18f),
                radius = size.minDimension * 0.28f,
                center = center,
                style = Stroke(width = 10.dp.toPx())
            )
            drawCircle(
                color = PaymentSuccessCheck.copy(alpha = 0.08f),
                radius = size.minDimension * 0.34f,
                center = center,
                style = Stroke(width = 16.dp.toPx())
            )

            // Soft ground shadow under check
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = Offset(center.x, size.height * 0.72f),
                    radius = size.width * 0.28f
                ),
                topLeft = Offset(size.width * 0.22f, size.height * 0.66f),
                size = androidx.compose.ui.geometry.Size(size.width * 0.56f, size.height * 0.12f)
            )
        }

        // Bold purple checkmark
        Canvas(modifier = Modifier.size(110.dp)) {
            val stroke = Stroke(
                width = 14.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
            val check = Path().apply {
                moveTo(size.width * 0.18f, size.height * 0.52f)
                lineTo(size.width * 0.42f, size.height * 0.74f)
                lineTo(size.width * 0.84f, size.height * 0.28f)
            }
            // Soft shadow pass
            drawPath(
                path = check,
                color = PaymentSuccessCheck.copy(alpha = 0.25f),
                style = Stroke(
                    width = 16.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
            drawPath(
                path = check,
                color = PaymentSuccessCheck,
                style = stroke
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PaymentSuccessScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        PaymentSuccessScreen()
    }
}
