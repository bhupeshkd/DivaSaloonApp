package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.LoginAccent
import com.example.mytestapplication.ui.theme.LoginBg
import com.example.mytestapplication.ui.theme.LoginFieldBg
import com.example.mytestapplication.ui.theme.LoginFieldBorder
import com.example.mytestapplication.ui.theme.LoginHint
import com.example.mytestapplication.ui.theme.LoginLabel
import com.example.mytestapplication.ui.theme.LoginLegal
import com.example.mytestapplication.ui.theme.LoginSubtitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.RegisterIconTint

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onContinueClick: (email: String, password: String) -> Unit = { _, _ -> },
    onForgotPasswordClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LoginBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(44.dp)
                    .clickable(onClick = onBackClick)
            ) {
                Canvas(Modifier.size(22.dp)) {
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
                    drawPath(path = path, color = LoginAccent, style = stroke)
                }
            }

            Text(
                text = "Login",
                color = LoginAccent,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(36.dp))

            Text(
                text = "DIVA",
                color = LoginAccent,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                letterSpacing = 6.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Welcome back to your premium wellness journey.",
                color = LoginSubtitle,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(Modifier.height(40.dp))

            LoginInputField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                placeholder = "Enter your email",
                keyboardType = KeyboardType.Email,
                leadingIcon = { drawPersonIcon() }
            )

            Spacer(Modifier.height(16.dp))

            LoginInputField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                placeholder = "Enter your password",
                keyboardType = KeyboardType.Password,
                isPassword = true,
                leadingIcon = { drawLockIcon() }
            )

            Text(
                text = "Forgot Password?",
                color = LoginAccent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clickable(onClick = onForgotPasswordClick)
                    .padding(vertical = 4.dp)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onContinueClick(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(14.dp),
                        ambientColor = LoginAccent.copy(alpha = 0.25f),
                        spotColor = LoginAccent.copy(alpha = 0.25f)
                    ),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LoginAccent,
                    contentColor = OtpWhite
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(22.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = LoginFieldBorder,
                    thickness = 1.dp
                )
                Text(
                    text = "OR",
                    color = LoginHint,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = LoginFieldBorder,
                    thickness = 1.dp
                )
            }

            Spacer(Modifier.height(18.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.5.dp, LoginAccent, RoundedCornerShape(14.dp))
                    .background(OtpWhite)
                    .clickable(onClick = onGoogleClick)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(Modifier.size(18.dp)) {
                        drawGoogleG()
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Continue with Google",
                        color = LoginLabel,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(48.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = LoginLegal)) {
                        append("By continuing, you agree to our ")
                    }
                    withStyle(
                        SpanStyle(
                            color = LoginAccent,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("Terms")
                    }
                    withStyle(SpanStyle(color = LoginLegal)) {
                        append(" & ")
                    }
                    withStyle(
                        SpanStyle(
                            color = LoginAccent,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("Privacy Policy")
                    }
                },
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onTermsClick()
                        onPrivacyClick()
                    }
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun LoginInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    leadingIcon: DrawScope.() -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = LoginLabel,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = LoginAccent,
                fontSize = 14.sp
            ),
            cursorBrush = SolidColor(LoginAccent),
            visualTransformation = if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LoginFieldBg)
                        .border(1.dp, LoginFieldBorder, RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp)
                ) {
                    Canvas(Modifier.size(18.dp), onDraw = leadingIcon)
                    Spacer(Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = LoginHint,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun DrawScope.drawPersonIcon() {
    val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(
        color = RegisterIconTint,
        radius = size.minDimension * 0.18f,
        center = Offset(size.width / 2f, size.height * 0.32f),
        style = stroke
    )
    drawArc(
        color = RegisterIconTint,
        startAngle = 20f,
        sweepAngle = 140f,
        useCenter = false,
        topLeft = Offset(size.width * 0.18f, size.height * 0.48f),
        size = Size(size.width * 0.64f, size.height * 0.55f),
        style = stroke
    )
}

private fun DrawScope.drawLockIcon() {
    val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = RegisterIconTint,
        topLeft = Offset(size.width * 0.22f, size.height * 0.42f),
        size = Size(size.width * 0.56f, size.height * 0.46f),
        cornerRadius = CornerRadius(2.dp.toPx()),
        style = stroke
    )
    drawArc(
        color = RegisterIconTint,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(size.width * 0.32f, size.height * 0.12f),
        size = Size(size.width * 0.36f, size.height * 0.4f),
        style = stroke
    )
}

private fun DrawScope.drawGoogleG() {
    val strokeWidth = 2.2.dp.toPx()
    val colors = listOf(
        Color(0xFFEA4335),
        Color(0xFFFBBC05),
        Color(0xFF34A853),
        Color(0xFF4285F4)
    )
    var start = -45f
    colors.forEach { color ->
        drawArc(
            color = color,
            startAngle = start,
            sweepAngle = 86f,
            useCenter = false,
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = Size(this.size.width - strokeWidth, this.size.height - strokeWidth),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        start += 90f
    }
    drawRect(
        color = Color(0xFF4285F4),
        topLeft = Offset(this.size.width * 0.48f, this.size.height * 0.45f),
        size = Size(this.size.width * 0.42f, strokeWidth)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        LoginScreen()
    }
}
