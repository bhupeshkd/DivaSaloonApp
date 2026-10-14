package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class CreateBookingRequest(
    @SerializedName("service_id")
    val serviceId: Int,
    @SerializedName("professional_id")
    val professionalId: Int,
    @SerializedName("address_id")
    val addressId: Int,
    @SerializedName("availability_slot_id")
    val availabilitySlotId: Int,
    @SerializedName("payment_method_type")
    val paymentMethodType: String? = null,
    val notes: String? = null,
)

data class RescheduleBookingRequest(
    @SerializedName("availability_slot_id")
    val availabilitySlotId: Int,
)

data class BookingDto(
    val id: Int,
    @SerializedName("public_number")
    val publicNumber: String? = null,
    val status: String,
    @SerializedName("scheduled_start")
    val scheduledStart: String? = null,
    @SerializedName("scheduled_end")
    val scheduledEnd: String? = null,
    @SerializedName("duration_minutes")
    val durationMinutes: Int? = null,
    val subtotal: Int? = null,
    @SerializedName("tax_fees")
    val taxFees: Int? = null,
    val discount: Int? = null,
    val total: Int? = null,
    @SerializedName("total_formatted")
    val totalFormatted: String? = null,
    @SerializedName("payment_method_type")
    val paymentMethodType: String? = null,
    @SerializedName("payment_status")
    val paymentStatus: String? = null,
    val notes: String? = null,
    @SerializedName("cancelled_at")
    val cancelledAt: String? = null,
    val service: ServiceDto? = null,
    val professional: ProfessionalDto? = null,
    val address: AddressDto? = null,
    @SerializedName("status_events")
    val statusEvents: List<BookingStatusEventDto>? = null,
    val review: BookingReviewDto? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
)

data class BookingStatusEventDto(
    val status: String,
    val message: String? = null,
    @SerializedName("occurred_at")
    val occurredAt: String? = null,
)

data class BookingReviewDto(
    val id: Int,
    val rating: Int,
    val body: String? = null,
)
