package com.example.mytestapplication.ui

import androidx.annotation.DrawableRes
import com.example.mytestapplication.R
import com.example.mytestapplication.data.api.dto.AddressDto
import com.example.mytestapplication.data.api.dto.AvailabilitySlotDto
import com.example.mytestapplication.data.api.dto.BookingDto
import com.example.mytestapplication.data.api.dto.CategoryDto
import com.example.mytestapplication.data.api.dto.FavoriteItemDto
import com.example.mytestapplication.data.api.dto.ProfessionalDto
import com.example.mytestapplication.data.api.dto.ReviewDto
import com.example.mytestapplication.data.api.dto.ServiceDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class ServiceInfo(
    val id: Int,
    val title: String,
    val price: String,
    val ratingLabel: String,
    val duration: String,
    val description: String,
    val shortDescription: String,
    @DrawableRes val imageRes: Int? = null,
    val imageUrl: String? = null,
    val included: List<String> = emptyList(),
    val notIncluded: List<String> = emptyList(),
    val professionals: List<ProfessionalInfo> = emptyList(),
    val categorySlug: String? = null,
    val categoryName: String? = null,
    val isFavorited: Boolean = false,
    val favoriteId: Int? = null,
)

data class ProfessionalInfo(
    val id: Int,
    val name: String,
    val role: String,
    val rating: String,
    val reviewCount: Int = 0,
    val bio: String = "",
    @DrawableRes val imageRes: Int? = null,
    val avatarUrl: String? = null,
)

data class BookingUiItem(
    val id: Int,
    val title: String,
    val location: String,
    val date: String,
    val time: String,
    val status: String,
    val professionalName: String = "",
    val totalLabel: String = "",
    val reference: String = "",
    val paymentStatus: String = "",
    val imageUrl: String? = null,
    @DrawableRes val imageRes: Int = R.drawable.service_bridal,
    val booking: BookingDto,
)

data class ReviewUi(
    val author: String,
    val rating: Int,
    val body: String,
    val date: String,
    val avatarUrl: String? = null,
)

fun ServiceDto.toServiceInfo(): ServiceInfo {
    val included = inclusions?.filter { it.included }?.map { it.text }.orEmpty()
    val notIncluded = inclusions?.filter { !it.included }?.map { it.text }.orEmpty()
    return ServiceInfo(
        id = id,
        title = name.ifBlank { fullName.orEmpty() },
        price = formatMoney(priceFormatted, price),
        ratingLabel = formatRating(avgRating, reviewCount),
        duration = "${durationMinutes ?: 60} mins",
        description = longDescription ?: shortDescription.orEmpty(),
        shortDescription = shortDescription.orEmpty(),
        imageUrl = imageUrl,
        included = included,
        notIncluded = notIncluded,
        professionals = professionals?.map { it.toProfessionalInfo() }.orEmpty(),
        categorySlug = category?.slug,
        categoryName = category?.name,
        isFavorited = isFavorited == true,
    )
}

fun ProfessionalDto.toProfessionalInfo(): ProfessionalInfo = ProfessionalInfo(
    id = id,
    name = fullName,
    role = title.orEmpty(),
    rating = String.format(Locale.US, "%.1f", avgRating ?: 0.0),
    reviewCount = reviewCount ?: 0,
    bio = bio.orEmpty(),
    avatarUrl = avatarUrl,
)

fun FavoriteItemDto.toServiceInfo(): ServiceInfo = ServiceInfo(
    id = id,
    title = name?.takeIf { it.isNotBlank() } ?: fullName.orEmpty(),
    price = formatMoney(priceFormatted, price),
    ratingLabel = formatRating(avgRating, reviewCount),
    duration = "${durationMinutes ?: 60} mins",
    description = longDescription ?: shortDescription.orEmpty(),
    shortDescription = shortDescription.orEmpty(),
    imageUrl = imageUrl ?: avatarUrl,
    categorySlug = category?.slug,
    categoryName = category?.name,
    isFavorited = true,
)

