package com.example.mytestapplication.data.repository

import com.example.mytestapplication.data.api.dto.ValidationError
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

object ApiErrorParser {

    private val gson = Gson()

    fun messageFrom(exception: Exception): String = when (exception) {
        is HttpException -> parseHttpException(exception)
        is IOException -> "Unable to reach the server. Check your connection."
        else -> exception.message ?: "Something went wrong."
    }

    private fun parseHttpException(exception: HttpException): String {
        val body = exception.response()?.errorBody()?.string()
        if (!body.isNullOrBlank()) {
            runCatching {
                gson.fromJson(body, ValidationError::class.java)
            }.getOrNull()?.let { error ->
                error.errors?.values?.flatten()?.firstOrNull()?.let { return it }
                error.message?.let { return it }
            }
        }
        return when (exception.code()) {
            401 -> "Session expired. Please log in again."
            404 -> "Resource not found."
            422 -> "Please check your input and try again."
            else -> "Request failed (${exception.code()})."
        }
    }
}

suspend fun <T> apiCall(block: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: Exception) {
        ApiResult.Error(ApiErrorParser.messageFrom(e))
    }
}
