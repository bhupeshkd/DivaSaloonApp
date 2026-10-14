package com.example.mytestapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.ui.theme.HomeBg
import com.example.mytestapplication.ui.theme.HomeSubtitle
import com.example.mytestapplication.ui.theme.HomeTitle
import com.example.mytestapplication.ui.theme.OtpWhite
import com.example.mytestapplication.ui.theme.WelcomeGold

data class SideMenuItem(
    val title: String,
    val section: String? = null,
)

@Composable
fun AppSideMenu(
    visible: Boolean,
    userName: String,
    items: List<SideMenuItem>,
    onDismiss: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
                    .clickable(onClick = onDismiss)
            )

            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally { -it },
                exit = slideOutHorizontally { -it },
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp)
                        .shadow(12.dp, RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                        .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                        .background(OtpWhite)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = "DIVA",
                        color = WelcomeGold,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 2.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hello, ${userName.ifBlank { "Guest" }}",
                        color = HomeSubtitle,
                        fontSize = 13.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFE8E4DF))
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        var lastSection: String? = null
                        items.forEach { item ->
                            if (item.section != null && item.section != lastSection) {
                                if (lastSection != null) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                                Text(
                                    text = item.section,
                                    color = HomeSubtitle,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                )
                                lastSection = item.section
                            }
                            Text(
                                text = item.title,
                                color = HomeTitle,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { onItemClick(item.title) }
                                    .background(HomeBg.copy(alpha = 0.35f))
                                    .padding(horizontal = 12.dp, vertical = 12.dp),
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        }
    }
}

fun defaultSideMenuItems(): List<SideMenuItem> = listOf(
    SideMenuItem("Home", "Navigate"),
    SideMenuItem("Services", "Navigate"),
    SideMenuItem("Bookings", "Navigate"),
    SideMenuItem("Favorites", "Navigate"),
    SideMenuItem("Profile", "Navigate"),
    SideMenuItem("Edit Profile", "Account"),
    SideMenuItem("Saved Address", "Account"),
    SideMenuItem("Payment Methods", "Account"),
    SideMenuItem("Coupons", "More"),
    SideMenuItem("Referral", "More"),
    SideMenuItem("Help Center", "More"),
    SideMenuItem("FAQs", "More"),
    SideMenuItem("Privacy Policy", "More"),
)
