package com.example.mytestapplication.data.repository

import android.content.Context
import com.example.mytestapplication.data.api.ApiClient
import com.example.mytestapplication.data.api.dto.AuthResponse
import com.example.mytestapplication.data.api.dto.EmailLoginRequest
import com.example.mytestapplication.data.api.dto.LoginRequest
import com.example.mytestapplication.data.api.dto.OtpResendRequest
import com.example.mytestapplication.data.api.dto.OtpResponse
import com.example.mytestapplication.data.api.dto.OtpVerifyRequest
import com.example.mytestapplication.data.api.dto.RegisterRequest
import com.example.mytestapplication.data.local.TokenStore

enum class OtpPurpose(val apiValue: String) {
    Register("register"),
    Login("login"),
}

class AuthRepository(context: Context) {

    private val tokenStore = TokenStore.getInstance(context)
    private val api = ApiClient.createApi(tokenStore)

    val isLoggedIn: Boolean
        get() = tokenStore.cachedToken != null

    suspend fun restoreSession(): Boolean = tokenStore.loadToken() != null

    suspend fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
    ): ApiResult<OtpResponse> = apiCall {
        api.register(
            RegisterRequest(
                name = name.trim(),
                phone = normalizePhone(phone),
                email = email.trim(),
                password = password,
                passwordConfirmation = passwordConfirmation,
            )
        )
    }

    suspend fun login(phone: String): ApiResult<OtpResponse> = apiCall {
        api.login(LoginRequest(phone = normalizePhone(phone)))
    }

    suspend fun loginWithEmail(email: String, password: String): ApiResult<AuthResponse> = apiCall {
        val response = api.loginWithPassword(
            EmailLoginRequest(
                email = email.trim(),
                password = password,
            )
        )
        response.token?.let { tokenStore.saveToken(it) }
        response
    }

    suspend fun verifyOtp(
        phone: String,
        otp: String,
        purpose: OtpPurpose,
    ): ApiResult<AuthResponse> = apiCall {
        val response = api.verifyOtp(
            OtpVerifyRequest(
                phone = normalizePhone(phone),
                otp = otp.trim(),
                purpose = purpose.apiValue,
            )
        )
        response.token?.let { tokenStore.saveToken(it) }
        response
    }

    suspend fun resendOtp(
        phone: String,
        purpose: OtpPurpose,
    ): ApiResult<OtpResponse> = apiCall {
        api.resendOtp(
            OtpResendRequest(
                phone = normalizePhone(phone),
                purpose = purpose.apiValue,
            )
        )
    }

    suspend fun logout(): ApiResult<Unit> = apiCall {
        api.logout()
        tokenStore.clearToken()
    }

    companion object {
        fun normalizePhone(raw: String): String {
            val trimmed = raw.trim()
            val digits = trimmed.filter { it.isDigit() }
            if (digits.isEmpty()) return trimmed
            return when {
                trimmed.startsWith("+") -> "+$digits"
                digits.length == 10 -> "+91$digits"
                else -> "+$digits"
            }
        }

        fun maskPhone(phone: String): String {
            val digits = phone.filter { it.isDigit() }
            if (digits.length < 4) return phone
            val lastTwo = digits.takeLast(2)
            val prefix = phone.takeWhile { !it.isDigit() }.ifEmpty { "+" }
            return "$prefix${digits.first()} ••••• $lastTwo"
        }
    }
}
