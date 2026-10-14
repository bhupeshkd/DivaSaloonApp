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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.RegisterBorder
import com.example.mytestapplication.ui.theme.RegisterBrown
import com.example.mytestapplication.ui.theme.RegisterCard
import com.example.mytestapplication.ui.theme.RegisterHint
import com.example.mytestapplication.ui.theme.RegisterIconTint
import com.example.mytestapplication.ui.theme.RegisterLavender
import com.example.mytestapplication.ui.theme.RegisterPink
import com.example.mytestapplication.ui.theme.RegisterSubtitle

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: (
        fullName: String,
        mobile: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) -> Unit = { _, _, _, _, _ -> },
    onGoogleSignUpClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    var fullName by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        RegisterLavender,
                        RegisterPink,
                        RegisterCard
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Text(
            text = "Create Account",
            color = RegisterBrown,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Join DIVA for premium wellness experiences.",
            color = RegisterSubtitle,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        RegisterTextField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = "Full Name",
            leadingIcon = { FieldIcon(FieldIconType.Person) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterTextField(
            value = mobile,
            onValueChange = { value ->
                mobile = value.filter { ch ->
                    ch.isDigit() || ch == '+' || ch == ' '
                }
            },
            placeholder = "Mobile Number",
            keyboardType = KeyboardType.Phone,
            leadingIcon = { FieldIcon(FieldIconType.Phone) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Email Address",
            keyboardType = KeyboardType.Email,
            leadingIcon = { FieldIcon(FieldIconType.Email) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            isPassword = true,
            leadingIcon = { FieldIcon(FieldIconType.Lock) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Confirm Password",
            isPassword = true,
            leadingIcon = { FieldIcon(FieldIconType.ConfirmLock) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onSignUpClick(fullName, mobile, email, password, confirmPassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RegisterBrown,
                contentColor = OtpWhite
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = "Sign Up",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        OrDivider()

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, RegisterBorder, RoundedCornerShape(14.dp))
                .background(OtpWhite.copy(alpha = 0.55f))
                .clickable(onClick = onGoogleSignUpClick)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    drawGoogleG()
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Sign up with Google",
                    color = RegisterSubtitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = RegisterSubtitle)) {
                    append("Already have an account? ")
                }
                withStyle(
                    SpanStyle(
                        color = RegisterBrown,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("Login")
                }
            },
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable(onClick = onLoginClick)
                .padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

private enum class FieldIconType {
    Person,
    Phone,
    Email,
    Lock,
    ConfirmLock,
}

@Composable
private fun FieldIcon(type: FieldIconType) {
    Canvas(modifier = Modifier.size(18.dp)) {
        when (type) {
            FieldIconType.Person -> drawPerson()
            FieldIconType.Phone -> drawPhone()
            FieldIconType.Email -> drawEmail()
            FieldIconType.Lock -> drawLock()
            FieldIconType.ConfirmLock -> {
                drawLock()
                drawArc(
                    color = RegisterIconTint,
                    startAngle = -40f,
                    sweepAngle = 220f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.55f, size.height * 0.05f),
                    size = Size(size.width * 0.4f, size.height * 0.4f),
                    style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
    }
}

private fun DrawScope.drawPerson() {
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

private fun DrawScope.drawPhone() {
    val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = RegisterIconTint,
        topLeft = Offset(size.width * 0.28f, size.height * 0.08f),
        size = Size(size.width * 0.44f, size.height * 0.84f),
        cornerRadius = CornerRadius(size.width * 0.1f),
        style = stroke
    )
    drawLine(
        color = RegisterIconTint,
        start = Offset(size.width * 0.4f, size.height * 0.78f),
        end = Offset(size.width * 0.6f, size.height * 0.78f),
        strokeWidth = 1.6.dp.toPx(),
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawEmail() {
    val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = RegisterIconTint,
        topLeft = Offset(size.width * 0.08f, size.height * 0.22f),
        size = Size(size.width * 0.84f, size.height * 0.56f),
        cornerRadius = CornerRadius(2.dp.toPx()),
        style = stroke
    )
    val path = Path().apply {
        moveTo(size.width * 0.12f, size.height * 0.28f)
        lineTo(size.width * 0.5f, size.height * 0.52f)
        lineTo(size.width * 0.88f, size.height * 0.28f)
    }
    drawPath(path, color = RegisterIconTint, style = stroke)
}

private fun DrawScope.drawLock() {
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

@Composable
private fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable () -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            color = RegisterBrown,
            fontSize = 14.sp
        ),
        cursorBrush = SolidColor(RegisterBrown),
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
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(OtpWhite.copy(alpha = 0.72f))
                    .border(1.dp, RegisterBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp)
            ) {
                leadingIcon()
                Spacer(modifier = Modifier.width(12.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = RegisterHint,
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

@Composable
private fun OrDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = RegisterBorder,
            thickness = 1.dp
        )
        Text(
            text = "OR",
            color = RegisterHint,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = RegisterBorder,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        RegisterScreen()
    }
}
