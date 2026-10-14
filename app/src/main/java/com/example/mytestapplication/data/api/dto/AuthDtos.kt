package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
)

data class LoginRequest(
    val phone: String,
)

data class EmailLoginRequest(
    val email: String,
    val password: String,
)

data class OtpVerifyRequest(
    val phone: String,
    val otp: String,
    val purpose: String,
)

data class OtpResendRequest(
    val phone: String,
    val purpose: String,
)

data class OtpResponse(
    val message: String? = null,
    val phone: String? = null,
    @SerializedName("expires_in_seconds")
    val expiresInSeconds: Int? = null,
    @SerializedName("debug_otp")
    val debugOtp: String? = null,
)

data class AuthResponse(
    val message: String? = null,
    val token: String? = null,
    @SerializedName("token_type")
    val tokenType: String? = null,
    val user: UserDto? = null,
)

data class UserDto(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @SerializedName("phone_verified_at")
    val phoneVerifiedAt: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("date_of_birth")
    val dateOfBirth: String? = null,
)

data class UpdateProfileRequest(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @SerializedName("date_of_birth")
    val dateOfBirth: String? = null,
    /** data:image/jpeg;base64,... — server saves file and returns avatar_url */
    @SerializedName("avatar_base64")
    val avatarBase64: String? = null,
)
