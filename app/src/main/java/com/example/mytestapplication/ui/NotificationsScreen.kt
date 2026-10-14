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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.data.api.dto.NotificationDto
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

enum class NotificationType {
    Booking,
    Offer,
    System,
}

data class AppNotification(
    val id: String,
    val title: String,
    val body: String,
    val time: String,
    val type: NotificationType,
    val unread: Boolean = false,
    val bookingId: Int? = null,
)

fun NotificationDto.toUiItem(): AppNotification {
    val rawType = type.orEmpty().lowercase()
    val mappedType = when {
        rawType.contains("booking") -> NotificationType.Booking
        rawType.contains("offer") || rawType.contains("promo") -> NotificationType.Offer
        else -> NotificationType.System
    }
    return AppNotification(
        id = id,
        title = title?.takeIf { it.isNotBlank() } ?: "Notification",
        body = body.orEmpty(),
        time = formatNotificationTime(createdAt),
        type = mappedType,
        unread = readAt.isNullOrBlank(),
        bookingId = bookingId,
    )
}

private fun formatNotificationTime(raw: String?): String {
    val dt = parseApiDateTime(raw) ?: return ""
    return dt.format(java.time.format.DateTimeFormatter.ofPattern("MMM d, h:mm a"))
}

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    notifications: List<AppNotification> = emptyList(),
    isLoading: Boolean = false,
    onBackClick: () -> Unit = {},
    onNotificationClick: (AppNotification) -> Unit = {},
    onMarkAllRead: () -> Unit = {},
) {
    var selectedFilter by remember { mutableIntStateOf(0) }
    val filters = remember { listOf("All", "Bookings", "Offers", "System") }

    val filtered = remember(selectedFilter, notifications) {
        when (selectedFilter) {
            1 -> notifications.filter { it.type == NotificationType.Booking }
            2 -> notifications.filter { it.type == NotificationType.Offer }
            3 -> notifications.filter { it.type == NotificationType.System }
            else -> notifications
        }
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
                .padding(horizontal = 16.dp, vertical = 10.dp)
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
                text = "Notifications",
                color = WelcomeGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )

            Text(
                text = "Mark all",
                color = Color(0xFF6B5A3E),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = onMarkAllRead)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.forEachIndexed { index, label ->
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
                            text = label,
                            color = if (selected) OtpWhite else HomeSubtitle,
                            fontSize = 12.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading && notifications.isEmpty() -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 64.dp)
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF6B5A3E),
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(28.dp),
                        )
                    }
                }

                filtered.isEmpty() -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 64.dp)
                    ) {
                        Text(
                            text = "No notifications here",
                            color = HomeSubtitle,
                            fontSize = 14.sp
                        )
                    }
                }

                else -> {
                    filtered.forEach { item ->
                        NotificationRow(
                            notification = item,
                            onClick = { onNotificationClick(item) }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun NotificationRow(
    notification: AppNotification,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.04f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(if (notification.unread) Color(0xFFF8F4EC) else OtpWhite)
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(
                    when (notification.type) {
                        NotificationType.Booking -> Color(0xFFF0E6D4)
                        NotificationType.Offer -> Color(0xFFEDE4F5)
                        NotificationType.System -> Color(0xFFE8E4DF)
                    }
                )
        ) {
            NotificationTypeIcon(type = notification.type)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = notification.title,
                    color = HomeTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (notification.unread) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF6B5A3E))
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.body,
                color = HomeSubtitle,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = notification.time,
                color = HomeSubtitle.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun NotificationTypeIcon(type: NotificationType) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val tint = Color(0xFF6B5A3E)
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        when (type) {
            NotificationType.Booking -> {
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
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.7f, size.height * 0.18f),
                    end = Offset(size.width * 0.7f, size.height * 0.3f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            NotificationType.Offer -> {
                val tag = Path().apply {
                    moveTo(size.width * 0.18f, size.height * 0.42f)
                    lineTo(size.width * 0.18f, size.height * 0.22f)
                    lineTo(size.width * 0.55f, size.height * 0.22f)
                    lineTo(size.width * 0.85f, size.height * 0.52f)
                    lineTo(size.width * 0.55f, size.height * 0.82f)
                    lineTo(size.width * 0.18f, size.height * 0.82f)
                    close()
                }
                drawPath(tag, color = tint, style = stroke)
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.07f,
                    center = Offset(size.width * 0.34f, size.height * 0.4f)
                )
            }

            NotificationType.System -> {
                val path = Path().apply {
                    moveTo(size.width * 0.2f, size.height * 0.42f)
                    quadraticTo(
                        size.width * 0.2f, size.height * 0.18f,
                        size.width * 0.5f, size.height * 0.18f
                    )
                    quadraticTo(
                        size.width * 0.8f, size.height * 0.18f,
                        size.width * 0.8f, size.height * 0.42f
                    )
                    lineTo(size.width * 0.8f, size.height * 0.62f)
                    lineTo(size.width * 0.88f, size.height * 0.72f)
                    lineTo(size.width * 0.12f, size.height * 0.72f)
                    lineTo(size.width * 0.2f, size.height * 0.62f)
                    close()
                }
                drawPath(path, color = tint, style = stroke)
                drawLine(
                    color = tint,
                    start = Offset(size.width * 0.42f, size.height * 0.78f),
                    end = Offset(size.width * 0.58f, size.height * 0.78f),
                    strokeWidth = 1.6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotificationsScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        NotificationsScreen(
            notifications = listOf(
                AppNotification(
                    id = "1",
                    title = "Booking Confirmed",
                    body = "Your Bridal Makeup appointment is confirmed.",
                    time = "2026-09-07 14:32",
                    type = NotificationType.Booking,
                    unread = true,
                )
            )
        )
    }
}
