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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.R
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeNavInactive
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class FavoritesNavType {
    Home,
    Bookings,
    Wallet,
    Profile,
}

private data class FavoritesNavItem(
    val title: String,
    val type: FavoritesNavType,
)

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    avatarRes: Int = R.drawable.pro_elena,
    onMenuClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onStartExploringClick: () -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    val navItems = remember {
        listOf(
            FavoritesNavItem("Home", FavoritesNavType.Home),
            FavoritesNavItem("Bookings", FavoritesNavType.Bookings),
            FavoritesNavItem("Wallet", FavoritesNavType.Wallet),
            FavoritesNavItem("Profile", FavoritesNavType.Profile),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HomeBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onMenuClick)
            ) {
                Canvas(modifier = Modifier.size(20.dp)) {
                    val stroke = 1.8.dp.toPx()
                    listOf(0.28f, 0.5f, 0.72f).forEach { y ->
                        drawLine(
                            color = HomeTitle,
                            start = Offset(size.width * 0.15f, size.height * y),
                            end = Offset(size.width * 0.85f, size.height * y),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            Text(
                text = "DIVA",
                color = WelcomeGold,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color(0xFFE5E1DB), CircleShape)
                    .clickable(onClick = onProfileClick)
            ) {
                Image(
                    painter = painterResource(id = avatarRes),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0x00FAF8F5),
                        ),
                        center = Offset(size.width / 2f, size.height * 0.42f),
                        radius = size.minDimension * 0.55f
                    ),
                    radius = size.minDimension * 0.55f,
                    center = Offset(size.width / 2f, size.height * 0.42f)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 36.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(110.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            ambientColor = Color.Black.copy(alpha = 0.06f),
                            spotColor = Color.Black.copy(alpha = 0.08f)
                        )
                        .clip(CircleShape)
                        .background(OtpWhite)
                ) {
                    Canvas(modifier = Modifier.size(48.dp)) {
                        val heart = Path().apply {
                            moveTo(size.width * 0.5f, size.height * 0.82f)
                            cubicTo(
                                size.width * 0.15f, size.height * 0.58f,
                                size.width * 0.08f, size.height * 0.28f,
                                size.width * 0.3f, size.height * 0.18f
                            )
                            cubicTo(
                                size.width * 0.42f, size.height * 0.12f,
                                size.width * 0.5f, size.height * 0.26f,
                                size.width * 0.5f, size.height * 0.34f
                            )
                            cubicTo(
                                size.width * 0.5f, size.height * 0.26f,
                                size.width * 0.58f, size.height * 0.12f,
                                size.width * 0.7f, size.height * 0.18f
                            )
                            cubicTo(
                                size.width * 0.92f, size.height * 0.28f,
                                size.width * 0.85f, size.height * 0.58f,
                                size.width * 0.5f, size.height * 0.82f
                            )
                            close()
                        }
                        drawPath(
                            path = heart,
                            color = WelcomeGold,
                            style = Stroke(
                                width = 2.2.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "No Favorites Yet",
                    color = HomeTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Your favorite services and professionals will appear here for easy booking.",
                    color = HomeSubtitle,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onStartExploringClick,
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6B5A3E),
                        contentColor = OtpWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    )
                ) {
                    Text(
                        text = "Start Exploring",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(OtpWhite)
                .padding(horizontal = 6.dp, vertical = 10.dp)
        ) {
            navItems.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavClick(item.title) }
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    FavoritesNavIcon(type = item.type, tint = HomeNavInactive)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        color = HomeNavInactive,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoritesNavIcon(
    type: FavoritesNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            FavoritesNavType.Home -> {
                val path = Path().apply {
                    moveTo(size.width * 0.5f, size.height * 0.18f)
                    lineTo(size.width * 0.15f, size.height * 0.48f)
                    lineTo(size.width * 0.15f, size.height * 0.85f)
                    lineTo(size.width * 0.85f, size.height * 0.85f)
                    lineTo(size.width * 0.85f, size.height * 0.48f)
                    close()
                }
                drawPath(path, color = tint, style = stroke)
            }

            FavoritesNavType.Bookings -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.18f, size.height * 0.22f),
                    size = Size(size.width * 0.64f, size.height * 0.62f),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.3f, size.height * 0.18f),
                    end = Offset(size.width * 0.3f, size.height * 0.3f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.7f, size.height * 0.18f),
                    end = Offset(size.width * 0.7f, size.height * 0.3f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            FavoritesNavType.Wallet -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.14f, size.height * 0.28f),
                    size = Size(size.width * 0.72f, size.height * 0.5f),
                    cornerRadius = CornerRadius(2.5.dp.toPx()),
                    style = stroke
                )
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.52f, size.height * 0.42f),
                    size = Size(size.width * 0.34f, size.height * 0.22f),
                    cornerRadius = CornerRadius(1.5.dp.toPx()),
                    style = stroke
                )
            }

            FavoritesNavType.Profile -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.16f,
                    center = Offset(size.width / 2f, size.height * 0.32f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.2f, size.height * 0.48f),
                    size = Size(size.width * 0.6f, size.height * 0.45f),
                    style = stroke
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FavoritesScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        FavoritesScreen()
    }
}
