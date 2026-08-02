package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.SuccessBg
import com.example.mytestapplication.ui.theme.SuccessBrown
import com.example.mytestapplication.ui.theme.SuccessGlow
import com.example.mytestapplication.ui.theme.SuccessSubtitle
import com.example.mytestapplication.ui.theme.SuccessTitle

@Composable
fun AuthSuccessScreen(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(SuccessBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 28.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        SuccessCheckIcon()

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Account Created\nSuccessfully!",
            color = SuccessTitle,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your journey to elegance begins here.\nWelcome to the DIVA community.",
            color = SuccessSubtitle,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onGetStartedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SuccessBrown,
                contentColor = OtpWhite
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = "Get Started",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
private fun SuccessCheckIcon() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(140.dp)
    ) {
        // Soft lavender glow rings
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            drawCircle(
                color = SuccessGlow.copy(alpha = 0.55f),
                radius = size.minDimension * 0.48f,
                center = center
            )
            drawCircle(
                color = SuccessGlow.copy(alpha = 0.85f),
                radius = size.minDimension * 0.38f,
                center = center
            )
        }

        // Brown circle + white check
        Canvas(modifier = Modifier.size(72.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            drawCircle(
                color = SuccessBrown,
                radius = size.minDimension / 2f,
                center = center
            )

            val stroke = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
            val check = Path().apply {
                moveTo(size.width * 0.28f, size.height * 0.52f)
                lineTo(size.width * 0.44f, size.height * 0.68f)
                lineTo(size.width * 0.74f, size.height * 0.34f)
            }
            drawPath(path = check, color = Color.White, style = stroke)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AuthSuccessScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        AuthSuccessScreen()
    }
}
