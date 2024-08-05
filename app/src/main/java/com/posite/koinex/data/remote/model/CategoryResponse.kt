package com.posite.koinex.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    @field:Json(name = "categories") val categories: List<Category>
)