package com.posite.koinex.domain.model.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
) : Parcelable