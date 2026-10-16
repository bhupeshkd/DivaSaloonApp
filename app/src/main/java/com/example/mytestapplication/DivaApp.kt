package com.example.mytestapplication

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import com.example.mytestapplication.BuildConfig
import com.example.mytestapplication.data.api.dto.AddressDto
import com.example.mytestapplication.data.api.dto.AvailabilitySlotDto
import com.example.mytestapplication.data.api.dto.BookingDto
import com.example.mytestapplication.data.api.dto.CategoryDto
import com.example.mytestapplication.data.api.dto.CreateAddressRequest
import com.example.mytestapplication.data.api.dto.CreateBookingRequest
import com.example.mytestapplication.data.api.dto.FavoriteDto
import com.example.mytestapplication.data.api.dto.ProfessionalDto
import com.example.mytestapplication.data.api.dto.UpdateProfileRequest
import com.example.mytestapplication.data.api.dto.UserDto
import com.example.mytestapplication.data.location.DeviceLocationReader
import com.example.mytestapplication.data.location.ResolvedLocation
import com.example.mytestapplication.data.repository.ApiResult
import com.example.mytestapplication.data.repository.AuthRepository
import com.example.mytestapplication.data.repository.DivaRepository
import com.example.mytestapplication.data.repository.OtpPurpose
import com.example.mytestapplication.ui.AddAddressScreen
import com.example.mytestapplication.ui.AppSideMenu
import com.example.mytestapplication.ui.AuthSuccessScreen
import com.example.mytestapplication.ui.BookingSuccessScreen
import com.example.mytestapplication.ui.BookingsScreen
import com.example.mytestapplication.ui.CheckoutScreen
import com.example.mytestapplication.ui.ConnectionErrorScreen
import com.example.mytestapplication.ui.EditProfileScreen
import com.example.mytestapplication.ui.FavoritesScreen
import com.example.mytestapplication.ui.HomeScreen
import com.example.mytestapplication.ui.HomeSkeletonScreen
import com.example.mytestapplication.ui.LoginScreen
import com.example.mytestapplication.ui.NotificationsScreen
import com.example.mytestapplication.ui.AppNotification
import com.example.mytestapplication.ui.OtpVerificationScreen
import com.example.mytestapplication.ui.PaymentMethodsScreen
import com.example.mytestapplication.ui.PaymentScreen
import com.example.mytestapplication.ui.PaymentSuccessScreen
import com.example.mytestapplication.ui.ProfileInfoScreen
import com.example.mytestapplication.ui.ProfileInfoSection
import com.example.mytestapplication.ui.ProfileScreen
import com.example.mytestapplication.ui.RateServiceScreen
import com.example.mytestapplication.ui.RegisterScreen
import com.example.mytestapplication.ui.SelectLocationScreen
import com.example.mytestapplication.ui.ServiceDetailsScreen
import com.example.mytestapplication.ui.ServiceInfo
import com.example.mytestapplication.ui.ServiceSamples
import com.example.mytestapplication.ui.ServicesScreen
import com.example.mytestapplication.ui.SplashScreen
import com.example.mytestapplication.ui.TrackingScreen
import com.example.mytestapplication.ui.WelcomeScreen
import com.example.mytestapplication.ui.defaultSideMenuItems
import com.example.mytestapplication.ui.displayLabel
import com.example.mytestapplication.ui.displayLine
import com.example.mytestapplication.ui.displayTitle
import com.example.mytestapplication.ui.formatBookingDate
import com.example.mytestapplication.ui.formatBookingTime
import com.example.mytestapplication.ui.formatMoney
import com.example.mytestapplication.ui.toServiceInfo
import com.example.mytestapplication.ui.toUi
import com.example.mytestapplication.ui.toUiItem
import com.example.mytestapplication.ui.tomorrowDateString
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

private enum class AppScreen {
    Splash,
    Welcome,
    Login,
    Register,
    Otp,
    AuthSuccess,
    Home,
    Notifications,
    Services,
    ServiceDetails,
    Checkout,
    SelectLocation,
    AddAddress,
    Payment,
    PaymentSuccess,
    BookingSuccess,
    Tracking,
    RateService,
    Profile,
    EditProfile,
    Favorites,
    Bookings,
    PaymentMethods,
    Coupons,
    Referral,
    HelpCenter,
    Faqs,
    PrivacyPolicy,
}

private data class PendingOtpSession(
    val phone: String,
    val displayPhone: String,
    val purpose: OtpPurpose,
)

