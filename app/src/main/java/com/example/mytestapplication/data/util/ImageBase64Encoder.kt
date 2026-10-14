package com.example.mytestapplication.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.example.mytestapplication.BuildConfig
import java.io.ByteArrayOutputStream
import kotlin.math.max

object ImageBase64Encoder {

    fun encodeJpegDataUri(
        context: Context,
        uri: Uri,
        maxSide: Int = 1024,
        quality: Int = 85,
    ): String {
        val original = context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream)
        } ?: error("Unable to read selected image")

        val scaled = scaleDown(original, maxSide)
        return try {
            val bytes = ByteArrayOutputStream().use { out ->
                scaled.compress(Bitmap.CompressFormat.JPEG, quality, out)
                out.toByteArray()
            }
            val encoded = Base64.encodeToString(bytes, Base64.NO_WRAP)
            "data:image/jpeg;base64,$encoded"
        } finally {
            if (scaled !== original) scaled.recycle()
            original.recycle()
        }
    }

    private fun scaleDown(bitmap: Bitmap, maxSide: Int): Bitmap {
        val largest = max(bitmap.width, bitmap.height)
        if (largest <= maxSide) return bitmap
        val scale = maxSide.toFloat() / largest.toFloat()
        val width = (bitmap.width * scale).toInt().coerceAtLeast(1)
        val height = (bitmap.height * scale).toInt().coerceAtLeast(1)
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
}

fun resolveMediaUrl(url: String?): String? {
    if (url.isNullOrBlank()) return null
    val apiOrigin = apiOrigin()
    val absolute = when {
        url.startsWith("content://") ||
            url.startsWith("file://") ||
            url.startsWith("data:") -> url
        url.startsWith("http://") || url.startsWith("https://") -> url
        url.startsWith("/") -> "$apiOrigin$url"
        else -> "$apiOrigin/$url"
    }
    return rewriteLoopbackHost(absolute, apiOrigin)
}

private fun apiOrigin(): String {
    return BuildConfig.API_BASE_URL
        .trim()
        .removeSuffix("/")
        .removeSuffix("/api")
        .trimEnd('/')
}

private fun rewriteLoopbackHost(url: String, apiOrigin: String): String {
    val match = Regex("^https?://(?:localhost|127\\.0\\.0\\.1)(?::\\d+)?", RegexOption.IGNORE_CASE)
        .find(url) ?: return url
    val path = url.substring(match.range.last + 1)
    val origin = if (
        apiOrigin.contains("localhost", ignoreCase = true) ||
        apiOrigin.contains("127.0.0.1") ||
        apiOrigin.contains("10.0.2.2")
    ) {
        "http://10.0.2.2:8000"
    } else {
        apiOrigin
    }
    return origin.trimEnd('/') + path
}
