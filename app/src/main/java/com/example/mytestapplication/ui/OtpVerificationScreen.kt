package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpBackground
import com.example.mytestapplication.ui.theme.OtpBox
import com.example.mytestapplication.ui.theme.OtpBrown
import com.example.mytestapplication.ui.theme.OtpBrownLight
import com.example.mytestapplication.ui.theme.OtpCard
import com.example.mytestapplication.ui.theme.OtpSubtitle
import com.example.mytestapplication.ui.theme.OtpTitle
import com.example.mytestapplication.ui.theme.OtpWhite

private const val OtpLength = 6

@Composable
fun OtpVerificationScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String = "+1 234 ••••• 89",
    onBackClick: () -> Unit = {},
    onResendClick: () -> Unit = {},
    onVerifyClick: (String) -> Unit = {},
) {
    var otpValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(OtpBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            DotGridBackground(Modifier.fillMaxSize())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    PhoneBadgeIcon(Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "OTP Verification",
                        color = OtpWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(OtpCard)
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .navigationBarsPadding()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(onClick = onBackClick)
                    ) {
                        BackArrowIcon(Modifier.size(22.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Verify Your Number",
                        color = OtpTitle,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 34.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Enter the 6-digit code sent to $phoneNumber",
                        color = OtpSubtitle,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    OtpInputField(
                        otpText = otpValue,
                        onOtpChange = { value ->
                            if (value.length <= OtpLength && value.all { it.isDigit() }) {
                                otpValue = value
                            }
                        },
                        focusRequester = focusRequester
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Resend Code Now",
                        color = OtpBrownLight,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onResendClick)
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { onVerifyClick(otpValue) },
                        enabled = otpValue.length == OtpLength,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = OtpBrown.copy(alpha = 0.4f),
                                spotColor = OtpBrown.copy(alpha = 0.4f)
                            ),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OtpBrown,
                            contentColor = OtpWhite,
                            disabledContainerColor = OtpBrown.copy(alpha = 0.45f),
                            disabledContentColor = OtpWhite.copy(alpha = 0.8f)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    ) {
                        Text(
                            text = "Verify & Proceed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BackArrowIcon(iconModifier: Modifier = Modifier) {
    Canvas(iconModifier) {
        val stroke = Stroke(
            width = 2.2.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
        val path = Path().apply {
            moveTo(size.width * 0.58f, size.height * 0.18f)
            lineTo(size.width * 0.28f, size.height * 0.5f)
            lineTo(size.width * 0.58f, size.height * 0.82f)
        }
        drawPath(path = path, color = OtpTitle, style = stroke)
    }
}

@Composable
private fun PhoneBadgeIcon(iconModifier: Modifier = Modifier) {
    Canvas(iconModifier) {
        val stroke = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
        val w = size.width
        val h = size.height
        drawRoundRect(
            color = OtpWhite,
            topLeft = Offset(w * 0.22f, h * 0.05f),
            size = Size(w * 0.56f, h * 0.9f),
            cornerRadius = CornerRadius(w * 0.12f, w * 0.12f),
            style = stroke
        )
        drawLine(
            color = OtpWhite,
            start = Offset(w * 0.38f, h * 0.78f),
            end = Offset(w * 0.62f, h * 0.78f),
            strokeWidth = 1.4.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun DotGridBackground(contentModifier: Modifier = Modifier) {
    Canvas(contentModifier) {
        val spacing = 28.dp.toPx()
        val radius = 1.2.dp.toPx()
        val dotColor = Color.White.copy(alpha = 0.08f)
        var y = spacing
        while (y < size.height) {
            var x = spacing
            while (x < size.width) {
                drawCircle(
                    color = dotColor,
                    radius = radius,
                    center = Offset(x, y)
                )
                x += spacing
            }
            y += spacing
        }
    }
}

@Composable
private fun OtpInputField(
    otpText: String,
    onOtpChange: (String) -> Unit,
    focusRequester: FocusRequester,
) {
    BasicTextField(
        value = TextFieldValue(
            text = otpText,
            selection = TextRange(otpText.length)
        ),
        onValueChange = { newValue ->
            val filtered = newValue.text.filter { it.isDigit() }.take(OtpLength)
            onOtpChange(filtered)
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        cursorBrush = SolidColor(Color.Transparent),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(OtpLength) { index ->
                    val char = otpText.getOrNull(index)?.toString().orEmpty()
                    val isFocused = otpText.length == index ||
                        (otpText.length == OtpLength && index == OtpLength - 1)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isFocused) OtpBrown.copy(alpha = 0.12f) else OtpBox
                            )
                    ) {
                        Text(
                            text = char,
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OtpTitle,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OtpVerificationScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        OtpVerificationScreen()
    }
}
