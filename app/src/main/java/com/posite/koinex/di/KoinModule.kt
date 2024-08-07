package com.posite.koinex.di

import com.posite.koinex.KoinApplication.Companion.getString
import com.posite.koinex.R
import com.posite.koinex.data.datasource.MealDataSource
import com.posite.koinex.data.remote.repository.MealRepositoryImpl
import com.posite.koinex.data.remote.service.MealService
import com.posite.koinex.domain.repository.MealRepository
import com.posite.koinex.domain.usecase.GetCategoriesUseCase
import com.posite.koinex.ui.presenter.main.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
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
        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(getString(R.string.meal_url))
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create(get()))

                .build()
        }
        single<MealService> {
            get<Retrofit>().create(MealService::class.java)
        }

    }

    val mealDataModule = module {
        single<MealDataSource> {
            MealDataSource(get())
        }
        single<MealRepository> {
            MealRepositoryImpl(get())
        }
    }

    val mealDomainModule = module {
        single<GetCategoriesUseCase> {
            GetCategoriesUseCase(get())
        }
    }

    val mealViewModelModule = module {
        viewModel<MainViewModel> { MainViewModel(get()) }
    }
}