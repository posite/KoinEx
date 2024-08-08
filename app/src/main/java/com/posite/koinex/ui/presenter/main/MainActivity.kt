package com.posite.koinex.ui.presenter.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.posite.koinex.R
import com.posite.koinex.data.remote.model.category.Category
import com.posite.koinex.data.remote.model.meal.Meal
import com.posite.koinex.ui.presenter.main.AnimationTransitions.enterTransition
import com.posite.koinex.ui.presenter.main.AnimationTransitions.exitTransition
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

@Composable
fun MainNavigation(
    mainViewModel: MainViewModel,
    mealViewModel: MealViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screens.CategoryScreen.route) {
        composable(
            route = Screens.CategoryScreen.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition
        ) {
            CategoriesScreen(mainViewModel) {
                navController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                Log.d("Category", "category: $it")
                navController.navigate(Screens.MealScreen.route)
            }
        }
        composable(
            route = Screens.MealScreen.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition
        ) {
            val category =
                navController.previousBackStackEntry?.savedStateHandle?.get<Category>("category")
                    ?: Category("", "", "")
            Log.d("Detail", "category: $category")
            MealScreen(category = category.strCategory, mealViewModel = mealViewModel) {
                navController.navigateUp()
                mealViewModel.clearAll()
            }
        }
    }
}


@Composable
fun CategoriesScreen(viewModel: MainViewModel, navigateToDetail: (Category) -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        val state = viewModel.currentState
        //이미지들이 한번에 로드되는 것 처럼 보이려면 isLoading 으로 확인 필요

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (viewModel.currentState.visible.visibility) 1f else 0f)
        ) {
            items(state.categories.categories) {
                MealCategory(it, navigateToDetail)
            }
        }
        when (state.loadState) {
            is MainContract.CategoryListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MainContract.CategoryListState.Success -> {
                Log.d("MealCategories", "Success")
                viewModel.setVisible()
            }

            is MainContract.CategoryListState.Before -> {
                viewModel.getCategories()
                Log.d("MealCategories", "Before")
            }

            else -> {
                Toast.makeText(
                    context,
                    (state.loadState as MainContract.CategoryListState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}

@Composable
fun MealCategory(category: Category, navigateToDetail: (Category) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                navigateToDetail(category)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.strCategoryThumb)
                .crossfade(true)
                .build(),
            contentDescription = category.strCategoryDescription
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.strCategory,
            style = TextStyle(fontSize = 20.sp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealScreen(category: String, mealViewModel: MealViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) { innerPadding ->
        val state = mealViewModel.currentState
        //이미지들이 한번에 로드되는 것 처럼 보이려면 isLoading 으로 확인 필요

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .alpha(if (mealViewModel.currentState.visible.visibility) 1f else 0f)
        ) {
            items(state.meals.meals) {
                MealList(it)
            }
        }
        when (state.loadState) {
            is MealContract.MealListState.LoadState.Loading -> {
                CircularProgressIndicator()
            }

            is MealContract.MealListState.LoadState.Success -> {
                Log.d("MealCategories", "Success")
                mealViewModel.setVisible()
            }

            is MealContract.MealListState.LoadState.Before -> {
                mealViewModel.getMeals(category)
                Log.d("MealCategories", "Before")
            }
        }


    }
}

@Composable
fun MealList(meal: Meal) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(meal.strMealThumb)
                .crossfade(true)
                .build(),
            contentDescription = meal.strMeal
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = meal.strMeal,
            style = TextStyle(fontSize = 20.sp)
        )

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