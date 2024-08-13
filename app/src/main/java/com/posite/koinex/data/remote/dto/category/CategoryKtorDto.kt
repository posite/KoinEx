package com.posite.koinex.data.remote.dto.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryKtorDto(
    @SerialName("strCategory")
    val strCategory: String,
    @SerialName("strCategoryThumb")
    val strCategoryThumb: String,
    @SerialName("strCategoryDescription")
    val strCategoryDescription: String
)