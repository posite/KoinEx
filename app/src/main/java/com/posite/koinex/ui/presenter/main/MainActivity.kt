package com.posite.koinex.ui.presenter.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.posite.koinex.R
import com.posite.koinex.ui.theme.KoinExTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var categoryViewModel: MainViewModel
    private lateinit var mealViewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinExTheme {
                navHostController = rememberNavController()
                categoryViewModel = koinViewModel(named(getString(R.string.category_qualifier)))
                mealViewModel = koinViewModel(named(getString(R.string.meal_qualifier)))
                onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
                MainNavigation(categoryViewModel, mealViewModel, navHostController)
            }
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("MainActivity", "${navHostController.currentDestination?.route}")
                if (navHostController.currentDestination?.route == Screens.MealScreen.route) {
                    categoryViewModel.setVisible()
                    navHostController.popBackStack()
                    mealViewModel.clearAll()
                }
            }
        }
}