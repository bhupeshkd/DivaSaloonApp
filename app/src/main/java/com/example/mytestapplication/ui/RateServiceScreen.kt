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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.R
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

@Composable
fun RateServiceScreen(
    serviceName: String = "Signature Facial",
    professionalName: String = "Isabella R.",
    imageRes: Int = R.drawable.cat_facial,
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    onSubmitClick: (rating: Int, review: String) -> Unit = { _, _ -> },
) {
    var rating by remember { mutableIntStateOf(0) }
    var review by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Dot grid
        Canvas(modifier = Modifier.fillMaxSize()) {
            val step = 18.dp.toPx()
            var y = step
            while (y < size.height) {
                var x = step
                while (x < size.width) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.07f),
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
                .padding(horizontal = 24.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFFAF8F5))
                .padding(horizontal = 20.dp, vertical = 18.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onCloseClick)
                        .align(Alignment.CenterStart)
                ) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawLine(
                            color = Color(0xFFB0B0B0),
                            start = Offset(size.width * 0.15f, size.height * 0.15f),
                            end = Offset(size.width * 0.85f, size.height * 0.85f),
                            strokeWidth = 1.8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color(0xFFB0B0B0),
                            start = Offset(size.width * 0.85f, size.height * 0.15f),
                            end = Offset(size.width * 0.15f, size.height * 0.85f),
                            strokeWidth = 1.8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
                Text(
                    text = "Rate Service",
                    color = WelcomeGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = serviceName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = serviceName,
                color = Color(0xFF1A1A2E),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "with $professionalName",
                color = Color(0xFF8A7A68),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "How was your experience?",
                color = Color(0xFF8A8A8A),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    val starIndex = index + 1
                    val filled = rating >= starIndex
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { rating = starIndex }
                    ) {
                        Canvas(modifier = Modifier.size(28.dp)) {
                            val path = Path().apply {
                                val midX = size.width / 2f
                                moveTo(midX, size.height * 0.08f)
                                lineTo(size.width * 0.61f, size.height * 0.38f)
                                lineTo(size.width * 0.95f, size.height * 0.42f)
                                lineTo(size.width * 0.7f, size.height * 0.64f)
                                lineTo(size.width * 0.78f, size.height * 0.95f)
                                lineTo(midX, size.height * 0.78f)
                                lineTo(size.width * 0.22f, size.height * 0.95f)
                                lineTo(size.width * 0.3f, size.height * 0.64f)
                                lineTo(size.width * 0.05f, size.height * 0.42f)
                                lineTo(size.width * 0.39f, size.height * 0.38f)
                                close()
                            }
                            if (filled) {
                                drawPath(path, color = Color(0xFFC4A574))
                            } else {
                                drawPath(
                                    path = path,
                                    color = Color(0xFFD9C9B0),
                                    style = Stroke(
                                        width = 1.6.dp.toPx(),
                                        join = StrokeJoin.Round
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Share your thoughts",
                color = Color(0xFF8A7A68),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            BasicTextField(
                value = review,
                onValueChange = { review = it },
                textStyle = TextStyle(
                    color = Color(0xFF1A1A2E),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
                cursorBrush = SolidColor(Color(0xFF924EB1)),
                decorationBox = { inner ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .border(1.dp, Color(0xFFE0DEDC), RoundedCornerShape(14.dp))
                            .background(OtpWhite, RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        if (review.isEmpty()) {
                            Text(
                                text = "Tell us what you loved about your service...",
                                color = Color(0xFFB0B0B0),
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                        inner()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (rating > 0) onSubmitClick(rating, review)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF924EB1),
                    contentColor = OtpWhite
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    text = "Submit Review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF1A1A1A)
@Composable
private fun RateServiceScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        RateServiceScreen()
    }
}
