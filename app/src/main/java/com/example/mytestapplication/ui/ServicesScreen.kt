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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeNavInactive
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class ServicesNavType {
    Home,
    Services,
    Bookings,
    Profile,
}

private data class ServicesNavItem(
    val title: String,
    val type: ServicesNavType,
)

private data class ServiceCategoryFilter(
    val label: String,
    val matches: (ServiceInfo) -> Boolean,
)

@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    services: List<ServiceInfo> = ServiceCatalog.all,
    onServiceClick: (ServiceInfo) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    var selectedFilter by remember { mutableIntStateOf(0) }

    val filters = remember {
        listOf(
            ServiceCategoryFilter("All") { true },
            ServiceCategoryFilter("Makeup") { it.id.contains("makeup") || it.title.contains("Makeup", true) },
            ServiceCategoryFilter("Facial") { it.id.contains("facial") || it.title.contains("Facial", true) },
            ServiceCategoryFilter("Hair") {
                it.id.contains("hair") || it.id.contains("balayage") ||
                    it.title.contains("Hair", true) || it.title.contains("Balayage", true)
            },
            ServiceCategoryFilter("Massage") {
                it.id.contains("massage") || it.id.contains("tissue") || it.id.contains("stone") ||
                    it.title.contains("Massage", true)
            },
        )
    }

    val navItems = remember {
        listOf(
            ServicesNavItem("Home", ServicesNavType.Home),
            ServicesNavItem("Services", ServicesNavType.Services),
            ServicesNavItem("Bookings", ServicesNavType.Bookings),
            ServicesNavItem("Profile", ServicesNavType.Profile),
        )
    }

    val filtered = remember(selectedFilter, services) {
        filters[selectedFilter].matches.let { predicate -> services.filter(predicate) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HomeBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Services",
                color = WelcomeGold,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onSearchClick)
                    .align(Alignment.CenterEnd)
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round)
                    drawCircle(
                        color = HomeTitle,
                        radius = size.minDimension * 0.32f,
                        center = Offset(size.width * 0.4f, size.height * 0.4f),
                        style = stroke
                    )
                    drawLine(
                        color = HomeTitle,
                        start = Offset(size.width * 0.62f, size.height * 0.62f),
                        end = Offset(size.width * 0.85f, size.height * 0.85f),
                        strokeWidth = 1.7.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Browse our premium beauty & wellness menu",
                color = HomeSubtitle,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.forEachIndexed { index, filter ->
                    val selected = selectedFilter == index
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (selected) Color(0xFF6B5A3E) else Color(0xFFF0EEEC))
                            .clickable { selectedFilter = index }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filter.label,
                            color = if (selected) OtpWhite else HomeSubtitle,
                            fontSize = 12.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (filtered.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 64.dp)
                ) {
                    Text(
                        text = "No services in this category",
                        color = HomeSubtitle,
                        fontSize = 14.sp
                    )
                }
            } else {
                filtered.forEach { service ->
                    ServiceCatalogCard(
                        service = service,
                        onClick = { onServiceClick(service) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(OtpWhite)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            navItems.forEach { item ->
                val selected = item.type == ServicesNavType.Services
                val tint = if (selected) OtpWhite else HomeNavInactive
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) Color(0xFF6B5A3E) else Color.Transparent)
                        .clickable { onNavClick(item.title) }
                        .padding(
                            horizontal = if (selected) 14.dp else 10.dp,
                            vertical = 8.dp
                        )
                ) {
                    ServicesNavIcon(type = item.type, tint = tint)
                    if (selected) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.title,
                            color = OtpWhite,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceCatalogCard(
    service: ServiceInfo,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(OtpWhite)
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = service.imageRes),
            contentDescription = service.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(84.dp)
                .clip(RoundedCornerShape(14.dp))
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = service.title,
                color = HomeTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = service.shortDescription,
                color = HomeSubtitle,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = service.price,
                    color = WelcomeGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFFE8E4DF), RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = service.duration,
                        color = HomeSubtitle,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Canvas(modifier = Modifier.size(16.dp)) {
            drawLine(
                color = HomeSubtitle,
                start = Offset(size.width * 0.35f, size.height * 0.2f),
                end = Offset(size.width * 0.7f, size.height * 0.5f),
                strokeWidth = 1.8.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                color = HomeSubtitle,
                start = Offset(size.width * 0.35f, size.height * 0.8f),
                end = Offset(size.width * 0.7f, size.height * 0.5f),
                strokeWidth = 1.8.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun ServicesNavIcon(
    type: ServicesNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            ServicesNavType.Home -> {
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

            ServicesNavType.Services -> {
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

            ServicesNavType.Bookings -> {
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

            ServicesNavType.Profile -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.16f,
                    center = Offset(size.width * 0.5f, size.height * 0.32f),
                    style = stroke
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.22f, size.height * 0.48f),
                    size = Size(size.width * 0.56f, size.height * 0.4f),
                    style = stroke
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ServicesScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        ServicesScreen()
    }
}
