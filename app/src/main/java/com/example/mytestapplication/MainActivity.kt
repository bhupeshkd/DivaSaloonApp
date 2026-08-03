package com.example.mytestapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytestapplication.ui.AuthSuccessScreen
import com.example.mytestapplication.ui.BookingSuccessScreen
import com.example.mytestapplication.ui.BookingsScreen
import com.example.mytestapplication.ui.CheckoutScreen
import com.example.mytestapplication.ui.ConnectionErrorScreen
import com.example.mytestapplication.ui.EmptyHomeScreen
import com.example.mytestapplication.ui.EditProfileScreen
import com.example.mytestapplication.ui.FavoritesScreen
import com.example.mytestapplication.ui.HomeScreen
import com.example.mytestapplication.ui.HomeSkeletonScreen
import com.example.mytestapplication.ui.LoginScreen
import com.example.mytestapplication.ui.NotificationsScreen
import com.example.mytestapplication.ui.OtpVerificationScreen
import com.example.mytestapplication.ui.PaymentScreen
import com.example.mytestapplication.ui.PaymentSuccessScreen
import com.example.mytestapplication.ui.ProfileScreen
import com.example.mytestapplication.ui.RateServiceScreen
import com.example.mytestapplication.ui.RegisterScreen
import com.example.mytestapplication.ui.ScheduleScreen
import com.example.mytestapplication.ui.SelectLocationScreen
import com.example.mytestapplication.ui.ServiceCatalog
import com.example.mytestapplication.ui.ServiceDetailsScreen
import com.example.mytestapplication.ui.ServiceInfo
import com.example.mytestapplication.ui.ServicesScreen
import com.example.mytestapplication.ui.SplashScreen
import com.example.mytestapplication.ui.TrackingScreen
import com.example.mytestapplication.ui.WalletScreen
import com.example.mytestapplication.ui.WelcomeScreen
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import kotlinx.coroutines.delay

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
    Schedule,
    Checkout,
    SelectLocation,
    Payment,
    PaymentSuccess,
    BookingSuccess,
    Tracking,
    RateService,
    Profile,
    Wallet,
    EditProfile,
    Favorites,
    Bookings,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTestApplicationTheme(dynamicColor = false) {
                DivaApp()
            }
        }
    }
}

