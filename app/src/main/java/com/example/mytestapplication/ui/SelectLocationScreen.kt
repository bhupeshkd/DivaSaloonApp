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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.LocationAccent
import com.example.mytestapplication.ui.theme.LocationAccentSoft
import com.example.mytestapplication.ui.theme.LocationBody
import com.example.mytestapplication.ui.theme.LocationCardBorder
import com.example.mytestapplication.ui.theme.LocationConfirm
import com.example.mytestapplication.ui.theme.LocationDashed
import com.example.mytestapplication.ui.theme.LocationLabel
import com.example.mytestapplication.ui.theme.LocationMapBg
import com.example.mytestapplication.ui.theme.LocationMapStreet
import com.example.mytestapplication.ui.theme.LocationSearchBg
import com.example.mytestapplication.ui.theme.LocationTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite

private data class SavedAddress(
    val title: String,
    val line: String,
    val isHome: Boolean,
)

@Composable
fun SelectLocationScreen(
    modifier: Modifier = Modifier,
    initialQuery: String = "123 Luxury Lane",
    selectedTitle: String = "123 Luxury Lane",
    selectedSubtitle: String = "Beverly Hills, CA 90210",
    onBackClick: () -> Unit = {},
    onAddAddressClick: () -> Unit = {},
    onAddressMenuClick: (String) -> Unit = {},
    onConfirmClick: (String, String) -> Unit = { _, _ -> },
) {
    var searchQuery by remember { mutableStateOf(initialQuery) }
    var selectedIndex by remember { mutableIntStateOf(-1) }

    val saved = remember {
        listOf(
            SavedAddress("Home", "456 Serenity Blvd, Apt 4B", isHome = true),
            SavedAddress("Work", "789 Corporate Drive, Suite 100", isHome = false),
        )
    }

    val displayTitle = if (selectedIndex >= 0) {
        saved[selectedIndex].line.substringBefore(",")
    } else {
        selectedTitle
    }
    val displaySubtitle = if (selectedIndex >= 0) {
        saved[selectedIndex].line.substringAfter(", ", missingDelimiterValue = saved[selectedIndex].line)
    } else {
        selectedSubtitle
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F4F2))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(OtpWhite)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBackClick)
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
                    drawPath(path, color = LocationTitle, style = stroke)
                }
            }
            Text(
                text = "Select Location",
                color = LocationTitle,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Map + overlapping search
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .background(LocationMapBg)
                ) {
                    MapBackground()
                    PhoneMapPin(
                        pinLabel = displayTitle,
                        pinSub = displaySubtitle
                    )
                }

                // Pill search overlapping map bottom
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 22.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .shadow(
                            elevation = 6.dp,
                            shape = RoundedCornerShape(28.dp),
                            ambientColor = Color.Black.copy(alpha = 0.1f)
                        )
                        .clip(RoundedCornerShape(28.dp))
                        .background(OtpWhite)
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                        drawCircle(
                            color = LocationBody,
                            radius = size.minDimension * 0.28f,
                            center = Offset(size.width * 0.42f, size.height * 0.42f),
                            style = stroke
                        )
                        drawLine(
                            color = LocationBody,
                            start = Offset(size.width * 0.58f, size.height * 0.58f),
                            end = Offset(size.width * 0.82f, size.height * 0.82f),
                            strokeWidth = 1.8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = LocationTitle,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        cursorBrush = SolidColor(LocationAccent),
                        modifier = Modifier.weight(1f)
                    )
                    if (searchQuery.isNotEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(LocationSearchBg)
                                .clickable { searchQuery = "" }
                        ) {
                            Canvas(modifier = Modifier.size(10.dp)) {
                                drawLine(
                                    color = LocationBody,
                                    start = Offset(size.width * 0.2f, size.height * 0.2f),
                                    end = Offset(size.width * 0.8f, size.height * 0.8f),
                                    strokeWidth = 1.6.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                                drawLine(
                                    color = LocationBody,
                                    start = Offset(size.width * 0.8f, size.height * 0.2f),
                                    end = Offset(size.width * 0.2f, size.height * 0.8f),
                                    strokeWidth = 1.6.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "SELECTED LOCATION",
                    color = LocationLabel,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.Top) {
                    CrosshairIcon()
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = displayTitle,
                            color = LocationTitle,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = displaySubtitle,
                            color = LocationBody,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "SAVED ADDRESSES",
                    color = LocationLabel,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                saved.forEachIndexed { index, address ->
                    SavedAddressRow(
                        address = address,
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            searchQuery = address.line.substringBefore(",")
                        },
                        onMenuClick = { onAddressMenuClick(address.title) }
                    )
                    if (index < saved.lastIndex) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = LocationDashed,
                                style = Stroke(
                                    width = 1.5.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(10f, 8f),
                                        0f
                                    )
                                ),
                                cornerRadius = CornerRadius(14.dp.toPx())
                            )
                        }
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = onAddAddressClick)
                ) {
                    Text(
                        text = "+ Add New Address",
                        color = LocationAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Button(
            onClick = {
                onConfirmClick(
                    if (selectedIndex >= 0) saved[selectedIndex].title else "Home Address",
                    if (selectedIndex >= 0) {
                        saved[selectedIndex].line
                    } else {
                        "$selectedTitle, $selectedSubtitle"
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LocationConfirm,
                contentColor = OtpWhite
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = "Confirm Location",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CrosshairIcon() {
    Canvas(modifier = Modifier.size(18.dp)) {
        val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round)
        drawCircle(
            color = LocationAccent,
            radius = size.minDimension * 0.28f,
            center = Offset(size.width / 2f, size.height / 2f),
            style = stroke
        )
        drawCircle(
            color = LocationAccent,
            radius = size.minDimension * 0.08f,
            center = Offset(size.width / 2f, size.height / 2f)
        )
        drawLine(
            color = LocationAccent,
            start = Offset(size.width * 0.5f, size.height * 0.05f),
            end = Offset(size.width * 0.5f, size.height * 0.28f),
            strokeWidth = 1.7.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = LocationAccent,
            start = Offset(size.width * 0.5f, size.height * 0.72f),
            end = Offset(size.width * 0.5f, size.height * 0.95f),
            strokeWidth = 1.7.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = LocationAccent,
            start = Offset(size.width * 0.05f, size.height * 0.5f),
            end = Offset(size.width * 0.28f, size.height * 0.5f),
            strokeWidth = 1.7.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = LocationAccent,
            start = Offset(size.width * 0.72f, size.height * 0.5f),
            end = Offset(size.width * 0.95f, size.height * 0.5f),
            strokeWidth = 1.7.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun SavedAddressRow(
    address: SavedAddress,
    selected: Boolean,
    onClick: () -> Unit,
    onMenuClick: () -> Unit,
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
            .border(
                width = 1.dp,
                color = if (selected) LocationAccent.copy(alpha = 0.4f) else LocationCardBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .background(OtpWhite)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (address.isHome) LocationAccentSoft else LocationSearchBg)
        ) {
            Canvas(modifier = Modifier.size(18.dp)) {
                val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                val tint = if (address.isHome) LocationAccent else LocationBody
                if (address.isHome) {
                    val path = Path().apply {
                        moveTo(size.width * 0.5f, size.height * 0.18f)
                        lineTo(size.width * 0.15f, size.height * 0.48f)
                        lineTo(size.width * 0.15f, size.height * 0.85f)
                        lineTo(size.width * 0.85f, size.height * 0.85f)
                        lineTo(size.width * 0.85f, size.height * 0.48f)
                        close()
                    }
                    drawPath(path, color = tint, style = stroke)
                } else {
                    drawRoundRect(
                        color = tint,
                        topLeft = Offset(size.width * 0.2f, size.height * 0.32f),
                        size = Size(size.width * 0.6f, size.height * 0.5f),
                        cornerRadius = CornerRadius(2.dp.toPx()),
                        style = stroke
                    )
                    drawLine(
                        color = tint,
                        start = Offset(size.width * 0.35f, size.height * 0.32f),
                        end = Offset(size.width * 0.35f, size.height * 0.2f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = tint,
                        start = Offset(size.width * 0.35f, size.height * 0.2f),
                        end = Offset(size.width * 0.7f, size.height * 0.2f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = tint,
                        start = Offset(size.width * 0.7f, size.height * 0.2f),
                        end = Offset(size.width * 0.7f, size.height * 0.32f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = address.title,
                color = LocationTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = address.line,
                color = LocationBody,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable(onClick = onMenuClick)
        ) {
            Canvas(modifier = Modifier.size(14.dp)) {
                val c = LocationBody
                drawCircle(c, 1.6.dp.toPx(), Offset(size.width / 2f, size.height * 0.22f))
                drawCircle(c, 1.6.dp.toPx(), Offset(size.width / 2f, size.height * 0.5f))
                drawCircle(c, 1.6.dp.toPx(), Offset(size.width / 2f, size.height * 0.78f))
            }
        }
    }
}

@Composable
private fun MapBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Soft coastal-style gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFD9E4EC),
                    LocationMapBg,
                    Color(0xFFE2DDD4)
                )
            )
        )
        val street = LocationMapStreet
        for (i in 1..5) {
            val y = size.height * (i / 6f)
            drawLine(street, Offset(0f, y), Offset(size.width, y), 3.dp.toPx())
        }
        for (i in 1..6) {
            val x = size.width * (i / 7f)
            drawLine(street, Offset(x, 0f), Offset(x, size.height), 3.dp.toPx())
        }
        // Soft "water" blot
        drawCircle(
            color = Color(0xFFB8D0DE).copy(alpha = 0.35f),
            radius = size.minDimension * 0.28f,
            center = Offset(size.width * 0.82f, size.height * 0.22f)
        )
        drawRect(
            color = Color.White.copy(alpha = 0.28f),
            topLeft = Offset(size.width * 0.08f, size.height * 0.18f),
            size = Size(size.width * 0.22f, size.height * 0.26f)
        )
        drawRect(
            color = Color.White.copy(alpha = 0.2f),
            topLeft = Offset(size.width * 0.5f, size.height * 0.48f),
            size = Size(size.width * 0.28f, size.height * 0.32f)
        )
    }
}

@Composable
private fun PhoneMapPin(
    pinLabel: String,
    pinSub: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(width = 118.dp, height = 148.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset(size.width * 0.08f, size.height * 0.02f),
                    size = Size(size.width * 0.84f, size.height * 0.96f),
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
                drawRoundRect(
                    color = Color(0xFFECE8E2),
                    topLeft = Offset(size.width * 0.14f, size.height * 0.08f),
                    size = Size(size.width * 0.72f, size.height * 0.82f),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
                val street = LocationMapStreet
                val left = size.width * 0.14f
                val top = size.height * 0.08f
                val w = size.width * 0.72f
                val h = size.height * 0.82f
                for (i in 1..3) {
                    val y = top + h * (i / 4f)
                    drawLine(street, Offset(left, y), Offset(left + w, y), 2.dp.toPx())
                }
                for (i in 1..3) {
                    val x = left + w * (i / 4f)
                    drawLine(street, Offset(x, top), Offset(x, top + h), 2.dp.toPx())
                }
                drawRoundRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset(size.width * 0.35f, size.height * 0.04f),
                    size = Size(size.width * 0.3f, size.height * 0.035f),
                    cornerRadius = CornerRadius(4.dp.toPx())
                )
            }

            Canvas(modifier = Modifier.size(34.dp)) {
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
                drawPath(pin, color = LocationAccent)
                drawCircle(
                    color = OtpWhite,
                    radius = size.minDimension * 0.16f,
                    center = Offset(size.width * 0.5f, size.height * 0.38f)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .shadow(3.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(OtpWhite)
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = pinLabel,
                color = LocationTitle,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = pinSub,
                color = LocationBody,
                fontSize = 9.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectLocationScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        SelectLocationScreen()
    }
}
