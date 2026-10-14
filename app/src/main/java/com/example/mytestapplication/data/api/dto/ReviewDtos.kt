package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class CreateReviewRequest(
    @SerializedName("booking_id")
    val bookingId: Int,
    val rating: Int,
    val body: String? = null,
    val photos: List<String>? = null,
)

data class CreateReviewResponse(
    val data: ReviewDto,
)

data class ReviewDto(
    val id: Int,
    @SerializedName("booking_id")
    val bookingId: Int? = null,
    val rating: Int,
    val body: String? = null,
    val user: ReviewUserDto? = null,
    val photos: List<String>? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
)

data class ReviewUserDto(
    val id: Int,
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
)

data class ServiceReviewsResponse(
    val data: List<ReviewDto>,
    val meta: PaginationMeta? = null,
)
