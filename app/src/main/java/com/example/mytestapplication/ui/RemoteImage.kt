package com.example.mytestapplication.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.mytestapplication.R
import com.example.mytestapplication.data.util.resolveMediaUrl

private val ImagePlaceholderBg = Color(0xFFF3F1EE)
private val ImagePlaceholderText = Color(0xFF8A8A8A)

@Composable
fun RemoteImage(
    imageUrl: String?,
    @DrawableRes fallbackRes: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    showUnavailableOnError: Boolean = false,
) {
    val resolved = resolveMediaUrl(imageUrl)
    if (!resolved.isNullOrBlank()) {
        SubcomposeAsyncImage(
            model = resolved,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                is AsyncImagePainter.State.Error -> {
                    if (showUnavailableOnError) {
                        ImageUnavailable(Modifier.fillMaxSize())
                    } else {
                        Image(
                            painter = painterResource(fallbackRes),
                            contentDescription = contentDescription,
                            contentScale = contentScale,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
                else -> Box(Modifier.fillMaxSize().background(ImagePlaceholderBg))
            }
        }
    } else if (showUnavailableOnError) {
        ImageUnavailable(modifier)
    } else {
        Image(
            painter = painterResource(fallbackRes),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        )
    }
}

@Composable
fun ServiceImage(
    service: ServiceInfo,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    RemoteImage(
        imageUrl = service.imageUrl,
        fallbackRes = R.drawable.service_bridal,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        showUnavailableOnError = true,
    )
}

@Composable
fun ProfessionalImage(
    professional: ProfessionalInfo,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    @DrawableRes fallbackRes: Int = R.drawable.pro_elena,
) {
    RemoteImage(
        imageUrl = professional.avatarUrl,
        fallbackRes = professional.imageRes ?: fallbackRes,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}

@Composable
private fun ImageUnavailable(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(ImagePlaceholderBg),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Image not available",
            color = ImagePlaceholderText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
        )
    }
}
