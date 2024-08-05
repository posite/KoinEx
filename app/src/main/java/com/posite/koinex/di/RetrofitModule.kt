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


object RetrofitModule {
    val moshiModule = module {
        single<Moshi> {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }
    }

    val okhttpModule = module {
        single<OkHttpClient> {
            OkHttpClient.Builder().build()
        }
    }

    val mealRetrofitModule = module {
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

    val mealDataSource = module {
        single<MealDataSource> {
            MealDataSource(get())
        }
    }

    val mealRepositoryModule = module {
        single<MealRepository> {
            MealRepositoryImpl(get())
        }
    }

    val mealUseCaseModule = module {
        single<GetCategoriesUseCase> {
            GetCategoriesUseCase(get())
        }
    }

    val mealViewModelModule = module {
        viewModel<MainViewModel> { MainViewModel(get()) }
    }
}