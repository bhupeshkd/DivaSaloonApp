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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.R
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class EditProfileFieldIcon {
    Name,
    Email,
    Phone,
    Birthday,
}

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    avatarRes: Int = R.drawable.pro_elena,
    initialName: String = "Alexandra V.",
    initialEmail: String = "alexandra.v@example.com",
    initialPhone: String = "(555) 123-4567",
    initialDob: String = "10-05-15",
    onBackClick: () -> Unit = {},
    onChangePhotoClick: () -> Unit = {},
    onSaveClick: (name: String, email: String, phone: String, dob: String) -> Unit = { _, _, _, _ -> },
) {
    var fullName by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var phone by remember { mutableStateOf(initialPhone) }
    var dob by remember { mutableStateOf(initialDob) }

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
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
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
                        drawLine(
                            color = HomeTitle,
                            start = Offset(size.width * 0.65f, size.height * 0.2f),
                            end = Offset(size.width * 0.3f, size.height * 0.5f),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = HomeTitle,
                            start = Offset(size.width * 0.65f, size.height * 0.8f),
                            end = Offset(size.width * 0.3f, size.height * 0.5f),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
                Text(
                    text = "Edit Profile",
                    color = WelcomeGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box {
                    Image(
                        painter = painterResource(id = avatarRes),
                        contentDescription = "Profile photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .shadow(6.dp, CircleShape)
                            .clip(CircleShape)
                            .border(3.dp, OtpWhite, CircleShape)
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(34.dp)
                            .shadow(3.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(0xFF6B5A3E))
                            .clickable(onClick = onChangePhotoClick)
                    ) {
                        Canvas(modifier = Modifier.size(16.dp)) {
                            val stroke = Stroke(
                                width = 1.5.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                            drawRoundRect(
                                color = OtpWhite,
                                topLeft = Offset(size.width * 0.12f, size.height * 0.32f),
                                size = Size(size.width * 0.76f, size.height * 0.5f),
                                cornerRadius = CornerRadius(2.dp.toPx()),
                                style = stroke
                            )
                            drawCircle(
                                color = OtpWhite,
                                radius = size.minDimension * 0.16f,
                                center = Offset(size.width * 0.5f, size.height * 0.55f),
                                style = stroke
                            )
                            drawRoundRect(
                                color = OtpWhite,
                                topLeft = Offset(size.width * 0.34f, size.height * 0.18f),
                                size = Size(size.width * 0.32f, size.height * 0.18f),
                                cornerRadius = CornerRadius(1.5.dp.toPx()),
                                style = stroke
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = Color.Black.copy(alpha = 0.05f),
                        spotColor = Color.Black.copy(alpha = 0.08f)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(OtpWhite)
                    .padding(horizontal = 18.dp, vertical = 20.dp)
            ) {
                EditProfileField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = { fullName = it },
                    icon = EditProfileFieldIcon.Name
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditProfileField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    icon = EditProfileFieldIcon.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditProfileField(
                    label = "Phone Number",
                    value = phone,
                    onValueChange = { phone = it },
                    icon = EditProfileFieldIcon.Phone,
                    keyboardType = KeyboardType.Phone
                )
                Spacer(modifier = Modifier.height(16.dp))
                EditProfileField(
                    label = "Date of Birth",
                    value = dob,
                    onValueChange = { dob = it },
                    icon = EditProfileFieldIcon.Birthday
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }

        Button(
            onClick = { onSaveClick(fullName, email, phone, dob) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp)
                .height(54.dp),
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
                text = "Save Changes",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun EditProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: EditProfileFieldIcon,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = HomeSubtitle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp, start = 2.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = TextStyle(
                color = HomeTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            ),
            cursorBrush = SolidColor(Color(0xFF924EB1)),
            decorationBox = { inner ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .border(1.dp, Color(0xFFE0DEDC), RoundedCornerShape(12.dp))
                        .background(OtpWhite, RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(20.dp)
                    ) {
                        EditProfileFieldIconCanvas(icon = icon)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        inner()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EditProfileFieldIconCanvas(icon: EditProfileFieldIcon) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val tint = Color(0xFF9A9A9A)
        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (icon) {
            EditProfileFieldIcon.Name -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.16f,
                    center = Offset(size.width / 2f, size.height * 0.3f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.18f, size.height * 0.48f),
                    size = Size(size.width * 0.64f, size.height * 0.45f),
                    style = stroke
                )
            }

            EditProfileFieldIcon.Email -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.08f, size.height * 0.28f),
                    size = Size(size.width * 0.84f, size.height * 0.5f),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                    style = stroke
                )
                val flap = Path().apply {
                    moveTo(size.width * 0.08f, size.height * 0.32f)
                    lineTo(size.width * 0.5f, size.height * 0.58f)
                    lineTo(size.width * 0.92f, size.height * 0.32f)
                }
                drawPath(flap, color = tint, style = stroke)
            }

            EditProfileFieldIcon.Phone -> {
                val phone = Path().apply {
                    moveTo(size.width * 0.35f, size.height * 0.12f)
                    lineTo(size.width * 0.65f, size.height * 0.12f)
                    lineTo(size.width * 0.72f, size.height * 0.22f)
                    lineTo(size.width * 0.72f, size.height * 0.78f)
                    lineTo(size.width * 0.65f, size.height * 0.88f)
                    lineTo(size.width * 0.35f, size.height * 0.88f)
                    lineTo(size.width * 0.28f, size.height * 0.78f)
                    lineTo(size.width * 0.28f, size.height * 0.22f)
                    close()
                }
                drawPath(phone, color = tint, style = stroke)
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.4f, size.height * 0.78f),
                    end = Offset(size.width * 0.6f, size.height * 0.78f),
                    strokeWidth = 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            EditProfileFieldIcon.Birthday -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.15f, size.height * 0.25f),
                    size = Size(size.width * 0.7f, size.height * 0.62f),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.15f, size.height * 0.42f),
                    end = Offset(size.width * 0.85f, size.height * 0.42f),
                    strokeWidth = 1.5.dp.toPx()
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.32f, size.height * 0.15f),
                    end = Offset(size.width * 0.32f, size.height * 0.32f),
                    strokeWidth = 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.68f, size.height * 0.15f),
                    end = Offset(size.width * 0.68f, size.height * 0.32f),
                    strokeWidth = 1.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditProfileScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        EditProfileScreen()
    }
}
