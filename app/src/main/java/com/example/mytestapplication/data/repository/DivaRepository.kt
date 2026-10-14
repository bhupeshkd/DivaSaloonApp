package com.example.mytestapplication.data.repository

import android.content.Context
import com.example.mytestapplication.data.api.ApiClient
import com.example.mytestapplication.data.api.dto.AddFavoriteRequest
import com.example.mytestapplication.data.api.dto.AddressDto
import com.example.mytestapplication.data.api.dto.AvailabilitySlotDto
import com.example.mytestapplication.data.api.dto.BookingDto
import com.example.mytestapplication.data.api.dto.CategoryDto
import com.example.mytestapplication.data.api.dto.CreateAddressRequest
import com.example.mytestapplication.data.api.dto.CreateBookingRequest
import com.example.mytestapplication.data.api.dto.CreateReviewRequest
import com.example.mytestapplication.data.api.dto.FavoriteDto
import com.example.mytestapplication.data.api.dto.HomeResponse
import com.example.mytestapplication.data.api.dto.NotificationsResponse
import com.example.mytestapplication.data.api.dto.RegisterDeviceTokenRequest
import com.example.mytestapplication.data.api.dto.RescheduleBookingRequest
import com.example.mytestapplication.data.api.dto.ReviewDto
import com.example.mytestapplication.data.api.dto.ServiceDto
import com.example.mytestapplication.data.api.dto.UnregisterDeviceTokenRequest
import com.example.mytestapplication.data.api.dto.UpdateAddressRequest
import com.example.mytestapplication.data.api.dto.UpdateProfileRequest
import com.example.mytestapplication.data.api.dto.UserDto
import com.example.mytestapplication.data.local.TokenStore

class DivaRepository(context: Context) {

    private val tokenStore = TokenStore.getInstance(context)
    private val api = ApiClient.createApi(tokenStore)

    val isLoggedIn: Boolean
        get() = tokenStore.cachedToken != null

    suspend fun getHome(): ApiResult<HomeResponse> = apiCall { api.getHome() }

    suspend fun getCategories(): ApiResult<List<CategoryDto>> = apiCall {
        api.getCategories().data
    }

    suspend fun getServices(
        query: String? = null,
        categoryId: Int? = null,
        category: String? = null,
        featured: Boolean? = null,
    ): ApiResult<List<ServiceDto>> = apiCall {
        api.getServices(
            query,
            categoryId,
            category,
            featured?.let { if (it) 1 else 0 },
        ).data
    }

    suspend fun getService(id: Int): ApiResult<ServiceDto> = apiCall {
        api.getService(id).data
    }

    suspend fun getAvailability(
        serviceId: Int,
        date: String,
        professionalId: Int? = null,
    ): ApiResult<List<AvailabilitySlotDto>> = apiCall {
        api.getAvailability(serviceId, date, professionalId).data
    }

    suspend fun getMe(): ApiResult<UserDto> = apiCall { api.getMe().data }

    suspend fun updateProfile(request: UpdateProfileRequest): ApiResult<UserDto> = apiCall {
        api.updateMe(request).data
    }

    suspend fun getAddresses(): ApiResult<List<AddressDto>> = apiCall {
        api.getAddresses().data
    }

    suspend fun createAddress(request: CreateAddressRequest): ApiResult<AddressDto> = apiCall {
        api.createAddress(request).data
    }

    suspend fun updateAddress(id: Int, request: UpdateAddressRequest): ApiResult<AddressDto> = apiCall {
        api.updateAddress(id, request).data
    }

    suspend fun deleteAddress(id: Int): ApiResult<Unit> = apiCall {
        api.deleteAddress(id)
    }

    suspend fun getBookings(status: String? = null): ApiResult<List<BookingDto>> = apiCall {
        api.getBookings(status).data
    }

    suspend fun createBooking(request: CreateBookingRequest): ApiResult<BookingDto> = apiCall {
        api.createBooking(request).data
    }

    suspend fun cancelBooking(id: Int): ApiResult<BookingDto> = apiCall {
        api.cancelBooking(id).data
    }

    suspend fun rescheduleBooking(id: Int, slotId: Int): ApiResult<BookingDto> = apiCall {
        api.rescheduleBooking(id, RescheduleBookingRequest(slotId)).data
    }

    suspend fun getFavorites(): ApiResult<List<FavoriteDto>> = apiCall {
        api.getFavorites().data
    }

    suspend fun addFavorite(type: String, id: Int): ApiResult<Unit> = apiCall {
        api.addFavorite(AddFavoriteRequest(type, id))
    }

    suspend fun removeFavorite(id: Int): ApiResult<Unit> = apiCall {
        api.removeFavorite(id)
    }

    suspend fun createReview(bookingId: Int, rating: Int, body: String?): ApiResult<ReviewDto> = apiCall {
        api.createReview(CreateReviewRequest(bookingId, rating, body)).data
    }

    suspend fun getServiceReviews(serviceId: Int): ApiResult<List<ReviewDto>> = apiCall {
        api.getServiceReviews(serviceId).data
    }

    suspend fun getNotifications(page: Int? = null): ApiResult<NotificationsResponse> = apiCall {
        api.getNotifications(page)
    }

    suspend fun markNotificationRead(id: String): ApiResult<Unit> = apiCall {
        api.markNotificationRead(id)
    }

    suspend fun markAllNotificationsRead(): ApiResult<Unit> = apiCall {
        api.markAllNotificationsRead()
    }

    /** Call after login once Firebase FCM token is available. */
    suspend fun registerDeviceToken(
        token: String,
        deviceId: String? = null,
    ): ApiResult<Unit> = apiCall {
        api.registerDeviceToken(
            RegisterDeviceTokenRequest(
                token = token,
                platform = "android",
                deviceId = deviceId,
            )
        )
    }

    /** Call on logout once Firebase FCM token is available. */
    suspend fun unregisterDeviceToken(token: String): ApiResult<Unit> = apiCall {
        api.unregisterDeviceToken(UnregisterDeviceTokenRequest(token = token))
    }
}
