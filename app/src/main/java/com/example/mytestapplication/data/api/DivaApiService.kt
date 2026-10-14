package com.example.mytestapplication.data.api

import com.example.mytestapplication.data.api.dto.AddFavoriteRequest
import com.example.mytestapplication.data.api.dto.AddFavoriteResponse
import com.example.mytestapplication.data.api.dto.AddressDto
import com.example.mytestapplication.data.api.dto.AuthResponse
import com.example.mytestapplication.data.api.dto.AvailabilityResponse
import com.example.mytestapplication.data.api.dto.BookingDto
import com.example.mytestapplication.data.api.dto.CreateAddressRequest
import com.example.mytestapplication.data.api.dto.CreateBookingRequest
import com.example.mytestapplication.data.api.dto.CreateReviewRequest
import com.example.mytestapplication.data.api.dto.CreateReviewResponse
import com.example.mytestapplication.data.api.dto.DataWrapper
import com.example.mytestapplication.data.api.dto.FavoritesResponse
import com.example.mytestapplication.data.api.dto.HomeResponse
import com.example.mytestapplication.data.api.dto.EmailLoginRequest
import com.example.mytestapplication.data.api.dto.LoginRequest
import com.example.mytestapplication.data.api.dto.MessageResponse
import com.example.mytestapplication.data.api.dto.NotificationsResponse
import com.example.mytestapplication.data.api.dto.OtpResendRequest
import com.example.mytestapplication.data.api.dto.OtpResponse
import com.example.mytestapplication.data.api.dto.OtpVerifyRequest
import com.example.mytestapplication.data.api.dto.PaginatedResponse
import com.example.mytestapplication.data.api.dto.ProfessionalDto
import com.example.mytestapplication.data.api.dto.RegisterDeviceTokenRequest
import com.example.mytestapplication.data.api.dto.RegisterRequest
import com.example.mytestapplication.data.api.dto.RescheduleBookingRequest
import com.example.mytestapplication.data.api.dto.ServiceDto
import com.example.mytestapplication.data.api.dto.ServiceReviewsResponse
import com.example.mytestapplication.data.api.dto.UnregisterDeviceTokenRequest
import com.example.mytestapplication.data.api.dto.UpdateAddressRequest
import com.example.mytestapplication.data.api.dto.UpdateProfileRequest
import com.example.mytestapplication.data.api.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DivaApiService {

    // Auth
    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): OtpResponse

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): OtpResponse

    @POST("auth/login")
    suspend fun loginWithPassword(@Body body: EmailLoginRequest): AuthResponse

    @POST("auth/otp/verify")
    suspend fun verifyOtp(@Body body: OtpVerifyRequest): AuthResponse

    @POST("auth/otp/resend")
    suspend fun resendOtp(@Body body: OtpResendRequest): OtpResponse

    @POST("auth/logout")
    suspend fun logout(): MessageResponse

    @GET("me")
    suspend fun getMe(): DataWrapper<UserDto>

    @PATCH("me")
    suspend fun updateMe(@Body body: UpdateProfileRequest): DataWrapper<UserDto>

    // Catalog
    @GET("home")
    suspend fun getHome(): HomeResponse

    @GET("categories")
    suspend fun getCategories(): DataWrapper<List<com.example.mytestapplication.data.api.dto.CategoryDto>>

    @GET("services")
    suspend fun getServices(
        @Query("q") query: String? = null,
        @Query("category_id") categoryId: Int? = null,
        @Query("category") category: String? = null,
        @Query("featured") featured: Int? = null,
        @Query("page") page: Int? = null,
    ): PaginatedResponse<ServiceDto>

    @GET("services/{id}")
    suspend fun getService(@Path("id") id: Int): DataWrapper<ServiceDto>

    @GET("services/{id}/professionals")
    suspend fun getServiceProfessionals(@Path("id") id: Int): DataWrapper<List<ProfessionalDto>>

    @GET("services/{id}/reviews")
    suspend fun getServiceReviews(
        @Path("id") id: Int,
        @Query("page") page: Int? = null,
    ): ServiceReviewsResponse

    @GET("availability")
    suspend fun getAvailability(
        @Query("service_id") serviceId: Int,
        @Query("date") date: String,
        @Query("professional_id") professionalId: Int? = null,
    ): AvailabilityResponse

    // Addresses
    @GET("addresses")
    suspend fun getAddresses(): DataWrapper<List<AddressDto>>

    @POST("addresses")
    suspend fun createAddress(@Body body: CreateAddressRequest): DataWrapper<AddressDto>

    @PATCH("addresses/{id}")
    suspend fun updateAddress(
        @Path("id") id: Int,
        @Body body: UpdateAddressRequest,
    ): DataWrapper<AddressDto>

    @DELETE("addresses/{id}")
    suspend fun deleteAddress(@Path("id") id: Int): MessageResponse

    // Bookings
    @GET("bookings")
    suspend fun getBookings(
        @Query("status") status: String? = null,
        @Query("page") page: Int? = null,
    ): PaginatedResponse<BookingDto>

    @POST("bookings")
    suspend fun createBooking(@Body body: CreateBookingRequest): DataWrapper<BookingDto>

    @GET("bookings/{id}")
    suspend fun getBooking(@Path("id") id: Int): DataWrapper<BookingDto>

    @POST("bookings/{id}/cancel")
    suspend fun cancelBooking(@Path("id") id: Int): DataWrapper<BookingDto>

    @POST("bookings/{id}/reschedule")
    suspend fun rescheduleBooking(
        @Path("id") id: Int,
        @Body body: RescheduleBookingRequest,
    ): DataWrapper<BookingDto>

    // Favorites
    @GET("favorites")
    suspend fun getFavorites(): FavoritesResponse

    @POST("favorites")
    suspend fun addFavorite(@Body body: AddFavoriteRequest): AddFavoriteResponse

    @DELETE("favorites/{id}")
    suspend fun removeFavorite(@Path("id") id: Int): MessageResponse

    // Reviews
    @POST("reviews")
    suspend fun createReview(@Body body: CreateReviewRequest): CreateReviewResponse

    // Notifications (FCM register deferred until Firebase credentials)
    @POST("device-tokens")
    suspend fun registerDeviceToken(@Body body: RegisterDeviceTokenRequest): MessageResponse

    @HTTP(method = "DELETE", path = "device-tokens", hasBody = true)
    suspend fun unregisterDeviceToken(@Body body: UnregisterDeviceTokenRequest): MessageResponse

    @GET("notifications")
    suspend fun getNotifications(@Query("page") page: Int? = null): NotificationsResponse

    @POST("notifications/{id}/read")
    suspend fun markNotificationRead(@Path("id") id: String): MessageResponse

    @POST("notifications/read-all")
    suspend fun markAllNotificationsRead(): MessageResponse
}