@Composable
fun DivaApp() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(context) }
    val divaRepository = remember { DivaRepository(context) }
    val locationReader = remember { DeviceLocationReader(context) }

    var currentScreen by remember { mutableStateOf(AppScreen.Splash) }
    var pendingOtp by remember { mutableStateOf<PendingOtpSession?>(null) }
    var isAuthLoading by remember { mutableStateOf(false) }
    var isDataLoading by remember { mutableStateOf(false) }
    var isSavingAddress by remember { mutableStateOf(false) }
    var loadError by remember { mutableStateOf<String?>(null) }

    var currentUser by remember { mutableStateOf<UserDto?>(null) }
    var homeGreeting by remember { mutableStateOf("Welcome") }
    var categories by remember { mutableStateOf<List<CategoryDto>>(emptyList()) }
    var featuredServices by remember { mutableStateOf<List<ServiceInfo>>(emptyList()) }
    var allServices by remember { mutableStateOf<List<ServiceInfo>>(emptyList()) }
    var favoriteServices by remember { mutableStateOf<List<ServiceInfo>>(emptyList()) }
    var favoriteRecords by remember { mutableStateOf<List<FavoriteDto>>(emptyList()) }
    var bookings by remember { mutableStateOf<List<com.example.mytestapplication.ui.BookingUiItem>>(emptyList()) }
    var addresses by remember { mutableStateOf<List<AddressDto>>(emptyList()) }
    var notifications by remember { mutableStateOf<List<AppNotification>>(emptyList()) }
    var unreadNotificationCount by remember { mutableIntStateOf(0) }
    var isNotificationsLoading by remember { mutableStateOf(false) }

    var selectedService by remember { mutableStateOf<ServiceInfo?>(null) }
    var serviceReviews by remember { mutableStateOf<List<com.example.mytestapplication.ui.ReviewUi>>(emptyList()) }
    var selectedProfessional by remember { mutableStateOf<ProfessionalDto?>(null) }
    var selectedSlot by remember { mutableStateOf<AvailabilitySlotDto?>(null) }
    var selectedAddress by remember { mutableStateOf<AddressDto?>(null) }
    var lastBooking by remember { mutableStateOf<BookingDto?>(null) }
    var paidAmount by remember { mutableStateOf("₹0.00") }
    var scheduleLabel by remember { mutableStateOf("Select a time slot") }
    var bookingAddressTitle by remember { mutableStateOf("Home Address") }
    var bookingAddressLine by remember { mutableStateOf("") }
    var homeLocation by remember { mutableStateOf("Locating...") }
    var isLocating by remember { mutableStateOf(false) }
    var detectedLocation by remember { mutableStateOf<ResolvedLocation?>(null) }
    var locationPrompted by remember { mutableStateOf(false) }
    var servicesCategoryFilter by remember { mutableStateOf<String?>(null) }
    var servicesSearchQuery by remember { mutableStateOf<String?>(null) }
    var returnToServices by remember { mutableStateOf(false) }
    var selectLocationReturnScreen by remember { mutableStateOf(AppScreen.Checkout) }
    var selectLocationBackScreen by remember { mutableStateOf(AppScreen.Checkout) }
    var trackingReturnScreen by remember { mutableStateOf(AppScreen.BookingSuccess) }
    var showSideMenu by remember { mutableStateOf(false) }
    var infoReturnScreen by remember { mutableStateOf(AppScreen.Profile) }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showError(message: String) {
        loadError = message
        showToast(message)
    }

    fun applyFavoriteState(service: ServiceInfo): ServiceInfo {
        val favoriteId = service.favoriteId
            ?: favoriteRecords.firstOrNull { it.type == "service" && it.item?.id == service.id }?.id
        return service.copy(
            isFavorited = service.isFavorited || favoriteId != null,
            favoriteId = favoriteId,
        )
    }

    suspend fun refreshFavorites() {
        if (!authRepository.isLoggedIn) {
            favoriteServices = emptyList()
            favoriteRecords = emptyList()
            return
        }
        when (val result = divaRepository.getFavorites()) {
            is ApiResult.Success -> {
                favoriteRecords = result.data
                favoriteServices = result.data.mapNotNull { fav ->
                    if (fav.type != "service") return@mapNotNull null
                    fav.item?.toServiceInfo()?.copy(
                        favoriteId = fav.id,
                        isFavorited = true,
                    )
                }
                featuredServices = featuredServices.map { applyFavoriteState(it) }
                allServices = allServices.map { applyFavoriteState(it) }
                selectedService = selectedService?.let { applyFavoriteState(it) }
            }
            is ApiResult.Error -> showError(result.message)
        }
    }

    suspend fun toggleFavorite(service: ServiceInfo) {
        val existingFavoriteId = service.favoriteId
            ?: favoriteRecords.firstOrNull { it.type == "service" && it.item?.id == service.id }?.id
        val shouldRemove = service.isFavorited || existingFavoriteId != null

        if (shouldRemove) {
            var favoriteId = existingFavoriteId
            if (favoriteId == null) {
                refreshFavorites()
                favoriteId = favoriteRecords
                    .firstOrNull { it.type == "service" && it.item?.id == service.id }
                    ?.id
            }
            if (favoriteId == null) {
                showError("Could not find this favorite.")
                return
            }
            when (val result = divaRepository.removeFavorite(favoriteId)) {
                is ApiResult.Success -> {
                    showToast("Removed from wishlist")
                    refreshFavorites()
                }
                is ApiResult.Error -> showError(result.message)
            }
        } else {
            when (val result = divaRepository.addFavorite("service", service.id)) {
                is ApiResult.Success -> {
                    showToast("Added to wishlist")
                    refreshFavorites()
                }
                is ApiResult.Error -> showError(result.message)
            }
        }
    }

    suspend fun refreshBookings() {
        if (!authRepository.isLoggedIn) {
            bookings = emptyList()
            return
        }
        when (val result = divaRepository.getBookings()) {
            is ApiResult.Success -> bookings = result.data.map { it.toUiItem() }
            is ApiResult.Error -> showError(result.message)
        }
    }

    suspend fun refreshNotifications() {
        if (!authRepository.isLoggedIn) {
            notifications = emptyList()
            unreadNotificationCount = 0
            return
        }
        isNotificationsLoading = true
        try {
            when (val result = divaRepository.getNotifications()) {
                is ApiResult.Success -> {
                    notifications = result.data.data.map { it.toUiItem() }
                    unreadNotificationCount = result.data.meta?.unreadCount
                        ?: notifications.count { it.unread }
                }
                is ApiResult.Error -> showError(result.message)
            }
        } finally {
            isNotificationsLoading = false
        }
    }

    suspend fun refreshAddresses() {
        if (!authRepository.isLoggedIn) {
            addresses = emptyList()
            return
        }
        when (val result = divaRepository.getAddresses()) {
            is ApiResult.Success -> {
                addresses = result.data
                selectedAddress = result.data.firstOrNull { it.isDefault } ?: result.data.firstOrNull()
                selectedAddress?.let {
                    bookingAddressTitle = it.displayTitle()
                    bookingAddressLine = it.displayLine()
                }
            }
            is ApiResult.Error -> showError(result.message)
        }
    }

    suspend fun refreshServices(
        category: String? = servicesCategoryFilter,
        query: String? = servicesSearchQuery,
    ) {
        when (val result = divaRepository.getServices(query = query, category = category)) {
            is ApiResult.Success -> {
                allServices = result.data.map { applyFavoriteState(it.toServiceInfo()) }
            }
            is ApiResult.Error -> showError(result.message)
        }
    }

    fun openServices(
        category: String? = null,
        query: String? = null,
    ) {
        servicesCategoryFilter = category
        servicesSearchQuery = query?.takeIf { it.isNotBlank() }
        currentScreen = AppScreen.Services
        scope.launch { refreshServices(category = servicesCategoryFilter, query = servicesSearchQuery) }
    }

    fun isAuthError(message: String): Boolean {
        return message.contains("Unauthenticated", ignoreCase = true) ||
            message.contains("Session expired", ignoreCase = true) ||
            message.contains("401", ignoreCase = true)
    }

    suspend fun loadPublicHomeData() {
        when (val catResult = divaRepository.getCategories()) {
            is ApiResult.Success -> categories = catResult.data
            is ApiResult.Error -> showError(catResult.message)
        }
        when (val servicesResult = divaRepository.getServices(featured = true)) {
            is ApiResult.Success -> featuredServices = servicesResult.data.map { it.toServiceInfo() }
            is ApiResult.Error -> showError(servicesResult.message)
        }
    }

    suspend fun loadHomeData() {
        isDataLoading = true
        loadError = null
        authRepository.restoreSession()
        if (authRepository.isLoggedIn) {
            when (val result = divaRepository.getHome()) {
                is ApiResult.Success -> {
                    homeGreeting = result.data.greeting ?: "Welcome"
                    categories = result.data.categories.orEmpty()
                    featuredServices = result.data.featuredServices.orEmpty().map { it.toServiceInfo() }
                    refreshFavorites()
                    featuredServices = featuredServices.map { applyFavoriteState(it) }
                }
                is ApiResult.Error -> {
                    if (isAuthError(result.message)) {
                        authRepository.clearLocalSession()
                        currentUser = null
                        currentScreen = AppScreen.Login
                        isDataLoading = false
                        return
                    } else {
                        showError(result.message)
                    }
                }
            }
        } else {
            // Explicit guest browse (Welcome → Continue as Guest) only.
            favoriteServices = emptyList()
            favoriteRecords = emptyList()
            loadPublicHomeData()
        }
        isDataLoading = false
    }

    suspend fun loadServiceDetail(serviceId: Int) {
        isDataLoading = true
        when (val result = divaRepository.getService(serviceId)) {
            is ApiResult.Success -> {
                selectedService = result.data.toServiceInfo()
                selectedProfessional = result.data.professionals?.firstOrNull()
            }
            is ApiResult.Error -> showError(result.message)
        }
        serviceReviews = when (val reviews = divaRepository.getServiceReviews(serviceId)) {
            is ApiResult.Success -> reviews.data.map { it.toUi() }
            is ApiResult.Error -> emptyList()
        }
        isDataLoading = false
    }

    suspend fun loadAvailabilityForBooking() {
        val service = selectedService ?: return
        val professionalId = selectedProfessional?.id
            ?: service.professionals.firstOrNull()?.id

        when (
            val result = divaRepository.getAvailability(
                serviceId = service.id,
                date = tomorrowDateString(),
                professionalId = professionalId,
            )
        ) {
            is ApiResult.Success -> {
                selectedSlot = result.data.firstOrNull()
                selectedSlot?.professional?.let { selectedProfessional = it }
                scheduleLabel = selectedSlot?.displayLabel() ?: "No slots available"
            }
            is ApiResult.Error -> showError(result.message)
        }
    }

    suspend fun ensureBookingAddress(): Boolean {
        refreshAddresses()
        val existing = selectedAddress
            ?: addresses.firstOrNull { it.isDefault }
            ?: addresses.firstOrNull()
        if (existing != null) {
            selectedAddress = existing
            bookingAddressTitle = existing.displayTitle()
            bookingAddressLine = existing.displayLine()
            return true
        }
        if (!locationReader.hasPermission()) {
            showToast("Allow location to use your current address")
            return false
        }
        val place = detectedLocation ?: locationReader.resolve()?.also { resolved ->
            detectedLocation = resolved
            homeLocation = resolved.homeLabel
        }
        if (place == null) {
            showToast("Couldn't read GPS. Turn on location and try again.")
            return false
        }
        return when (val result = divaRepository.createAddress(place.toRequest(isDefault = true))) {
            is ApiResult.Success -> {
                addresses = listOf(result.data)
                selectedAddress = result.data
                bookingAddressTitle = result.data.displayTitle()
                bookingAddressLine = result.data.displayLine()
                true
            }
            is ApiResult.Error -> {
                showError(result.message)
                false
            }
        }
    }

    fun publishLocation(explicit: Boolean) {
        scope.launch {
            isLocating = true
            val place = locationReader.resolve()
            isLocating = false
            if (place == null) {
                if (homeLocation == "Locating...") homeLocation = "Location unavailable"
                if (explicit) showToast("Couldn't read GPS. Turn on location and try again.")
                return@launch
            }
            homeLocation = place.homeLabel
            detectedLocation = place
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { grants ->
        if (grants.values.any { it }) {
            publishLocation(explicit = true)
        } else {
            homeLocation = "Location off"
            showToast("Allow location to use your current place")
        }
    }

    fun requestCurrentLocation(explicit: Boolean) {
        if (locationReader.hasPermission()) {
            publishLocation(explicit)
            return
        }
        if (!explicit && locationPrompted) return
        locationPrompted = true
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
        )
    }

    fun confirmDetectedLocation() {
        val place = detectedLocation
        if (place == null) {
            requestCurrentLocation(explicit = true)
            showToast("Fetching current location")
            return
        }
        if (!authRepository.isLoggedIn) {
            showToast("Please log in to continue")
            currentScreen = AppScreen.Login
            return
        }
        scope.launch {
                val existing = addresses.firstOrNull { address ->
                    val lat = address.lat
                    val lng = address.lng
                    lat != null && lng != null &&
                        abs(lat - place.lat) < 0.0005 &&
                        abs(lng - place.lng) < 0.0005
                }
                val saved = if (existing != null) {
                    existing
                } else {
                    when (val result = divaRepository.createAddress(place.toRequest(addresses.isEmpty()))) {
                        is ApiResult.Success -> result.data
                        is ApiResult.Error -> {
                            showError(result.message)
                            return@launch
                        }
                    }
                }
                if (existing == null) refreshAddresses()
                selectedAddress = saved
                bookingAddressTitle = saved.displayTitle()
                bookingAddressLine = saved.displayLine()
                homeLocation = place.homeLabel
                currentScreen = selectLocationReturnScreen
        }
    }

    fun openHome() {
        currentScreen = AppScreen.Home
        scope.launch { loadHomeData() }
        // Do not auto-prompt location on every Home open — system dialog blocks the UI.
        // Location is fetched only when already granted, or when user taps the location chip.
        if (locationReader.hasPermission()) {
            publishLocation(explicit = false)
        } else if (homeLocation == "Locating..." || homeLocation.isBlank()) {
            homeLocation = "Set location"
        }
    }

    fun handleSystemBack() {
        when (currentScreen) {
            AppScreen.Splash, AppScreen.Home -> (context as? ComponentActivity)?.finish()
            AppScreen.Login -> currentScreen = AppScreen.Welcome
            AppScreen.Welcome, AppScreen.AuthSuccess -> currentScreen = AppScreen.Login
            AppScreen.Register, AppScreen.Otp -> currentScreen = AppScreen.Welcome
            AppScreen.Tracking -> currentScreen = trackingReturnScreen
            AppScreen.RateService -> currentScreen = AppScreen.Tracking
            AppScreen.AddAddress -> currentScreen = AppScreen.SelectLocation
            AppScreen.SelectLocation -> currentScreen = selectLocationBackScreen
            AppScreen.Checkout -> currentScreen = AppScreen.ServiceDetails
            AppScreen.Payment -> currentScreen = AppScreen.Checkout
            AppScreen.PaymentSuccess, AppScreen.BookingSuccess -> openHome()
            AppScreen.ServiceDetails -> {
                if (returnToServices) {
                    returnToServices = false
                    currentScreen = AppScreen.Services
                } else {
                    openHome()
                }
            }
            AppScreen.EditProfile, AppScreen.PaymentMethods, AppScreen.Favorites ->
                currentScreen = AppScreen.Profile
            AppScreen.Coupons, AppScreen.Referral, AppScreen.HelpCenter,
            AppScreen.Faqs, AppScreen.PrivacyPolicy ->
                currentScreen = infoReturnScreen
            AppScreen.Notifications -> openHome()
            AppScreen.Bookings, AppScreen.Services, AppScreen.Profile -> openHome()
        }
    }

    BackHandler(enabled = true) {
        handleSystemBack()
    }

    fun openTracking(booking: BookingDto, returnScreen: AppScreen) {
        lastBooking = booking
        paidAmount = formatMoney(booking.totalFormatted, booking.total)
        trackingReturnScreen = returnScreen
        currentScreen = AppScreen.Tracking
    }

    fun navigateToOtp(phone: String, purpose: OtpPurpose, response: com.example.mytestapplication.data.api.dto.OtpResponse) {
        pendingOtp = PendingOtpSession(
            phone = phone,
            displayPhone = AuthRepository.maskPhone(phone),
            purpose = purpose,
        )
        if (BuildConfig.DEBUG) {
            response.debugOtp?.let { showToast("Debug OTP: $it") }
        }
        currentScreen = AppScreen.Otp
    }

    fun requireAuth(action: () -> Unit) {
        if (authRepository.isLoggedIn) action() else {
            showToast("Please log in to continue")
            currentScreen = AppScreen.Login
        }
    }

    /** Book Now → select location first (required), then checkout. */
    fun startBookingWithLocation(backScreen: AppScreen = AppScreen.ServiceDetails) {
        requireAuth {
            scope.launch {
                refreshAddresses()
                loadAvailabilityForBooking()
                selectLocationReturnScreen = AppScreen.Checkout
                selectLocationBackScreen = backScreen
                currentScreen = AppScreen.SelectLocation
            }
        }
    }

    fun handleMenuItem(item: String) {
        showSideMenu = false
        when (item) {
            "Home" -> openHome()
            "Services" -> openServices()
            "Bookings", "Booking History" -> requireAuth {
                currentScreen = AppScreen.Bookings
                scope.launch { refreshBookings() }
            }
            "Favorites" -> requireAuth {
                currentScreen = AppScreen.Favorites
                scope.launch { refreshFavorites() }
            }
            "Profile" -> requireAuth { currentScreen = AppScreen.Profile }
            "Edit Profile" -> requireAuth { currentScreen = AppScreen.EditProfile }
            "Saved Address" -> requireAuth {
                selectLocationReturnScreen = AppScreen.Profile
                selectLocationBackScreen = AppScreen.Profile
                scope.launch {
                    refreshAddresses()
                    currentScreen = AppScreen.SelectLocation
                }
            }
            "Payment Methods" -> requireAuth { currentScreen = AppScreen.PaymentMethods }
            "Coupons" -> requireAuth {
                infoReturnScreen = AppScreen.Profile
                currentScreen = AppScreen.Coupons
            }
            "Referral" -> requireAuth { currentScreen = AppScreen.Referral }
            "Help Center" -> requireAuth { currentScreen = AppScreen.HelpCenter }
            "FAQs" -> requireAuth { currentScreen = AppScreen.Faqs }
            "Privacy Policy" -> {
                infoReturnScreen = if (authRepository.isLoggedIn) AppScreen.Profile else AppScreen.Login
                currentScreen = AppScreen.PrivacyPolicy
            }
            else -> showToast(item)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
    when (currentScreen) {
        AppScreen.Splash -> {
            SplashScreen(
                modifier = Modifier.fillMaxSize(),
                onFinished = {
                    scope.launch {
                        if (authRepository.restoreSession()) {
                            when (val me = divaRepository.getMe()) {
                                is ApiResult.Success -> {
                                    currentUser = me.data
                                    openHome()
                                }
                                is ApiResult.Error -> {
                                    if (isAuthError(me.message)) {
                                        authRepository.clearLocalSession()
                                        currentUser = null
                                        currentScreen = AppScreen.Login
                                    } else {
                                        // Token present; network/API blip — keep session, open home.
                                        openHome()
                                    }
                                }
                            }
                        } else {
                            currentScreen = AppScreen.Login
                        }
                    }
                },
            )
        }

        AppScreen.Welcome -> {
            WelcomeScreen(
                modifier = Modifier.fillMaxSize(),
                onLoginClick = { currentScreen = AppScreen.Login },
                onCreateAccountClick = { currentScreen = AppScreen.Register },
                onContinueAsGuestClick = { openHome() },
            )
        }

        AppScreen.Login -> {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Welcome },
                onContinueClick = { email, password ->
                    when {
                        email.isBlank() -> showToast("Enter your email")
                        password.isBlank() -> showToast("Enter your password")
                        else -> scope.launch {
                            isAuthLoading = true
                            when (val result = authRepository.loginWithEmail(email, password)) {
                                is ApiResult.Success -> {
                                    currentUser = result.data.user
                                    openHome()
                                }
                                is ApiResult.Error -> showError(result.message)
                            }
                            isAuthLoading = false
                        }
                    }
                },
                onForgotPasswordClick = { showToast("Password reset coming soon") },
                onGoogleClick = { showToast("Google sign-in coming soon") },
                onTermsClick = {
                    infoReturnScreen = AppScreen.Login
                    currentScreen = AppScreen.PrivacyPolicy
                },
                onPrivacyClick = {
                    infoReturnScreen = AppScreen.Login
                    currentScreen = AppScreen.PrivacyPolicy
                },
            )
        }

        AppScreen.Register -> {
            RegisterScreen(
                modifier = Modifier.fillMaxSize(),
                onSignUpClick = { name, mobile, email, password, confirmPassword ->
                    when {
                        name.isBlank() || mobile.isBlank() || email.isBlank() ||
                            password.isBlank() || confirmPassword.isBlank() -> showToast("Please fill all fields")
                        password != confirmPassword -> showToast("Passwords do not match")
                        else -> scope.launch {
                            isAuthLoading = true
                            val phone = AuthRepository.normalizePhone(mobile)
                            when (
                                val result = authRepository.register(
                                    name = name,
                                    phone = phone,
                                    email = email,
                                    password = password,
                                    passwordConfirmation = confirmPassword,
                                )
                            ) {
                                is ApiResult.Success -> navigateToOtp(phone, OtpPurpose.Register, result.data)
                                is ApiResult.Error -> showError(result.message)
                            }
                            isAuthLoading = false
                        }
                    }
                },
                onGoogleSignUpClick = { showToast("Google sign-up coming soon") },
                onLoginClick = { currentScreen = AppScreen.Login },
            )
        }

        AppScreen.Otp -> {
            OtpVerificationScreen(
                modifier = Modifier.fillMaxSize(),
                phoneNumber = pendingOtp?.displayPhone.orEmpty(),
                isLoading = isAuthLoading,
                onBackClick = {
                    currentScreen = when (pendingOtp?.purpose) {
                        OtpPurpose.Login -> AppScreen.Login
                        OtpPurpose.Register, null -> AppScreen.Register
                    }
                },
                onResendClick = {
                    val session = pendingOtp ?: return@OtpVerificationScreen
                    scope.launch {
                        isAuthLoading = true
                        when (val result = authRepository.resendOtp(session.phone, session.purpose)) {
                            is ApiResult.Success -> {
                                showToast(result.data.message ?: "Code resent")
                                if (BuildConfig.DEBUG) {
                                    result.data.debugOtp?.let { showToast("Debug OTP: $it") }
                                }
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                        isAuthLoading = false
                    }
                },
                onVerifyClick = { otp ->
                    val session = pendingOtp ?: return@OtpVerificationScreen
                    scope.launch {
                        isAuthLoading = true
                        when (
                            val result = authRepository.verifyOtp(
                                phone = session.phone,
                                otp = otp,
                                purpose = session.purpose,
                            )
                        ) {
                            is ApiResult.Success -> {
                                currentUser = result.data.user
                                pendingOtp = null
                                currentScreen = AppScreen.AuthSuccess
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                        isAuthLoading = false
                    }
                },
            )
        }

        AppScreen.AuthSuccess -> {
            AuthSuccessScreen(
                modifier = Modifier.fillMaxSize(),
                onGetStartedClick = { openHome() },
            )
        }

        AppScreen.Home -> {
            if (isDataLoading && featuredServices.isEmpty()) {
                HomeSkeletonScreen(modifier = Modifier.fillMaxSize())
            } else if (loadError != null && featuredServices.isEmpty()) {
                ConnectionErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNotificationClick = {
                        currentScreen = AppScreen.Notifications
                        scope.launch { refreshNotifications() }
                    },
                    onRetryClick = { scope.launch { loadHomeData() } },
                    onOfflineBookingsClick = { requireAuth { currentScreen = AppScreen.Bookings } },
                    onNavClick = { tab ->
                        when (tab) {
                            "Services" -> openServices()
                            "Profile" -> requireAuth { currentScreen = AppScreen.Profile }
                            "Bookings" -> requireAuth {
                                currentScreen = AppScreen.Bookings
                                scope.launch { refreshBookings() }
                            }
                            else -> showToast(tab)
                        }
                    },
                )
            } else {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    greeting = homeGreeting,
                    location = homeLocation,
                    categories = categories,
                    featuredServices = featuredServices,
                    onLocationClick = {
                        if (authRepository.isLoggedIn) {
                            selectLocationReturnScreen = AppScreen.Home
                            selectLocationBackScreen = AppScreen.Home
                            scope.launch {
                                refreshAddresses()
                                currentScreen = AppScreen.SelectLocation
                            }
                        } else {
                            requestCurrentLocation(explicit = true)
                        }
                    },
                    onFilterClick = { openServices() },
                    onSearchSubmit = { query -> openServices(query = query) },
                    onNotificationClick = {
                        currentScreen = AppScreen.Notifications
                        scope.launch { refreshNotifications() }
                    },
                    onSeeAllTrending = { openServices() },
                    onFeaturedClick = { service ->
                        scope.launch {
                            loadServiceDetail(service.id)
                            returnToServices = false
                            currentScreen = AppScreen.ServiceDetails
                        }
                    },
                    onTrendingBookClick = { service ->
                        scope.launch {
                            loadServiceDetail(service.id)
                            returnToServices = false
                            startBookingWithLocation(backScreen = AppScreen.Home)
                        }
                    },
                    onFavoriteClick = { service ->
                        requireAuth {
                            scope.launch { toggleFavorite(service) }
                        }
                    },
                    onNavClick = { tab ->
                        when (tab) {
                            "Services" -> openServices()
                            "Profile" -> requireAuth { currentScreen = AppScreen.Profile }
                            "Bookings" -> requireAuth {
                                currentScreen = AppScreen.Bookings
                                scope.launch { refreshBookings() }
                            }
                            else -> showToast(tab)
                        }
                    },
                )
            }
        }

        AppScreen.Services -> {
            ServicesScreen(
                modifier = Modifier.fillMaxSize(),
                services = allServices,
                categories = categories,
                initialCategorySlug = servicesCategoryFilter,
                initialSearchQuery = servicesSearchQuery.orEmpty(),
                onServiceClick = { service ->
                    scope.launch {
                        loadServiceDetail(service.id)
                        returnToServices = true
                        currentScreen = AppScreen.ServiceDetails
                    }
                },
                onSearchSubmit = { query ->
                    servicesSearchQuery = query.ifBlank { null }
                    scope.launch {
                        refreshServices(
                            category = servicesCategoryFilter,
                            query = servicesSearchQuery,
                        )
                    }
                },
                onCategoryFilterChange = { slug ->
                    servicesCategoryFilter = slug
                    scope.launch {
                        refreshServices(
                            category = servicesCategoryFilter,
                            query = servicesSearchQuery,
                        )
                    }
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> openHome()
                        "Bookings" -> requireAuth {
                            currentScreen = AppScreen.Bookings
                            scope.launch { refreshBookings() }
                        }
                        "Profile" -> requireAuth { currentScreen = AppScreen.Profile }
                        else -> showToast(tab)
                    }
                },
            )
        }

        AppScreen.ServiceDetails -> {
            ServiceDetailsScreen(
                service = selectedService ?: ServiceSamples.preview,
                reviews = serviceReviews,
                modifier = Modifier.fillMaxSize(),
                onBackClick = {
                    selectedService = null
                    if (returnToServices) {
                        returnToServices = false
                        currentScreen = AppScreen.Services
                    } else {
                        openHome()
                    }
                },
                onViewAllProfessionals = {
                    showToast("All professionals are listed above")
                },
                onBookNowClick = {
                    startBookingWithLocation(
                        backScreen = if (returnToServices) AppScreen.Services else AppScreen.ServiceDetails,
                    )
                },
            )
        }

        AppScreen.Checkout -> {
            CheckoutScreen(
                service = selectedService ?: ServiceSamples.preview,
                addressTitle = bookingAddressTitle,
                addressLine = bookingAddressLine,
                scheduleLabel = scheduleLabel,
                professionalName = selectedProfessional?.fullName
                    ?: selectedService?.professionals?.firstOrNull()?.name.orEmpty(),
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.ServiceDetails },
                onEditLocationClick = {
                    scope.launch {
                        refreshAddresses()
                        selectLocationReturnScreen = AppScreen.Checkout
                        selectLocationBackScreen = AppScreen.Checkout
                        currentScreen = AppScreen.SelectLocation
                    }
                },
                onConfirmBookingClick = {
                    scope.launch {
                        if (selectedAddress == null) {
                            showToast("Select a location for this service")
                            selectLocationReturnScreen = AppScreen.Checkout
                            selectLocationBackScreen = AppScreen.Checkout
                            refreshAddresses()
                            currentScreen = AppScreen.SelectLocation
                            return@launch
                        }
                        loadAvailabilityForBooking()
                        if (selectedSlot == null) {
                            showToast("No available time slots for tomorrow")
                            return@launch
                        }
                        currentScreen = AppScreen.Payment
                    }
                },
            )
        }

        AppScreen.SelectLocation -> {
            SelectLocationScreen(
                modifier = Modifier.fillMaxSize(),
                addresses = addresses,
                selectedTitle = bookingAddressTitle,
                selectedSubtitle = bookingAddressLine,
                isLocating = isLocating,
                detectedTitle = detectedLocation?.title,
                detectedSubtitle = detectedLocation?.subtitle,
                onBackClick = { currentScreen = selectLocationBackScreen },
                onUseCurrentLocation = { requestCurrentLocation(explicit = true) },
                onConfirmCurrentLocation = { confirmDetectedLocation() },
                onAddAddressClick = { currentScreen = AppScreen.AddAddress },
                onAddressMenuClick = { address ->
                    scope.launch {
                        when (val result = divaRepository.deleteAddress(address.id)) {
                            is ApiResult.Success -> {
                                showToast("Address removed")
                                refreshAddresses()
                                if (selectedAddress?.id == address.id) {
                                    selectedAddress = null
                                    bookingAddressTitle = "Select address"
                                    bookingAddressLine = ""
                                }
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                    }
                },
                onConfirmClick = { address ->
                    selectedAddress = address
                    bookingAddressTitle = address.displayTitle()
                    bookingAddressLine = address.displayLine()
                    if (selectLocationReturnScreen == AppScreen.Home) {
                        homeLocation = address.displayTitle()
                    }
                    currentScreen = selectLocationReturnScreen
                    if (selectLocationReturnScreen == AppScreen.Profile) {
                        showToast("Selected ${address.displayTitle()}")
                    }
                },
            )
        }

        AppScreen.AddAddress -> {
            AddAddressScreen(
                modifier = Modifier.fillMaxSize(),
                isSaving = isSavingAddress,
                makeDefaultByDefault = addresses.isEmpty(),
                onBackClick = { currentScreen = AppScreen.SelectLocation },
                onSaveClick = { request: CreateAddressRequest ->
                    scope.launch {
                        isSavingAddress = true
                        when (val result = divaRepository.createAddress(request)) {
                            is ApiResult.Success -> {
                                showToast("Address saved")
                                refreshAddresses()
                                selectedAddress = result.data
                                bookingAddressTitle = result.data.displayTitle()
                                bookingAddressLine = result.data.displayLine()
                                currentScreen = AppScreen.SelectLocation
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                        isSavingAddress = false
                    }
                },
            )
        }

        AppScreen.Payment -> {
            PaymentScreen(
                service = selectedService ?: ServiceSamples.preview,
                scheduleLabel = scheduleLabel,
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Checkout },
                onViewAllCoupons = {
                    infoReturnScreen = AppScreen.Payment
                    currentScreen = AppScreen.Coupons
                },
                onConfirmPayClick = { total ->
                    scope.launch {
                        if (selectedAddress == null) {
                            showToast("Select a location for this service")
                            selectLocationReturnScreen = AppScreen.Payment
                            selectLocationBackScreen = AppScreen.Payment
                            refreshAddresses()
                            currentScreen = AppScreen.SelectLocation
                            return@launch
                        }
                        loadAvailabilityForBooking()
                        val service = selectedService
                        val professional = selectedProfessional
                        val slot = selectedSlot
                        val address = selectedAddress
                        if (service == null || professional == null || slot == null || address == null) {
                            showToast("Missing booking details. Please go back and try again.")
                            return@launch
                        }
                        isDataLoading = true
                        when (
                            val result = divaRepository.createBooking(
                                CreateBookingRequest(
                                    serviceId = service.id,
                                    professionalId = professional.id,
                                    addressId = address.id,
                                    availabilitySlotId = slot.id,
                                    paymentMethodType = "cash",
                                )
                            )
                        ) {
                            is ApiResult.Success -> {
                                lastBooking = result.data
                                paidAmount = formatMoney(result.data.totalFormatted, result.data.total)
                                trackingReturnScreen = AppScreen.BookingSuccess
                                currentScreen = AppScreen.PaymentSuccess
                                refreshBookings()
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                        isDataLoading = false
                    }
                },
            )
        }

        AppScreen.PaymentSuccess -> {
            PaymentSuccessScreen(
                amount = paidAmount,
                modifier = Modifier.fillMaxSize(),
                onFinished = { currentScreen = AppScreen.BookingSuccess },
            )
        }

        AppScreen.BookingSuccess -> {
            BookingSuccessScreen(
                amount = paidAmount,
                modifier = Modifier.fillMaxSize(),
                onTrackBookingClick = {
                    lastBooking?.let { openTracking(it, AppScreen.BookingSuccess) }
                        ?: run { currentScreen = AppScreen.Tracking }
                },
                onBackToHomeClick = { openHome() },
            )
        }

        AppScreen.Tracking -> {
            val trackingId = lastBooking?.id
            LaunchedEffect(trackingId, currentScreen) {
                val id = trackingId ?: return@LaunchedEffect
                while (isActive) {
                    when (val result = divaRepository.getBooking(id)) {
                        is ApiResult.Success -> lastBooking = result.data
                        is ApiResult.Error -> Unit
                    }
                    val status = lastBooking?.status.orEmpty()
                    if (status == "completed" || status == "cancelled") break
                    delay(5_000)
                }
            }
            TrackingScreen(
                modifier = Modifier.fillMaxSize(),
                bookingId = lastBooking?.publicNumber?.let { "#$it" } ?: lastBooking?.id?.let { "#$it" }.orEmpty(),
                professionalName = lastBooking?.professional?.fullName
                    ?: selectedProfessional?.fullName.orEmpty(),
                serviceName = lastBooking?.service?.name ?: selectedService?.title.orEmpty(),
                serviceDuration = lastBooking?.durationMinutes?.let { "$it mins" }
                    ?: selectedService?.duration.orEmpty(),
                dateTime = listOf(
                    lastBooking?.let { formatBookingDate(it.scheduledStart) },
                    lastBooking?.let { formatBookingTime(it.scheduledStart, it.scheduledEnd) },
                ).filter { !it.isNullOrBlank() }.joinToString(", "),
                location = lastBooking?.address?.displayLine() ?: bookingAddressLine,
                status = lastBooking?.status.orEmpty(),
                statusEvents = lastBooking?.statusEvents.orEmpty(),
                totalLabel = paidAmount,
                isLiveUpdating = lastBooking?.status !in listOf("completed", "cancelled"),
                onBackClick = { currentScreen = trackingReturnScreen },
                onMessageClick = { showToast("Chat coming soon") },
                onCallClick = { showToast("Calling coming soon") },
                onDownloadInvoice = { showToast("Invoices coming soon") },
                onRateServiceClick = { currentScreen = AppScreen.RateService },
            )
        }

        AppScreen.RateService -> {
            RateServiceScreen(
                modifier = Modifier.fillMaxSize(),
                serviceName = lastBooking?.service?.name ?: selectedService?.title.orEmpty(),
                professionalName = lastBooking?.professional?.fullName
                    ?: selectedProfessional?.fullName.orEmpty(),
                onCloseClick = { currentScreen = AppScreen.Tracking },
                onSubmitClick = { rating, review ->
                    val bookingId = lastBooking?.id
                    if (bookingId == null) {
                        showToast("No booking to review")
                        openHome()
                        return@RateServiceScreen
                    }
                    scope.launch {
                        when (val result = divaRepository.createReview(bookingId, rating, review)) {
                            is ApiResult.Success -> {
                                showToast("Thanks for your review")
                                openHome()
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                    }
                },
            )
        }

        AppScreen.Profile -> {
            ProfileScreen(
                modifier = Modifier.fillMaxSize(),
                userName = currentUser?.name ?: "Guest",
                membership = listOfNotNull(
                    currentUser?.email?.takeIf { it.isNotBlank() },
                    currentUser?.phone?.takeIf { it.isNotBlank() },
                ).joinToString("  ·  "),
                avatarUrl = currentUser?.avatarUrl,
                onMenuClick = { showSideMenu = true },
                onEditAvatarClick = { currentScreen = AppScreen.EditProfile },
                onItemClick = { item -> handleMenuItem(item) },
                onLogoutClick = {
                    scope.launch {
                        authRepository.logout()
                        currentUser = null
                        pendingOtp = null
                        favoriteServices = emptyList()
                        bookings = emptyList()
                        addresses = emptyList()
                        notifications = emptyList()
                        unreadNotificationCount = 0
                        currentScreen = AppScreen.Login
                    }
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> openHome()
                        "Services" -> openServices()
                        "Bookings" -> {
                            currentScreen = AppScreen.Bookings
                            scope.launch { refreshBookings() }
                        }
                        "Profile" -> Unit
                        else -> showToast(tab)
                    }
                },
            )
        }

        AppScreen.EditProfile -> {
            EditProfileScreen(
                modifier = Modifier.fillMaxSize(),
                avatarUrl = currentUser?.avatarUrl,
                initialName = currentUser?.name.orEmpty(),
                initialEmail = currentUser?.email.orEmpty(),
                initialPhone = currentUser?.phone.orEmpty(),
                initialDob = currentUser?.dateOfBirth.orEmpty(),
                isSaving = isDataLoading,
                onBackClick = { currentScreen = AppScreen.Profile },
                onEncodeError = { showToast(it) },
                onSaveClick = { name, email, phone, dob, avatarBase64 ->
                    scope.launch {
                        isDataLoading = true
                        try {
                            when (
                                val result = divaRepository.updateProfile(
                                    UpdateProfileRequest(
                                        name = name,
                                        email = email,
                                        phone = phone,
                                        dateOfBirth = dob.ifBlank { null },
                                        avatarBase64 = avatarBase64,
                                    )
                                )
                            ) {
                                is ApiResult.Success -> {
                                    currentUser = result.data
                                    showToast("Profile saved")
                                    currentScreen = AppScreen.Profile
                                }
                                is ApiResult.Error -> showError(result.message)
                            }
                        } finally {
                            isDataLoading = false
                        }
                    }
                },
            )
        }

        AppScreen.Favorites -> {
            FavoritesScreen(
                modifier = Modifier.fillMaxSize(),
                favoriteServices = favoriteServices,
                avatarUrl = currentUser?.avatarUrl,
                onMenuClick = { showSideMenu = true },
                onProfileClick = { currentScreen = AppScreen.Profile },
                onStartExploringClick = { openServices() },
                onServiceClick = { service ->
                    scope.launch {
                        loadServiceDetail(service.id)
                        returnToServices = false
                        currentScreen = AppScreen.ServiceDetails
                    }
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> openHome()
                        "Services" -> openServices()
                        "Bookings" -> {
                            currentScreen = AppScreen.Bookings
                            scope.launch { refreshBookings() }
                        }
                        "Profile" -> currentScreen = AppScreen.Profile
                        else -> showToast(tab)
                    }
                },
            )
        }

        AppScreen.Bookings -> {
            BookingsScreen(
                modifier = Modifier.fillMaxSize(),
                bookings = bookings,
                avatarUrl = currentUser?.avatarUrl,
                onMenuClick = { showSideMenu = true },
                onProfileClick = { currentScreen = AppScreen.Profile },
                onTrackClick = { booking ->
                    openTracking(booking.booking, AppScreen.Bookings)
                },
                onRescheduleClick = { booking ->
                    scope.launch {
                        val serviceId = booking.booking.service?.id
                        if (serviceId == null) {
                            showToast("Unable to reschedule this booking")
                            return@launch
                        }
                        when (
                            val availability = divaRepository.getAvailability(
                                serviceId = serviceId,
                                date = tomorrowDateString(),
                                professionalId = booking.booking.professional?.id,
                            )
                        ) {
                            is ApiResult.Success -> {
                                val slot = availability.data.firstOrNull()
                                if (slot == null) {
                                    showToast("No available slots for tomorrow")
                                    return@launch
                                }
                                when (val result = divaRepository.rescheduleBooking(booking.id, slot.id)) {
                                    is ApiResult.Success -> {
                                        showToast("Booking rescheduled")
                                        refreshBookings()
                                    }
                                    is ApiResult.Error -> showError(result.message)
                                }
                            }
                            is ApiResult.Error -> showError(availability.message)
                        }
                    }
                },
                onCancelClick = { booking ->
                    scope.launch {
                        when (val result = divaRepository.cancelBooking(booking.id)) {
                            is ApiResult.Success -> {
                                showToast("Booking cancelled")
                                refreshBookings()
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                    }
                },
                onRebookClick = { booking ->
                    booking.booking.service?.let { serviceDto ->
                        scope.launch {
                            loadServiceDetail(serviceDto.id)
                            currentScreen = AppScreen.ServiceDetails
                        }
                    }
                },
                onReceiptClick = { booking ->
                    val total = formatMoney(
                        booking.booking.totalFormatted,
                        booking.booking.total,
                    )
                    showToast("${booking.title} • ${booking.date} ${booking.time} • $total (Cash)")
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> openHome()
                        "Services" -> openServices()
                        "Bookings" -> Unit
                        "Profile" -> currentScreen = AppScreen.Profile
                        else -> showToast(tab)
                    }
                },
            )
        }

        AppScreen.Notifications -> {
            NotificationsScreen(
                modifier = Modifier.fillMaxSize(),
                notifications = notifications,
                isLoading = isNotificationsLoading,
                onBackClick = { openHome() },
                onNotificationClick = { item ->
                    scope.launch {
                        if (item.unread) {
                            when (val result = divaRepository.markNotificationRead(item.id)) {
                                is ApiResult.Success -> refreshNotifications()
                                is ApiResult.Error -> showError(result.message)
                            }
                        }
                        if (item.bookingId != null) {
                            showToast(item.title)
                        }
                    }
                },
                onMarkAllRead = {
                    scope.launch {
                        when (val result = divaRepository.markAllNotificationsRead()) {
                            is ApiResult.Success -> {
                                showToast("All marked as read")
                                refreshNotifications()
                            }
                            is ApiResult.Error -> showError(result.message)
                        }
                    }
                },
            )
        }

        AppScreen.PaymentMethods -> {
            PaymentMethodsScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Profile },
            )
        }

        AppScreen.Coupons -> {
            ProfileInfoScreen(
                title = "Coupons",
                sections = listOf(
                    ProfileInfoSection(
                        heading = "WELCOME10",
                        body = "Get 10% off your first booking. Apply at checkout when online payments go live.",
                    ),
                    ProfileInfoSection(
                        heading = "DIVA20",
                        body = "Save 20% on facial and spa packages this week. Terms apply.",
                    ),
                    ProfileInfoSection(
                        heading = "Cash bookings",
                        body = "Coupon auto-apply for online checkout is coming soon. For now, ask your professional about active offers.",
                    ),
                ),
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = infoReturnScreen },
            )
        }

        AppScreen.Referral -> {
            val code = "DIVA${currentUser?.id ?: 0}"
            ProfileInfoScreen(
                title = "Referral",
                sections = listOf(
                    ProfileInfoSection(
                        heading = "Your referral code",
                        body = code,
                    ),
                    ProfileInfoSection(
                        heading = "How it works",
                        body = "Share your code with friends. When they complete their first booking, you both unlock a reward. Referral credits will appear once online payments launch.",
                    ),
                ),
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Profile },
            )
        }

        AppScreen.HelpCenter -> {
            ProfileInfoScreen(
                title = "Help Center",
                sections = listOf(
                    ProfileInfoSection(
                        heading = "Contact us",
                        body = "Email: support@lookdiva.app\nWebsite: https://lookdiva.app\nWe typically reply within 24 hours.",
                    ),
                    ProfileInfoSection(
                        heading = "Bookings",
                        body = "You can reschedule or cancel upcoming bookings from the Bookings tab. Charges for late cancellations may apply.",
                    ),
                    ProfileInfoSection(
                        heading = "Payments",
                        body = "Right now only cash at venue is available. Online payment (UPI / cards) is coming soon.",
                    ),
                ),
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Profile },
            )
        }

        AppScreen.Faqs -> {
            ProfileInfoScreen(
                title = "FAQs",
                sections = listOf(
                    ProfileInfoSection(
                        heading = "How do I book a service?",
                        body = "Pick a service, choose your address and time, then confirm. Pay cash when the professional arrives.",
                    ),
                    ProfileInfoSection(
                        heading = "Can I pay online?",
                        body = "Not yet. Online payment is coming soon. Cash at venue is the only option for now.",
                    ),
                    ProfileInfoSection(
                        heading = "How do favorites work?",
                        body = "Tap the heart on a service while logged in. Find them anytime under Profile → Favorites.",
                    ),
                    ProfileInfoSection(
                        heading = "How do I change my address?",
                        body = "Go to Profile → Saved Address to view, add, or select a location.",
                    ),
                ),
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Profile },
            )
        }

        AppScreen.PrivacyPolicy -> {
            ProfileInfoScreen(
                title = "Privacy Policy",
                sections = listOf(
                    ProfileInfoSection(
                        heading = "Your data",
                        body = "DIVA collects account details (name, phone, email), booking history, and saved addresses to provide salon services through lookdiva.app.",
                    ),
                    ProfileInfoSection(
                        heading = "How we use it",
                        body = "We use your information to manage bookings, send OTP login codes, and improve the app experience. We do not sell your personal data.",
                    ),
                    ProfileInfoSection(
                        heading = "Contact",
                        body = "For privacy requests, email support@lookdiva.app.",
                    ),
                ),
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = infoReturnScreen },
            )
        }
    }

        AppSideMenu(
            visible = showSideMenu,
            userName = currentUser?.name ?: "Guest",
            items = defaultSideMenuItems(),
            onDismiss = { showSideMenu = false },
            onItemClick = { handleMenuItem(it) },
        )
    }
}
