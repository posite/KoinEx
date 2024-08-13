package com.posite.koinex

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.posite.koinex.di.KtorModule
import com.posite.koinex.util.network.NetworkChecker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class KoinApplication : Application(), DefaultLifecycleObserver {
    override fun onCreate() {
        super<Application>.onCreate()
        context = applicationContext
        networkConnectionChecker = NetworkChecker(context)


        // 런타임에서 의존성 주입을 사용할 수 있도록 Application context와 Koin module 등록
        startKoin {
            androidContext(this@KoinApplication)
            /*modules(
                KoinModule.mealNetworkModule,
                KoinModule.mealDataModule,
                KoinModule.mealDomainModule,
                KoinModule.mealViewModelModule
            )*/
            modules(
                KtorModule.mealNetworkModule,
                KtorModule.mealDataModule,
                KtorModule.mealDomainModule,
                KtorModule.mealViewModelModule
            )
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        networkConnectionChecker.unregister()
        super.onStop(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        networkConnectionChecker.register()
        super.onStart(owner)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getString(@StringRes stringResId: Int): String {
            return context.getString(stringResId)
        }

        private lateinit var networkConnectionChecker: NetworkChecker
        fun isOnline() = networkConnectionChecker.isOnline()
    }
}