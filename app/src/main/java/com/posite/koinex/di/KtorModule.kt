package com.posite.koinex.di

import android.util.Log
import com.posite.koinex.KoinApplication.Companion.getString
import com.posite.koinex.R
import com.posite.koinex.data.datasource.category.CategoryKtorDataSource
import com.posite.koinex.data.datasource.meal.MealKtorDataSource
import com.posite.koinex.data.remote.repository.category.CategoryKtorRepositoryImpl
import com.posite.koinex.data.remote.repository.meal.MealKtorRepositoryImpl
import com.posite.koinex.data.remote.service.MealKtorService
import com.posite.koinex.domain.repository.category.CategoryKtorRepository
import com.posite.koinex.domain.repository.meal.MealKtorRepository
import com.posite.koinex.domain.usecase.category.GetKtorCategoriesUseCase
import com.posite.koinex.domain.usecase.meal.GetKtorMealByCategoryUseCase
import com.posite.koinex.ui.presenter.main.MainViewModel
import com.posite.koinex.ui.presenter.main.MealViewModel
import com.posite.koinex.util.mapper.CategoryKtorMapper
import com.posite.koinex.util.mapper.MealKtorMapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object KtorModule {
    private const val NETWORK_TIME_OUT = 6_000L

    val mealNetworkModule = module {
        single<HttpClient> {
            HttpClient(Android) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            useAlternativeNames = true
                            ignoreUnknownKeys = true
                            encodeDefaults = false
                        }
                    )
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = NETWORK_TIME_OUT
                    connectTimeoutMillis = NETWORK_TIME_OUT
                    socketTimeoutMillis = NETWORK_TIME_OUT
                }

                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("Logger Ktor =>", message)
                        }
                    }
                    level = LogLevel.ALL
                }

                install(ResponseObserver) {
                    onResponse { response ->
                        Log.d("HTTP status:", "${response.status.value}")
                    }
                }

                install(DefaultRequest) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }

                defaultRequest {
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            }
        }

        single<MealKtorService>(named(getString(R.string.meal_qualifier))) {
            MealKtorService(get())
        }
    }

    val mealDataModule = module {
        single<CategoryKtorDataSource>(named(getString(R.string.category_qualifier))) {
            CategoryKtorDataSource(get(named(getString(R.string.meal_qualifier))))
        }
        single<CategoryKtorMapper>(named(getString(R.string.category_qualifier))) {
            CategoryKtorMapper()
        }
        single<CategoryKtorRepository>(named(getString(R.string.category_qualifier))) {
            CategoryKtorRepositoryImpl(
                get(named(getString(R.string.category_qualifier))),
                get(named(getString(R.string.category_qualifier)))
            )
        }

        single<MealKtorDataSource>(named(getString(R.string.meal_qualifier))) {
            MealKtorDataSource(get(named(getString(R.string.meal_qualifier))))
        }

        single<MealKtorMapper>(named(getString(R.string.meal_qualifier))) {
            MealKtorMapper()
        }
        single<MealKtorRepository>(named(getString(R.string.meal_qualifier))) {
            MealKtorRepositoryImpl(
                get(named(getString(R.string.meal_qualifier))),
                get((named(getString(R.string.meal_qualifier))))
            )
        }
    }

    val mealDomainModule = module {
        single<GetKtorCategoriesUseCase>(named(getString(R.string.category_qualifier))) {
            GetKtorCategoriesUseCase(get(named(getString(R.string.category_qualifier))))
        }

        single<GetKtorMealByCategoryUseCase>(named(getString(R.string.meal_qualifier))) {
            GetKtorMealByCategoryUseCase(get(named(getString(R.string.meal_qualifier))))
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