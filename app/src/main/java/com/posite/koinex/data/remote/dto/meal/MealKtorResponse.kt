package com.posite.koinex.data.remote.dto.meal

import kotlinx.serialization.Serializable

@Serializable
data class MealKtorResponse(
    val meals: List<MealKtorDto>
)