package com.example.mytestapplication.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mytestapplication.ui.theme.HomeAccent
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeEmptyCard
import com.example.mytestapplication.ui.theme.HomeLotusBlue
import com.example.mytestapplication.ui.theme.HomeLotusPurple
import com.example.mytestapplication.ui.theme.HomeNavActiveBg
import com.example.mytestapplication.ui.theme.HomeNavInactive
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private data class EmptyHomeCategory(
    val title: String,
    @DrawableRes val imageRes: Int,
    val iconType: CategoryIconType,
)

private enum class CategoryIconType {
    Lotus,
    Face,
    Nails,
    Yoga,
}

private enum class EmptyHomeNavType {
    Home,
    Services,
    Bookings,
    Profile,
}

@Composable
fun EmptyHomeScreen(
    modifier: Modifier = Modifier,
    userName: String = "Alex",
    greeting: String = "Good morning",
    onNotificationClick: () -> Unit = {},
    onExploreServicesClick: () -> Unit = {},
    onClaimOfferClick: () -> Unit = {},
    onViewAllCategories: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    var selectedNav by remember { mutableIntStateOf(0) }

    val categories = remember {
        listOf(
            EmptyHomeCategory("Massage", R.drawable.cat_massage, CategoryIconType.Lotus),
            EmptyHomeCategory("Facials", R.drawable.cat_facial, CategoryIconType.Face),
            EmptyHomeCategory("Nails", R.drawable.cat_nails, CategoryIconType.Nails),
            EmptyHomeCategory("Wellness", R.drawable.home_promo, CategoryIconType.Yoga),
        )
    }

    val navItems = remember {
        listOf(
            "Home" to EmptyHomeNavType.Home,
            "Services" to EmptyHomeNavType.Services,
            "Bookings" to EmptyHomeNavType.Bookings,
            "Profile" to EmptyHomeNavType.Profile,
        )
    }

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

            // Header: pin | DIVA | bell
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.weight(1f)
                ) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round)
                        drawCircle(
                            color = WelcomeGold,
                            radius = size.minDimension * 0.18f,
                            center = Offset(size.width / 2f, size.height * 0.36f)
                        )
                        val pin = Path().apply {
                            moveTo(size.width * 0.5f, size.height * 0.92f)
                            quadraticTo(
                                size.width * 0.1f,
                                size.height * 0.55f,
                                size.width * 0.5f,
                                size.height * 0.12f
                            )
                            quadraticTo(
                                size.width * 0.9f,
                                size.height * 0.55f,
                                size.width * 0.5f,
                                size.height * 0.92f
                            )
                            close()
                        }
                        drawPath(pin, color = WelcomeGold, style = stroke)
                    }
                }

                Text(
                    text = "DIVA",
                    color = WelcomeGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onNotificationClick)
                    ) {
                        Canvas(modifier = Modifier.size(20.dp)) {
                            val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round)
                            drawPath(
                                path = Path().apply {
                                    moveTo(size.width * 0.2f, size.height * 0.42f)
                                    quadraticTo(
                                        size.width * 0.2f,
                                        size.height * 0.18f,
                                        size.width * 0.5f,
                                        size.height * 0.18f
                                    )
                                    quadraticTo(
                                        size.width * 0.8f,
                                        size.height * 0.18f,
                                        size.width * 0.8f,
                                        size.height * 0.42f
                                    )
                                    lineTo(size.width * 0.8f, size.height * 0.62f)
                                    lineTo(size.width * 0.92f, size.height * 0.72f)
                                    lineTo(size.width * 0.08f, size.height * 0.72f)
                                    lineTo(size.width * 0.2f, size.height * 0.62f)
                                    close()
                                },
                                color = WelcomeGold,
                                style = stroke
                            )
                            drawArc(
                                color = WelcomeGold,
                                startAngle = 20f,
                                sweepAngle = 140f,
                                useCenter = false,
                                topLeft = Offset(size.width * 0.35f, size.height * 0.72f),
                                size = Size(size.width * 0.3f, size.height * 0.2f),
                                style = stroke
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "$greeting, $userName.",
                color = HomeTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ready to pamper yourself today?",
                color = HomeSubtitle,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Empty state card
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = Color.Black.copy(alpha = 0.05f)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(HomeEmptyCard)
                    .padding(horizontal = 22.dp, vertical = 28.dp)
            ) {
                LotusWelcomeGraphic()

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Your Journey Begins Here",
                    color = HomeTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "You haven't booked any services yet. Discover our premium wellness offerings and schedule your first session to start feeling your best.",
                    color = HomeSubtitle,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onExploreServicesClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HomeAccent,
                        contentColor = OtpWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    )
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
                        drawCircle(
                            color = OtpWhite,
                            radius = size.minDimension * 0.38f,
                            center = Offset(size.width / 2f, size.height / 2f),
                            style = stroke
                        )
                        drawLine(
                            color = OtpWhite,
                            start = Offset(size.width * 0.5f, size.height * 0.22f),
                            end = Offset(size.width * 0.5f, size.height * 0.78f),
                            strokeWidth = 1.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = OtpWhite,
                            start = Offset(size.width * 0.22f, size.height * 0.5f),
                            end = Offset(size.width * 0.78f, size.height * 0.5f),
                            strokeWidth = 1.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = OtpWhite,
                            start = Offset(size.width * 0.3f, size.height * 0.3f),
                            end = Offset(size.width * 0.7f, size.height * 0.7f),
                            strokeWidth = 1.2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Explore Services",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Promo banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_promo),
                    contentDescription = "First massage offer",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.55f),
                                    Color.Black.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(0.78f)
                ) {
                    Text(
                        text = "20% Off Your First Massage",
                        color = WelcomeGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 24.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Experience ultimate relaxation with our signature therapy.",
                        color = OtpWhite.copy(alpha = 0.92f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.22f))
                            .clickable(onClick = onClaimOfferClick)
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = "Claim Offer",
                            color = OtpWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Browse by Category",
                    color = HomeTitle,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "View All",
                    color = WelcomeGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(onClick = onViewAllCategories)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.take(2).forEach { category ->
                        CategoryTile(
                            category = category,
                            onClick = { onCategoryClick(category.title) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.drop(2).forEach { category ->
                        CategoryTile(
                            category = category,
                            onClick = { onCategoryClick(category.title) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Bottom nav
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(OtpWhite)
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            navItems.forEachIndexed { index, (title, type) ->
                val selected = selectedNav == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            selectedNav = index
                            onNavClick(title)
                        }
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .then(
                                if (selected) {
                                    Modifier
                                        .clip(CircleShape)
                                        .background(HomeNavActiveBg)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        EmptyHomeNavIcon(
                            type = type,
                            tint = if (selected) WelcomeGold else HomeNavInactive
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = title,
                        color = if (selected) HomeTitle else HomeNavInactive,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun LotusWelcomeGraphic() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(120.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Soft circle background
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        HomeLotusBlue.copy(alpha = 0.35f),
                        HomeLotusPurple.copy(alpha = 0.18f),
                        Color.Transparent
                    )
                ),
                radius = size.minDimension * 0.48f
            )
            drawCircle(
                color = OtpWhite.copy(alpha = 0.55f),
                radius = size.minDimension * 0.42f
            )

            val center = Offset(size.width / 2f, size.height * 0.52f)
            val petalCount = 6
            for (i in 0 until petalCount) {
                val angle = Math.toRadians((-90.0 + i * (360.0 / petalCount)))
                val petalCenter = Offset(
                    center.x + (size.minDimension * 0.16f * Math.cos(angle)).toFloat(),
                    center.y + (size.minDimension * 0.14f * Math.sin(angle)).toFloat()
                )
                drawOval(
                    color = if (i % 2 == 0) HomeLotusPurple.copy(alpha = 0.85f)
                    else HomeLotusBlue.copy(alpha = 0.8f),
                    topLeft = Offset(
                        petalCenter.x - size.minDimension * 0.1f,
                        petalCenter.y - size.minDimension * 0.16f
                    ),
                    size = Size(size.minDimension * 0.2f, size.minDimension * 0.32f)
                )
            }
            drawCircle(
                color = OtpWhite,
                radius = size.minDimension * 0.07f,
                center = center
            )
            drawCircle(
                color = HomeLotusPurple.copy(alpha = 0.7f),
                radius = size.minDimension * 0.035f,
                center = center
            )
        }
    }
}

@Composable
private fun CategoryTile(
    category: EmptyHomeCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1.05f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = category.imageRes),
            contentDescription = category.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.55f)
                        )
                    )
                )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            CategoryIcon(type = category.iconType, tint = OtpWhite)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = category.title,
                color = OtpWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CategoryIcon(
    type: CategoryIconType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(14.dp)) {
        val stroke = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            CategoryIconType.Lotus -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.12f,
                    center = Offset(size.width * 0.5f, size.height * 0.55f)
                )
                for (i in 0 until 5) {
                    val angle = Math.toRadians((-90.0 + i * 72.0))
                    val cx = size.width * 0.5f + (size.minDimension * 0.22f * Math.cos(angle)).toFloat()
                    val cy = size.height * 0.48f + (size.minDimension * 0.2f * Math.sin(angle)).toFloat()
                    drawCircle(
                        color = tint,
                        radius = size.minDimension * 0.13f,
                        center = Offset(cx, cy),
                        style = stroke
                    )
                }
            }

            CategoryIconType.Face -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.36f,
                    center = Offset(size.width / 2f, size.height / 2f),
                    style = stroke
                )
                drawCircle(color = tint, radius = 1.2.dp.toPx(), center = Offset(size.width * 0.38f, size.height * 0.42f))
                drawCircle(color = tint, radius = 1.2.dp.toPx(), center = Offset(size.width * 0.62f, size.height * 0.42f))
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.32f, size.height * 0.48f),
                    size = Size(size.width * 0.36f, size.height * 0.28f),
                    style = stroke
                )
            }

            CategoryIconType.Nails -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.28f, size.height * 0.15f),
                    size = Size(size.width * 0.44f, size.height * 0.7f),
                    cornerRadius = CornerRadius(size.width * 0.22f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.35f, size.height * 0.55f),
                    end = Offset(size.width * 0.65f, size.height * 0.55f),
                    strokeWidth = 1.2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            CategoryIconType.Yoga -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.12f,
                    center = Offset(size.width * 0.5f, size.height * 0.22f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.34f),
                    end = Offset(size.width * 0.5f, size.height * 0.62f),
                    strokeWidth = 1.4.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.22f, size.height * 0.48f),
                    end = Offset(size.width * 0.78f, size.height * 0.48f),
                    strokeWidth = 1.4.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.62f),
                    end = Offset(size.width * 0.28f, size.height * 0.88f),
                    strokeWidth = 1.4.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.5f, size.height * 0.62f),
                    end = Offset(size.width * 0.72f, size.height * 0.88f),
                    strokeWidth = 1.4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
private fun EmptyHomeNavIcon(
    type: EmptyHomeNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            EmptyHomeNavType.Home -> {
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

            EmptyHomeNavType.Services -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.12f,
                    center = Offset(size.width * 0.5f, size.height * 0.52f)
                )
                for (i in 0 until 5) {
                    val angle = Math.toRadians((-90.0 + i * 72.0))
                    val cx = size.width * 0.5f + (size.minDimension * 0.22f * Math.cos(angle)).toFloat()
                    val cy = size.height * 0.48f + (size.minDimension * 0.22f * Math.sin(angle)).toFloat()
                    drawCircle(
                        color = tint,
                        radius = size.minDimension * 0.14f,
                        center = Offset(cx, cy),
                        style = stroke
                    )
                }
            }

            EmptyHomeNavType.Bookings -> {
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

            EmptyHomeNavType.Profile -> {
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
private fun EmptyHomeScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        EmptyHomeScreen()
    }
}
