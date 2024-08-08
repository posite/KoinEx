package com.posite.koinex.ui.presenter.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.posite.koinex.R
import com.posite.koinex.ui.theme.KoinExTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
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
                categoryViewModel = getViewModel(named(getString(R.string.category_qualifier)))
                mealViewModel = getViewModel(named(getString(R.string.meal_qualifier)))
                MainNavigation(categoryViewModel, mealViewModel, navHostController)
                onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
            }
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navHostController.currentDestination?.route == Screens.MealScreen.route) {
                    mealViewModel.clearAll()
                    navHostController.popBackStack()
                }
            }
        }
}