fun ReviewDto.toUi(): ReviewUi = ReviewUi(
    author = user?.name?.takeIf { it.isNotBlank() } ?: "Guest",
    rating = rating,
    body = body.orEmpty(),
    date = formatBookingDate(createdAt),
    avatarUrl = user?.avatarUrl,
)

fun BookingDto.toUiItem(): BookingUiItem = BookingUiItem(
    id = id,
    title = service?.name?.ifBlank { service.fullName }.orEmpty().ifBlank { "Booking" },
    location = address?.fullAddress ?: address?.displayLine().orEmpty(),
    date = formatBookingDate(scheduledStart),
    time = formatBookingTime(scheduledStart, scheduledEnd),
    status = status,
    professionalName = professional?.fullName.orEmpty(),
    totalLabel = formatMoney(totalFormatted, total),
    reference = publicNumber.orEmpty(),
    paymentStatus = paymentStatus.orEmpty(),
    imageUrl = service?.imageUrl,
    booking = this,
)

fun AddressDto.displayTitle(): String = label?.takeIf { it.isNotBlank() } ?: line1

fun AddressDto.displayLine(): String = fullAddress ?: listOfNotNull(line1, line2, city, state)
    .filter { it.isNotBlank() }
    .joinToString(", ")

fun AvailabilitySlotDto.displayLabel(): String {
    val start = parseApiDateTime(startsAt) ?: return startsAt
    return start.format(DateTimeFormatter.ofPattern("MMM d, h:mm a"))
}

fun parseApiDateTime(raw: String?): java.time.LocalDateTime? {
    if (raw.isNullOrBlank()) return null
    val value = raw.trim()
    runCatching { java.time.Instant.parse(value) }.getOrNull()?.let {
        return it.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
    }
    runCatching { java.time.OffsetDateTime.parse(value) }.getOrNull()?.let {
        return it.toLocalDateTime()
    }
    val local = value.replace(' ', 'T').substringBefore("+").substringBefore(".")
    return runCatching { java.time.LocalDateTime.parse(local) }.getOrNull()
        ?: runCatching { java.time.LocalDate.parse(local.take(10)).atStartOfDay() }.getOrNull()
}

fun formatMoney(priceFormatted: String?, priceCents: Int?): String {
    if (!priceFormatted.isNullOrBlank()) return "₹$priceFormatted"
    if (priceCents != null) {
        return "₹" + String.format(Locale.US, "%.2f", priceCents / 100.0)
    }
    return "₹0.00"
}

private fun formatRating(avgRating: Double?, reviewCount: Int?): String {
    val rating = String.format(Locale.US, "%.1f", avgRating ?: 0.0)
    val count = reviewCount ?: 0
    return "$rating ($count Reviews)"
}

fun formatBookingDate(iso: String?): String {
    val dt = parseApiDateTime(iso) ?: return ""
    return dt.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
}

fun formatBookingTime(start: String?, end: String?): String {
    val startDt = parseApiDateTime(start) ?: return ""
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    val startLabel = startDt.format(timeFormatter)
    val endLabel = parseApiDateTime(end)?.format(timeFormatter).orEmpty()
    return if (endLabel.isBlank()) startLabel else "$startLabel - $endLabel"
}

fun tomorrowDateString(): String = LocalDate.now().plusDays(1).toString()

object ServiceSamples {
    val preview = ServiceInfo(
        id = 1,
        title = "Premium Bridal Makeup",
        price = "$150.00",
        ratingLabel = "4.9 (128 Reviews)",
        duration = "90 mins",
        description = "Experience flawless beauty for your special day.",
        shortDescription = "Full face makeup and premium lashes.",
        imageRes = R.drawable.service_bridal,
        included = listOf("Skin preparation", "Premium products"),
        notIncluded = listOf("Hair styling"),
        categorySlug = "makeup",
    )
}
