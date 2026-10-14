package com.example.mytestapplication.data.api.dto

import com.google.gson.annotations.SerializedName

data class DataWrapper<T>(
    val data: T,
)

data class PaginatedResponse<T>(
    val data: List<T>,
    val meta: PaginationMeta? = null,
)

data class PaginationMeta(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("last_page")
    val lastPage: Int? = null,
    val total: Int? = null,
)

data class MessageResponse(
    val message: String? = null,
)

data class ValidationError(
    val message: String? = null,
    val errors: Map<String, List<String>>? = null,
)
