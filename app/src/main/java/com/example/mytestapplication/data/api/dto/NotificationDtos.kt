package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    val id: String,
    val type: String? = null,
    val event: String? = null,
    val title: String? = null,
    val body: String? = null,
    @SerializedName("booking_id")
    val bookingId: Int? = null,
    val status: String? = null,
    @SerializedName("read_at")
    val readAt: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
)

data class NotificationsResponse(
    val data: List<NotificationDto> = emptyList(),
    val meta: NotificationMeta? = null,
)

data class NotificationMeta(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    val total: Int? = null,
    @SerializedName("unread_count")
    val unreadCount: Int? = null,
)

data class RegisterDeviceTokenRequest(
    val token: String,
    val platform: String = "android",
    @SerializedName("device_id")
    val deviceId: String? = null,
)

data class UnregisterDeviceTokenRequest(
    val token: String,
)
