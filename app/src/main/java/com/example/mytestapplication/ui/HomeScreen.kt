package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.data.api.dto.CategoryDto
import com.example.mytestapplication.R
import com.example.mytestapplication.ui.theme.HomeAccent
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeNavInactive
import com.example.mytestapplication.ui.theme.HomeSearchBg
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.ServiceStar
import com.example.mytestapplication.ui.theme.WelcomeGold

private enum class HomeCategoryIcon {
    Hair,
    Nails,
    Facial,
    Massage,
}

private data class HomeCategoryItem(
    val title: String,
    val icon: HomeCategoryIcon,
)

private data class FeaturedServiceCard(
    val service: ServiceInfo,
)

private enum class HomeNavType {
    Home,
    Services,
    Bookings,
    Profile,
}

private data class HomeNavItem(
    val title: String,
    val type: HomeNavType,
)

private fun categoryIconFor(slug: String): HomeCategoryIcon = when (slug.lowercase()) {
    "nails" -> HomeCategoryIcon.Nails
    "facial" -> HomeCategoryIcon.Facial
    "massage" -> HomeCategoryIcon.Massage
    else -> HomeCategoryIcon.Hair
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    greeting: String = "Welcome",
    location: String = "Locating...",
    onLocationClick: () -> Unit = {},
    categories: List<CategoryDto> = emptyList(),
    featuredServices: List<ServiceInfo> = emptyList(),
    onFilterClick: () -> Unit = {},
    onSearchSubmit: (String) -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onSeeAllTrending: () -> Unit = {},
    onFeaturedClick: (ServiceInfo) -> Unit = {},
    onTrendingBookClick: (ServiceInfo) -> Unit = {},
    onNavClick: (String) -> Unit = {},
    onFavoriteClick: (ServiceInfo) -> Unit = {},
    onPromoClick: () -> Unit = {},
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableIntStateOf(-1) }
    var promoPage by remember { mutableIntStateOf(0) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun submitSearch() {
        val query = searchQuery.trim()
        if (query.isEmpty()) return
        focusManager.clearFocus()
        keyboardController?.hide()
        onSearchSubmit(query)
    }

    val categoryItems = remember(categories) {
        categories.map {
            HomeCategoryItem(it.name, categoryIconFor(it.slug))
        }.ifEmpty {
            listOf(
                HomeCategoryItem("Hair", HomeCategoryIcon.Hair),
                HomeCategoryItem("Nails", HomeCategoryIcon.Nails),
                HomeCategoryItem("Facial", HomeCategoryIcon.Facial),
                HomeCategoryItem("Massage", HomeCategoryIcon.Massage),
            )
        }
    }

    val featured = remember(featuredServices, categories, selectedCategory) {
        val selected = categories.getOrNull(selectedCategory)
        featuredServices
            .filter { service ->
                selected == null ||
                    service.categorySlug.equals(selected.slug, ignoreCase = true) ||
                    service.categoryName.equals(selected.name, ignoreCase = true)
            }
            .map { FeaturedServiceCard(it) }
    }

    val navItems = remember {
        listOf(
            HomeNavItem("Home", HomeNavType.Home),
            HomeNavItem("Services", HomeNavType.Services),
            HomeNavItem("Bookings", HomeNavType.Bookings),
            HomeNavItem("Profile", HomeNavType.Profile),
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
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onLocationClick)
                ) {
                    Canvas(modifier = Modifier.size(14.dp)) {
                        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
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
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        color = HomeSubtitle,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "DIVA",
                    color = WelcomeGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 1.5.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
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
                                    lineTo(size.width * 0.88f, size.height * 0.72f)
                                    lineTo(size.width * 0.12f, size.height * 0.72f)
                                    lineTo(size.width * 0.2f, size.height * 0.62f)
                                    close()
                                },
                                color = HomeAccent,
                                style = stroke
                            )
                            drawLine(
                                color = HomeAccent,
                                start = Offset(size.width * 0.42f, size.height * 0.78f),
                                end = Offset(size.width * 0.58f, size.height * 0.78f),
                                strokeWidth = 1.7.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                            drawCircle(
                                color = WelcomeGold,
                                radius = size.minDimension * 0.08f,
                                center = Offset(size.width * 0.72f, size.height * 0.22f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Hello, Beautiful",
                color = HomeTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "What would you like to do today?",
                color = HomeSubtitle,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    textStyle = TextStyle(color = HomeTitle, fontSize = 14.sp),
                    cursorBrush = SolidColor(WelcomeGold),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { submitSearch() }),
                    decorationBox = { inner ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(HomeSearchBg)
                                .padding(horizontal = 14.dp)
                        ) {
                            Canvas(modifier = Modifier.size(16.dp)) {
                                val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
                                drawCircle(
                                    color = HomeSubtitle,
                                    radius = size.minDimension * 0.28f,
                                    center = Offset(size.width * 0.42f, size.height * 0.42f),
                                    style = stroke
                                )
                                drawLine(
                                    color = HomeSubtitle,
                                    start = Offset(size.width * 0.6f, size.height * 0.6f),
                                    end = Offset(size.width * 0.82f, size.height * 0.82f),
                                    strokeWidth = 1.6.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(modifier = Modifier.weight(1f)) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Search for services...",
                                        color = HomeSubtitle,
                                        fontSize = 14.sp
                                    )
                                }
                                inner()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF6B5A3E))
                        .clickable(onClick = onFilterClick)
                ) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        val stroke = 1.8.dp.toPx()
                        drawLine(
                            color = OtpWhite,
                            start = Offset(size.width * 0.15f, size.height * 0.3f),
                            end = Offset(size.width * 0.85f, size.height * 0.3f),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = OtpWhite,
                            start = Offset(size.width * 0.25f, size.height * 0.5f),
                            end = Offset(size.width * 0.75f, size.height * 0.5f),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = OtpWhite,
                            start = Offset(size.width * 0.35f, size.height * 0.7f),
                            end = Offset(size.width * 0.65f, size.height * 0.7f),
                            strokeWidth = stroke,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .shadow(6.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(onClick = onPromoClick)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat_facial),
                    contentDescription = "Promo",
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
                                    Color.Black.copy(alpha = 0.15f),
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(20.dp)
                        .fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = "50% Off Luxury Facials",
                        color = OtpWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Rejuvenate your skin with our premium treatments.",
                        color = OtpWhite.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(if (index == promoPage) 8.dp else 6.dp)
                                .clip(CircleShape)
                                .background(
                                    if (index == promoPage) OtpWhite
                                    else OtpWhite.copy(alpha = 0.45f)
                                )
                                .clickable { promoPage = index }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Categories",
                    color = HomeTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "See All",
                    color = WelcomeGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(onClick = onSeeAllTrending)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                categoryItems.forEachIndexed { index, category ->
                    val selected = selectedCategory == index
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                selectedCategory = if (selectedCategory == index) -1 else index
                            }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(58.dp)
                                .clip(CircleShape)
                                .background(if (selected) Color(0xFF6B5A3E) else OtpWhite)
                                .then(
                                    if (!selected) Modifier.border(1.dp, Color(0xFFE8E4DF), CircleShape)
                                    else Modifier
                                )
                        ) {
                            CategoryIconCanvas(
                                icon = category.icon,
                                tint = if (selected) OtpWhite else Color(0xFF6B5A3E)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = category.title,
                            color = if (selected) HomeTitle else HomeSubtitle,
                            fontSize = 12.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Featured Services",
                color = HomeTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (featured.isEmpty()) {
                Text(
                    text = "No services in this category.",
                    color = HomeSubtitle,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            featured.forEach { card ->
                FeaturedServiceCardView(
                    service = card.service,
                    onClick = { onFeaturedClick(card.service) },
                    onFavoriteClick = { onFavoriteClick(card.service) }
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
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
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            navItems.forEach { item ->
                val selected = item.type == HomeNavType.Home
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavClick(item.title) }
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .then(
                                if (selected) {
                                    Modifier
                                        .clip(CircleShape)
                                        .background(Color(0xFF6B5A3E))
                                } else Modifier
                            )
                    ) {
                        HomeNavIcon(
                            type = item.type,
                            tint = if (selected) OtpWhite else HomeNavInactive
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.title,
                        color = if (selected) Color(0xFF6B5A3E) else HomeNavInactive,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturedServiceCardView(
    service: ServiceInfo,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(OtpWhite)
            .clickable(onClick = onClick)
    ) {
        Box {
            ServiceImage(
                service = service,
                contentDescription = service.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onFavoriteClick,
                    )
            ) {
                Canvas(modifier = Modifier.size(16.dp)) {
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
                    val heartColor = Color(0xFF6B5A3E)
                    if (service.isFavorited) {
                        drawPath(path = heart, color = heartColor, style = Fill)
                    } else {
                        drawPath(
                            path = heart,
                            color = heartColor,
                            style = Stroke(width = 1.6.dp.toPx(), join = StrokeJoin.Round)
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = service.title,
                    color = HomeTitle,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = service.price,
                    color = WelcomeGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Canvas(modifier = Modifier.size(12.dp)) {
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
                    drawPath(path, color = ServiceStar)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = service.ratingLabel,
                    color = HomeSubtitle,
                    fontSize = 12.sp
                )
                if (service.duration.isNotBlank()) {
                    Text(
                        text = "  ·  ${service.duration}",
                        color = HomeSubtitle,
                        fontSize = 12.sp
                    )
                }
                if (!service.categoryName.isNullOrBlank()) {
                    Text(
                        text = "  ·  ${service.categoryName}",
                        color = HomeSubtitle,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.shortDescription.ifBlank { service.description },
                color = HomeSubtitle,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CategoryIconCanvas(
    icon: HomeCategoryIcon,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val stroke = Stroke(width = 1.7.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (icon) {
            HomeCategoryIcon.Hair -> {
                // Scissors
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.14f,
                    center = Offset(size.width * 0.32f, size.height * 0.28f),
                    style = stroke
                )
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.14f,
                    center = Offset(size.width * 0.68f, size.height * 0.28f),
                    style = stroke
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.38f, size.height * 0.38f),
                    end = Offset(size.width * 0.7f, size.height * 0.82f),
                    strokeWidth = 1.7.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.62f, size.height * 0.38f),
                    end = Offset(size.width * 0.3f, size.height * 0.82f),
                    strokeWidth = 1.7.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            HomeCategoryIcon.Nails -> {
                val hand = Path().apply {
                    moveTo(size.width * 0.28f, size.height * 0.7f)
                    lineTo(size.width * 0.28f, size.height * 0.42f)
                    quadraticTo(
                        size.width * 0.28f, size.height * 0.22f,
                        size.width * 0.45f, size.height * 0.22f
                    )
                    lineTo(size.width * 0.5f, size.height * 0.12f)
                    lineTo(size.width * 0.55f, size.height * 0.22f)
                    lineTo(size.width * 0.62f, size.height * 0.18f)
                    lineTo(size.width * 0.66f, size.height * 0.28f)
                    lineTo(size.width * 0.74f, size.height * 0.28f)
                    lineTo(size.width * 0.74f, size.height * 0.7f)
                    close()
                }
                drawPath(hand, color = tint, style = stroke)
            }

            HomeCategoryIcon.Facial -> {
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.32f,
                    center = Offset(size.width / 2f, size.height * 0.48f),
                    style = stroke
                )
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.05f,
                    center = Offset(size.width * 0.38f, size.height * 0.42f)
                )
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.05f,
                    center = Offset(size.width * 0.62f, size.height * 0.42f)
                )
                drawArc(
                    color = tint,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.35f, size.height * 0.48f),
                    size = Size(size.width * 0.3f, size.height * 0.22f),
                    style = stroke
                )
            }

            HomeCategoryIcon.Massage -> {
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
                        radius = size.minDimension * 0.13f,
                        center = Offset(cx, cy),
                        style = stroke
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeNavIcon(
    type: HomeNavType,
    tint: Color,
) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            HomeNavType.Home -> {
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

            HomeNavType.Services -> {
                // Lotus / flower style icon
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

            HomeNavType.Bookings -> {
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

            HomeNavType.Profile -> {
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
private fun HomeScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        HomeScreen()
    }
}
