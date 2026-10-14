package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    val greeting: String? = null,
    val categories: List<CategoryDto>? = null,
    @SerializedName("featured_services")
    val featuredServices: List<ServiceDto>? = null,
)

data class CategoryDto(
    val id: Int,
    val name: String,
    val slug: String,
    val icon: String? = null,
)

data class ServiceDto(
    val id: Int,
    @SerializedName("category_id")
    val categoryId: Int? = null,
    val category: CategoryDto? = null,
    val name: String = "",
    @SerializedName("full_name")
    val fullName: String? = null,
    val slug: String? = null,
    @SerializedName("short_description")
    val shortDescription: String? = null,
    @SerializedName("long_description")
    val longDescription: String? = null,
    val price: Int? = null,
    @SerializedName("price_formatted")
    val priceFormatted: String? = null,
    @SerializedName("duration_minutes")
    val durationMinutes: Int? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("avg_rating")
    val avgRating: Double? = null,
    @SerializedName("review_count")
    val reviewCount: Int? = null,
    @SerializedName("is_featured")
    val isFeatured: Boolean? = null,
    val inclusions: List<ServiceInclusionDto>? = null,
    val professionals: List<ProfessionalDto>? = null,
    @SerializedName("is_favorited")
    val isFavorited: Boolean? = null,
)

data class ServiceInclusionDto(
    val id: Int? = null,
    val text: String,
    val included: Boolean,
)

data class ProfessionalDto(
    val id: Int,
    @SerializedName("full_name")
    val fullName: String,
    val title: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    val bio: String? = null,
    @SerializedName("avg_rating")
    val avgRating: Double? = null,
    @SerializedName("review_count")
    val reviewCount: Int? = null,
)

data class AvailabilitySlotDto(
    val id: Int,
    @SerializedName("professional_id")
    val professionalId: Int,
    val professional: ProfessionalDto? = null,
    @SerializedName("starts_at")
    val startsAt: String,
    @SerializedName("ends_at")
    val endsAt: String,
)

data class AvailabilityResponse(
    val data: List<AvailabilitySlotDto>,
)
