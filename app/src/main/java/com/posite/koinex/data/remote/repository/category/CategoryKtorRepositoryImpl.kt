package com.posite.koinex.data.remote.repository.category

import com.posite.koinex.data.datasource.category.CategoryKtorDataSource
import com.posite.koinex.domain.model.category.CategoriesModel
import com.posite.koinex.domain.repository.category.CategoryKtorRepository
import com.posite.koinex.util.mapper.CategoryKtorMapper
import com.posite.koinex.util.network.DataResult

class CategoryKtorRepositoryImpl(
    private val categoryDataSource: CategoryKtorDataSource,
    private val mapper: CategoryKtorMapper
) :
    CategoryKtorRepository {
    override suspend fun getCategories(): DataResult<CategoriesModel> {
        return mapper(categoryDataSource.fetchCategories())
    }
}