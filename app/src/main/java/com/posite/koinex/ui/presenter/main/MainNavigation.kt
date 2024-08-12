package com.posite.koinex.ui.presenter.main

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.posite.koinex.data.remote.dto.category.CategoryDto

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavigation(
    mainViewModel: MainViewModel,
    mealViewModel: MealViewModel,
    navController: NavHostController
) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Screens.CategoryScreen.route) {
            composable(
                route = Screens.CategoryScreen.route,
                //enterTransition = enterTransition,
                //exitTransition = exitTransition
            ) {
                CategoriesScreen(mainViewModel, this@SharedTransitionLayout) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                    Log.d("Category", "category: $it")
                    navController.navigate(Screens.MealScreen.route)
                }
            }
            composable(
                route = Screens.MealScreen.route,
                //enterTransition = enterTransition,
                //exitTransition = exitTransition
            ) {
                val category =
                    navController.previousBackStackEntry?.savedStateHandle?.get<CategoryDto>("category")
                        ?: CategoryDto("", "", "")
                Log.d("Detail", "category: $category")
                MealScreen(
                    category = category.strCategory,
                    mealViewModel = mealViewModel,
                    this@SharedTransitionLayout
                ) {
                    navController.popBackStack()
                    mealViewModel.clearAll()
                }
            }
        }
    }

}


object AnimationTransitions {
    private const val TIME_DURATION = 300

    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(durationMillis = TIME_DURATION, easing = FastOutLinearInEasing)
        )
    }

    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        fadeOut(
            targetAlpha = 0.3f,
            animationSpec = tween(durationMillis = TIME_DURATION, easing = FastOutLinearInEasing)
        )
    }


}