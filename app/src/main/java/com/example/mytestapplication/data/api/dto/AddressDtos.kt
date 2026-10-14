package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    val id: Int,
    val label: String? = null,
    val line1: String,
    val line2: String? = null,
    val city: String,
    val state: String? = null,
    @SerializedName("postal_code")
    val postalCode: String? = null,
    val country: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    @SerializedName("is_default")
    val isDefault: Boolean = false,
    @SerializedName("full_address")
    val fullAddress: String? = null,
)

data class CreateAddressRequest(
    val label: String? = null,
    val line1: String,
    val line2: String? = null,
    val city: String,
    val state: String? = null,
    @SerializedName("postal_code")
    val postalCode: String? = null,
    val country: String = "IN",
    val lat: Double? = null,
    val lng: Double? = null,
    @SerializedName("is_default")
    val isDefault: Boolean = false,
)

data class UpdateAddressRequest(
    val label: String? = null,
    val line1: String? = null,
    val line2: String? = null,
    val city: String? = null,
    val state: String? = null,
    @SerializedName("postal_code")
    val postalCode: String? = null,
    val country: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    @SerializedName("is_default")
    val isDefault: Boolean? = null,
)
