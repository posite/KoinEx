package com.posite.koinex.di

import com.posite.koinex.KoinApplication.Companion.getString
import com.posite.koinex.R
import com.posite.koinex.data.datasource.category.CategoryDataSource
import com.posite.koinex.data.datasource.meal.MealDataSource
import com.posite.koinex.data.remote.repository.category.CategoryRepositoryImpl
import com.posite.koinex.data.remote.repository.meal.MealRepositoryImpl
import com.posite.koinex.data.remote.service.MealService
import com.posite.koinex.domain.repository.category.CategoryRepository
import com.posite.koinex.domain.repository.meal.MealRepository
import com.posite.koinex.domain.usecase.category.GetCategoriesUseCase
import com.posite.koinex.domain.usecase.meal.GetMealByCategoryUseCase
import com.posite.koinex.ui.presenter.main.MainViewModel
import com.posite.koinex.ui.presenter.main.MealViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/*

    Moshi, OkHttp, Retrofit, Service, DataSource, Repository, UseCase, ViewModel의
    의존성 주입을 위한 Koin module

 */

object KoinModule {
    val mealNetworkModule = module {
        single<Moshi> {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }
        single<OkHttpClient> {
            OkHttpClient.Builder().build()
        }
        single<Retrofit>(named(getString(R.string.meal_qualifier))) {
            Retrofit.Builder()
                .baseUrl(getString(R.string.meal_url))
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create(get()))

                .build()
        }
        single<MealService>(named(getString(R.string.meal_qualifier))) {
            get<Retrofit>(named(getString(R.string.meal_qualifier))).create(MealService::class.java)
        }

    }

    val mealDataModule = module {
        single<CategoryDataSource>(named(getString(R.string.category_qualifier))) {
            CategoryDataSource(get(named(getString(R.string.meal_qualifier))))
        }
        single<CategoryRepository>(named(getString(R.string.category_qualifier))) {
            CategoryRepositoryImpl(get(named(getString(R.string.category_qualifier))))
        }

        single<MealDataSource>(named(getString(R.string.meal_qualifier))) {
            MealDataSource(get(named(getString(R.string.meal_qualifier))))
        }
        single<MealRepository>(named(getString(R.string.meal_qualifier))) {
            MealRepositoryImpl(get(named(getString(R.string.meal_qualifier))))
        }
    }

    val mealDomainModule = module {
        single<GetCategoriesUseCase>(named(getString(R.string.category_qualifier))) {
            GetCategoriesUseCase(get(named(getString(R.string.category_qualifier))))
        }

        single<GetMealByCategoryUseCase>(named(getString(R.string.meal_qualifier))) {
            GetMealByCategoryUseCase(get(named(getString(R.string.meal_qualifier))))
        }
    }

    val mealViewModelModule = module {
        viewModel<MainViewModel>(named(getString(R.string.category_qualifier))) {
            MainViewModel(
                get(
                    named(getString(R.string.category_qualifier))
                )
            )
        }
        viewModel<MealViewModel>(named(getString(R.string.meal_qualifier))) {
            MealViewModel(
                get(
                    named(getString(R.string.meal_qualifier))
                )
            )
        }
    }
}