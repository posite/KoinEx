package com.posite.koinex.data.remote.dto.meal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealResopnse(
    @field:Json(name = "meals") val meals: List<MealDto>
)