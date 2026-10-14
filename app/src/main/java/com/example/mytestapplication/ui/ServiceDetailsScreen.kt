package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.mytestapplication.R
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.ServiceAccent
import com.example.mytestapplication.ui.theme.ServiceAccentSoft
import com.example.mytestapplication.ui.theme.ServiceBg
import com.example.mytestapplication.ui.theme.ServiceBody
import com.example.mytestapplication.ui.theme.ServiceButton
import com.example.mytestapplication.ui.theme.ServiceCard
import com.example.mytestapplication.ui.theme.ServiceNotIncluded
import com.example.mytestapplication.ui.theme.ServiceNotIncludedSoft
import com.example.mytestapplication.ui.theme.ServiceStar
import com.example.mytestapplication.ui.theme.ServiceTitle


@Composable
fun ServiceDetailsScreen(
    service: ServiceInfo = ServiceSamples.preview,
    reviews: List<ReviewUi> = emptyList(),
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onViewAllProfessionals: () -> Unit = {},
    onBookNowClick: () -> Unit = {},
) {
    val professionals = service.professionals

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ServiceBg)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp))
            ) {
                ServiceImage(
                    service = service,
                    contentDescription = service.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0x99000000))
                        .clickable(onClick = onBackClick)
                        .align(Alignment.TopStart)
                ) {
                    Canvas(modifier = Modifier.size(18.dp)) {
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
                        drawPath(path = path, color = OtpWhite, style = stroke)
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = service.title,
                        color = Color(0xFF6B5A3E),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        lineHeight = 30.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = service.price,
                        color = ServiceTitle,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(14.dp)) {
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
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = service.ratingLabel,
                        color = ServiceBody,
                        fontSize = 13.sp
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(ServiceBody.copy(alpha = 0.5f))
                    )

                    Canvas(modifier = Modifier.size(14.dp)) {
                        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
                        drawCircle(
                            color = ServiceBody,
                            radius = size.minDimension * 0.38f,
                            center = Offset(size.width / 2f, size.height / 2f),
                            style = stroke
                        )
                        drawLine(
                            color = ServiceBody,
                            start = Offset(size.width * 0.5f, size.height * 0.28f),
                            end = Offset(size.width * 0.5f, size.height * 0.52f),
                            strokeWidth = 1.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = ServiceBody,
                            start = Offset(size.width * 0.5f, size.height * 0.52f),
                            end = Offset(size.width * 0.68f, size.height * 0.62f),
                            strokeWidth = 1.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = service.duration,
                        color = ServiceBody,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (!service.categoryName.isNullOrBlank()) {
                    Text(
                        text = service.categoryName,
                        color = ServiceAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (service.shortDescription.isNotBlank()) {
                    Text(
                        text = service.shortDescription,
                        color = ServiceTitle,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                val longDescription = service.description
                    .takeIf { it.isNotBlank() && it != service.shortDescription }
                if (longDescription != null) {
                    Text(
                        text = "Description",
                        color = Color(0xFF6B5A3E),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = longDescription,
                        color = ServiceBody,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(18.dp))

                if (service.included.isNotEmpty()) {
                    InclusionCard(
                        title = "Included",
                        items = service.included,
                        isIncluded = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (service.notIncluded.isNotEmpty()) {
                    InclusionCard(
                        title = "Not Included",
                        items = service.notIncluded,
                        isIncluded = false
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (reviews.isNotEmpty()) {
                    Text(
                        text = "Reviews",
                        color = Color(0xFF6B5A3E),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    reviews.forEach { review ->
                        Column(modifier = Modifier.padding(bottom = 12.dp)) {
                            Text(
                                text = "${review.author}  ·  ${"★".repeat(review.rating.coerceIn(0, 5))}  ·  ${review.date}",
                                color = ServiceTitle,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            if (review.body.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = review.body,
                                    color = ServiceBody,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Available Professionals",
                        color = Color(0xFF6B5A3E),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "View All",
                        color = Color(0xFF6B5A3E),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(onClick = onViewAllProfessionals)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    professionals.forEach { pro ->
                        ProfessionalCard(
                            professional = pro,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Button(
            onClick = onBookNowClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .height(54.dp),
            shape = RoundedCornerShape(28.dp),
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
                text = "Book Now",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun InclusionCard(
    title: String,
    items: List<String>,
    isIncluded: Boolean,
) {
    val accent = if (isIncluded) ServiceAccent else ServiceNotIncluded
    val soft = if (isIncluded) ServiceAccentSoft else ServiceNotIncludedSoft

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(soft)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(OtpWhite.copy(alpha = 0.7f))
            ) {
                Canvas(modifier = Modifier.size(14.dp)) {
                    val stroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                    if (isIncluded) {
                        val path = Path().apply {
                            moveTo(size.width * 0.18f, size.height * 0.52f)
                            lineTo(size.width * 0.42f, size.height * 0.72f)
                            lineTo(size.width * 0.82f, size.height * 0.28f)
                        }
                        drawPath(path, color = accent, style = stroke)
                    } else {
                        drawLine(
                            color = accent,
                            start = Offset(size.width * 0.25f, size.height * 0.25f),
                            end = Offset(size.width * 0.75f, size.height * 0.75f),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = accent,
                            start = Offset(size.width * 0.75f, size.height * 0.25f),
                            end = Offset(size.width * 0.25f, size.height * 0.75f),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                color = accent,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Canvas(modifier = Modifier.size(14.dp).padding(top = 2.dp)) {
                    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                    if (isIncluded) {
                        val path = Path().apply {
                            moveTo(size.width * 0.15f, size.height * 0.52f)
                            lineTo(size.width * 0.4f, size.height * 0.72f)
                            lineTo(size.width * 0.85f, size.height * 0.28f)
                        }
                        drawPath(path, color = accent, style = stroke)
                    } else {
                        drawLine(
                            color = accent,
                            start = Offset(size.width * 0.22f, size.height * 0.22f),
                            end = Offset(size.width * 0.78f, size.height * 0.78f),
                            strokeWidth = 1.8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = accent,
                            start = Offset(size.width * 0.78f, size.height * 0.22f),
                            end = Offset(size.width * 0.22f, size.height * 0.78f),
                            strokeWidth = 1.8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = item,
                    color = ServiceBody,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ProfessionalCard(
    professional: ProfessionalInfo,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(ServiceCard)
            .padding(vertical = 14.dp, horizontal = 10.dp)
    ) {
        ProfessionalImage(
            professional = professional,
            contentDescription = professional.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = professional.name,
            color = ServiceTitle,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = professional.role,
            color = ServiceBody,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
        )
        if (professional.bio.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = professional.bio,
                color = ServiceBody,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "★ ${professional.rating} (${professional.reviewCount})",
            color = ServiceStar,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ServiceDetailsScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        ServiceDetailsScreen()
    }
}
