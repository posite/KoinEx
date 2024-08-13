package com.posite.koinex.data.remote.dto.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryKtorResponse(
    @SerialName("categories")
    val categories: List<CategoryKtorDto>
)