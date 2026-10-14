package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class FavoriteDto(
    val id: Int,
    val type: String,
    val item: FavoriteItemDto? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
)

data class FavoriteItemDto(
    val id: Int = 0,
    val name: String? = null,
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
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("avg_rating")
    val avgRating: Double? = null,
    @SerializedName("review_count")
    val reviewCount: Int? = null,
    val category: CategoryDto? = null,
    @SerializedName("is_favorited")
    val isFavorited: Boolean? = null,
)

data class FavoritesResponse(
    val data: List<FavoriteDto>,
)

data class AddFavoriteRequest(
    val type: String,
    val id: Int,
)

data class AddFavoriteResponse(
    val message: String? = null,
    val data: AddFavoriteData? = null,
)

data class AddFavoriteData(
    val id: Int,
    val type: String,
    @SerializedName("item_id")
    val itemId: Int,
)
