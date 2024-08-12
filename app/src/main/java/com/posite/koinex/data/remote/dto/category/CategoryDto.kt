package com.posite.koinex.data.remote.dto.category

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CategoryDto(
    @field:Json(name = "strCategory") val strCategory: String,
    @field:Json(name = "strCategoryThumb") val strCategoryThumb: String,
    @field:Json(name = "strCategoryDescription") val strCategoryDescription: String
) : Parcelable