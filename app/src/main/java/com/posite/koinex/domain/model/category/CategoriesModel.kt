package com.posite.koinex.domain.model.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesModel(
    val categories: List<CategoryModel>
) : Parcelable