@Composable
fun DivaApp() {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf(AppScreen.Splash) }
    var selectedService by remember { mutableStateOf<ServiceInfo?>(ServiceCatalog.bridalMakeup) }
    var paidAmount by remember { mutableStateOf("$249.99") }
    var scheduleLabel by remember { mutableStateOf("Tomorrow, 10:00 AM") }
    var bookingAddressTitle by remember { mutableStateOf("Home Address") }
    var bookingAddressLine by remember {
        mutableStateOf("123 Luxury Lane, Apt 4B, Manhattan, NY 10001")
    }
    var showFilledHome by remember { mutableStateOf(true) }
    var isHomeLoading by remember { mutableStateOf(false) }
    var showConnectionError by remember { mutableStateOf(false) }
    var showErrorAfterLoad by remember { mutableStateOf(false) }
    var returnToServices by remember { mutableStateOf(false) }

    fun openHome(withLoading: Boolean = true, errorAfterLoad: Boolean = false) {
        if (errorAfterLoad) {
            showFilledHome = false
        }
        isHomeLoading = withLoading
        showConnectionError = !withLoading && errorAfterLoad
        showErrorAfterLoad = withLoading && errorAfterLoad
        currentScreen = AppScreen.Home
    }

    when (currentScreen) {
        AppScreen.Splash -> {
            SplashScreen(
                modifier = Modifier.fillMaxSize(),
                onFinished = { currentScreen = AppScreen.Welcome }
            )
        }

        AppScreen.Welcome -> {
            WelcomeScreen(
                modifier = Modifier.fillMaxSize(),
                onLoginClick = { currentScreen = AppScreen.Login },
                onCreateAccountClick = { currentScreen = AppScreen.Register },
                onContinueAsGuestClick = { openHome(withLoading = true, errorAfterLoad = true) }
            )
        }

        AppScreen.Login -> {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Welcome },
                onContinueClick = { _, _ -> openHome(withLoading = true, errorAfterLoad = true) },
                onForgotPasswordClick = {
                    Toast.makeText(context, "Forgot Password", Toast.LENGTH_SHORT).show()
                },
                onGoogleClick = {
                    Toast.makeText(context, "Continue with Google", Toast.LENGTH_SHORT).show()
                },
                onTermsClick = {
                    Toast.makeText(context, "Terms", Toast.LENGTH_SHORT).show()
                },
                onPrivacyClick = {
                    Toast.makeText(context, "Privacy Policy", Toast.LENGTH_SHORT).show()
                }
            )
        }

        AppScreen.Register -> {
            RegisterScreen(
                modifier = Modifier.fillMaxSize(),
                onSignUpClick = { currentScreen = AppScreen.Otp },
                onGoogleSignUpClick = {
                    Toast.makeText(context, "Google sign up", Toast.LENGTH_SHORT).show()
                },
                onLoginClick = { currentScreen = AppScreen.Login }
            )
        }

        AppScreen.Otp -> {
            OtpVerificationScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Register },
                onResendClick = {
                    Toast.makeText(context, "Code resent", Toast.LENGTH_SHORT).show()
                },
                onVerifyClick = {
                    currentScreen = AppScreen.AuthSuccess
                }
            )
        }

        AppScreen.AuthSuccess -> {
            AuthSuccessScreen(
                modifier = Modifier.fillMaxSize(),
                onGetStartedClick = { openHome(withLoading = true, errorAfterLoad = true) }
            )
        }

        AppScreen.Home -> {
            if (isHomeLoading) {
                HomeSkeletonScreen(modifier = Modifier.fillMaxSize())
                LaunchedEffect(Unit) {
                    delay(1800)
                    isHomeLoading = false
                    if (showErrorAfterLoad) {
                        showConnectionError = true
                        showErrorAfterLoad = false
                    }
                }
            } else if (showConnectionError) {
                ConnectionErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNotificationClick = {
                        currentScreen = AppScreen.Notifications
                    },
                    onRetryClick = {
                        openHome(withLoading = true, errorAfterLoad = false)
                    },
                    onOfflineBookingsClick = {
                        Toast.makeText(context, "Offline Bookings", Toast.LENGTH_SHORT).show()
                    },
                    onNavClick = { tab ->
                        when (tab) {
                            "Services" -> currentScreen = AppScreen.Services
                            "Profile" -> currentScreen = AppScreen.Profile
                            "Wallet" -> currentScreen = AppScreen.Wallet
                            "Bookings" -> currentScreen = AppScreen.Bookings
                            else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            } else if (!showFilledHome) {
                EmptyHomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNotificationClick = {
                        currentScreen = AppScreen.Notifications
                    },
                    onExploreServicesClick = {
                        currentScreen = AppScreen.Services
                    },
                    onClaimOfferClick = {
                        selectedService = ServiceCatalog.deepTissue
                        returnToServices = false
                        currentScreen = AppScreen.ServiceDetails
                    },
                    onViewAllCategories = {
                        currentScreen = AppScreen.Services
                    },
                    onCategoryClick = { category ->
                        Toast.makeText(context, category, Toast.LENGTH_SHORT).show()
                    },
                    onNavClick = { tab ->
                        when (tab) {
                            "Services" -> currentScreen = AppScreen.Services
                            "Profile" -> currentScreen = AppScreen.Profile
                            "Wallet" -> currentScreen = AppScreen.Wallet
                            "Bookings" -> currentScreen = AppScreen.Bookings
                            else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            } else {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    onFilterClick = {
                        Toast.makeText(context, "Filters", Toast.LENGTH_SHORT).show()
                    },
                    onNotificationClick = {
                        currentScreen = AppScreen.Notifications
                    },
                    onWalletClick = {
                        currentScreen = AppScreen.Wallet
                    },
                    onSeeAllTrending = {
                        currentScreen = AppScreen.Services
                    },
                    onCategoryClick = { category ->
                        Toast.makeText(context, category, Toast.LENGTH_SHORT).show()
                    },
                    onFeaturedClick = { service ->
                        val details = ServiceCatalog.findByFeaturedTitle(service)
                        if (details != null) {
                            selectedService = details
                            returnToServices = false
                            currentScreen = AppScreen.ServiceDetails
                        } else {
                            Toast.makeText(context, service, Toast.LENGTH_SHORT).show()
                        }
                    },
                    onTrendingBookClick = { service ->
                        val details = ServiceCatalog.findByFeaturedTitle(service)
                        if (details != null) {
                            selectedService = details
                            returnToServices = false
                            currentScreen = AppScreen.Checkout
                        } else {
                            Toast.makeText(context, service, Toast.LENGTH_SHORT).show()
                        }
                    },
                    onFavoriteClick = { title ->
                        Toast.makeText(context, "Saved $title", Toast.LENGTH_SHORT).show()
                    },
                    onPromoClick = {
                        selectedService = ServiceCatalog.glowFacial
                        returnToServices = false
                        currentScreen = AppScreen.ServiceDetails
                    },
                    onNavClick = { tab ->
                        when (tab) {
                            "Home" -> Unit
                            "Services" -> currentScreen = AppScreen.Services
                            "Profile" -> currentScreen = AppScreen.Profile
                            "Bookings" -> currentScreen = AppScreen.Bookings
                            else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }

        AppScreen.Notifications -> {
            NotificationsScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClick = {
                    openHome(withLoading = false)
                },
                onNotificationClick = { title ->
                    Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
                },
                onMarkAllRead = {
                    Toast.makeText(context, "All marked as read", Toast.LENGTH_SHORT).show()
                }
            )
        }

        AppScreen.Services -> {
            ServicesScreen(
                modifier = Modifier.fillMaxSize(),
                onServiceClick = { service ->
                    selectedService = service
                    returnToServices = true
                    currentScreen = AppScreen.ServiceDetails
                },
                onSearchClick = {
                    Toast.makeText(context, "Search services", Toast.LENGTH_SHORT).show()
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> {
                            showFilledHome = true
                            openHome(withLoading = false)
                        }
                        "Services" -> Unit
                        "Bookings" -> currentScreen = AppScreen.Bookings
                        "Profile" -> currentScreen = AppScreen.Profile
                        else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        AppScreen.ServiceDetails -> {
            ServiceDetailsScreen(
                service = selectedService ?: ServiceCatalog.bridalMakeup,
                modifier = Modifier.fillMaxSize(),
                onBackClick = {
                    selectedService = null
                    if (returnToServices) {
                        returnToServices = false
                        currentScreen = AppScreen.Services
                    } else {
                        openHome(withLoading = false)
                    }
                },
                onViewAllProfessionals = {
                    Toast.makeText(context, "View all professionals", Toast.LENGTH_SHORT).show()
                },
                onBookNowClick = {
                    currentScreen = AppScreen.Checkout
                }
            )
        }

        AppScreen.Schedule -> {
            ScheduleScreen(
                modifier = Modifier.fillMaxSize(),
                onMenuClick = {
                    Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
                },
                onProfileClick = {
                    Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
                },
                onCalendarClick = {
                    Toast.makeText(context, "Calendar", Toast.LENGTH_SHORT).show()
                },
                onViewAllProfessionals = {
                    Toast.makeText(context, "All professionals", Toast.LENGTH_SHORT).show()
                },
                onContinueToPayment = { date, time ->
                    scheduleLabel = "$date, $time"
                    currentScreen = AppScreen.Payment
                },
                onNavClick = { tab ->
                    Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                }
            )
        }

        AppScreen.Checkout -> {
            CheckoutScreen(
                service = selectedService ?: ServiceCatalog.bridalMakeup,
                addressTitle = bookingAddressTitle,
                addressLine = bookingAddressLine,
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.ServiceDetails },
                onEditLocationClick = {
                    currentScreen = AppScreen.SelectLocation
                },
                onConfirmBookingClick = {
                    currentScreen = AppScreen.Payment
                }
            )
        }

        AppScreen.SelectLocation -> {
            SelectLocationScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Checkout },
                onAddAddressClick = {
                    Toast.makeText(context, "Add new address", Toast.LENGTH_SHORT).show()
                },
                onAddressMenuClick = { label ->
                    Toast.makeText(context, "$label options", Toast.LENGTH_SHORT).show()
                },
                onConfirmClick = { title, line ->
                    bookingAddressTitle = title
                    bookingAddressLine = line
                    currentScreen = AppScreen.Checkout
                }
            )
        }

        AppScreen.Payment -> {
            PaymentScreen(
                service = selectedService ?: ServiceCatalog.swedishMassage,
                scheduleLabel = scheduleLabel,
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Checkout },
                onViewAllCoupons = {
                    Toast.makeText(context, "All coupons", Toast.LENGTH_SHORT).show()
                },
                onConfirmPayClick = { total ->
                    paidAmount = total
                    currentScreen = AppScreen.PaymentSuccess
                }
            )
        }

        AppScreen.PaymentSuccess -> {
            PaymentSuccessScreen(
                amount = paidAmount,
                modifier = Modifier.fillMaxSize(),
                onFinished = {
                    currentScreen = AppScreen.BookingSuccess
                }
            )
        }

        AppScreen.BookingSuccess -> {
            BookingSuccessScreen(
                amount = paidAmount,
                modifier = Modifier.fillMaxSize(),
                onTrackBookingClick = {
                    currentScreen = AppScreen.Tracking
                },
                onBackToHomeClick = {
                    showFilledHome = true
                    openHome(withLoading = false)
                }
            )
        }

        AppScreen.Tracking -> {
            TrackingScreen(
                modifier = Modifier.fillMaxSize(),
                serviceName = selectedService?.title ?: "Signature Swedish Massage",
                onBackClick = { currentScreen = AppScreen.BookingSuccess },
                onMessageClick = {
                    Toast.makeText(context, "Message Elena", Toast.LENGTH_SHORT).show()
                },
                onCallClick = {
                    Toast.makeText(context, "Calling Elena", Toast.LENGTH_SHORT).show()
                },
                onDownloadInvoice = {
                    Toast.makeText(context, "Downloading invoice", Toast.LENGTH_SHORT).show()
                },
                onRateServiceClick = {
                    currentScreen = AppScreen.RateService
                }
            )
        }

        AppScreen.RateService -> {
            RateServiceScreen(
                modifier = Modifier.fillMaxSize(),
                onCloseClick = { currentScreen = AppScreen.Tracking },
                onSubmitClick = { rating, _ ->
                    Toast.makeText(context, "Thanks for $rating★ review", Toast.LENGTH_SHORT).show()
                    showFilledHome = true
                    openHome(withLoading = false)
                }
            )
        }

        AppScreen.Profile -> {
            ProfileScreen(
                modifier = Modifier.fillMaxSize(),
                onMenuClick = {
                    Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
                },
                onHeaderProfileClick = {
                    Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
                },
                onEditAvatarClick = {
                    currentScreen = AppScreen.EditProfile
                },
                onItemClick = { item ->
                    when (item) {
                        "Edit Profile" -> currentScreen = AppScreen.EditProfile
                        "Favorites" -> currentScreen = AppScreen.Favorites
                        "Booking History" -> currentScreen = AppScreen.Bookings
                        "Wallet" -> currentScreen = AppScreen.Wallet
                        else -> Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                },
                onLogoutClick = {
                    currentScreen = AppScreen.Welcome
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> {
                            showFilledHome = true
                            openHome(withLoading = false)
                        }
                        "Wallet" -> currentScreen = AppScreen.Wallet
                        "Bookings" -> currentScreen = AppScreen.Bookings
                        "Profile" -> Unit
                        else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        AppScreen.Wallet -> {
            WalletScreen(
                modifier = Modifier.fillMaxSize(),
                onMenuClick = {
                    Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
                },
                onProfileClick = {
                    currentScreen = AppScreen.Profile
                },
                onActionClick = { action ->
                    Toast.makeText(context, action, Toast.LENGTH_SHORT).show()
                },
                onViewAllClick = {
                    Toast.makeText(context, "All transactions", Toast.LENGTH_SHORT).show()
                },
                onTransactionClick = { title ->
                    Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> {
                            showFilledHome = true
                            openHome(withLoading = false)
                        }
                        "Profile" -> currentScreen = AppScreen.Profile
                        "Bookings" -> currentScreen = AppScreen.Bookings
                        "Wallet" -> Unit
                        else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        AppScreen.EditProfile -> {
            EditProfileScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClick = { currentScreen = AppScreen.Profile },
                onChangePhotoClick = {
                    Toast.makeText(context, "Change photo", Toast.LENGTH_SHORT).show()
                },
                onSaveClick = { _, _, _, _ ->
                    Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
                    currentScreen = AppScreen.Profile
                }
            )
        }

        AppScreen.Favorites -> {
            FavoritesScreen(
                modifier = Modifier.fillMaxSize(),
                onMenuClick = {
                    Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
                },
                onProfileClick = {
                    currentScreen = AppScreen.Profile
                },
                onStartExploringClick = {
                    showFilledHome = true
                    openHome(withLoading = false)
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> {
                            showFilledHome = true
                            openHome(withLoading = false)
                        }
                        "Wallet" -> currentScreen = AppScreen.Wallet
                        "Profile" -> currentScreen = AppScreen.Profile
                        "Bookings" -> currentScreen = AppScreen.Bookings
                        else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        AppScreen.Bookings -> {
            BookingsScreen(
                modifier = Modifier.fillMaxSize(),
                onMenuClick = {
                    Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
                },
                onProfileClick = {
                    currentScreen = AppScreen.Profile
                },
                onRescheduleClick = { title ->
                    Toast.makeText(context, "Reschedule $title", Toast.LENGTH_SHORT).show()
                },
                onCancelClick = { title ->
                    Toast.makeText(context, "Cancel $title", Toast.LENGTH_SHORT).show()
                },
                onRebookClick = { title ->
                    Toast.makeText(context, "Rebook $title", Toast.LENGTH_SHORT).show()
                },
                onReceiptClick = { title ->
                    Toast.makeText(context, "Receipt $title", Toast.LENGTH_SHORT).show()
                },
                onNavClick = { tab ->
                    when (tab) {
                        "Home" -> {
                            showFilledHome = true
                            openHome(withLoading = false)
                        }
                        "Wallet" -> currentScreen = AppScreen.Wallet
                        "Profile" -> currentScreen = AppScreen.Profile
                        "Bookings" -> Unit
                        else -> Toast.makeText(context, tab, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DivaAppPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        DivaApp()
    }
}
