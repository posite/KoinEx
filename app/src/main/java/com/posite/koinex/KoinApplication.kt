package com.posite.koinex

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.posite.koinex.di.RetrofitModule
import com.posite.koinex.util.NetworkChecker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class KoinApplication : Application(), DefaultLifecycleObserver {
    override fun onCreate() {
        super<Application>.onCreate()
        context = applicationContext
        networkConnectionChecker = NetworkChecker(context)

        startKoin {
            androidContext(this@KoinApplication)
            modules(RetrofitModule.moshiModule)
            modules(RetrofitModule.okhttpModule)
            modules(RetrofitModule.mealRetrofitModule)
            modules(RetrofitModule.mealDataSource)
            modules(RetrofitModule.mealRepositoryModule)
            modules(RetrofitModule.mealUseCaseModule)
            modules(RetrofitModule.mealViewModelModule)
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