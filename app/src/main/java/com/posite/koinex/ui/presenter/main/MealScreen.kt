package com.posite.koinex.ui.presenter.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.posite.koinex.domain.model.meal.MealModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MealScreen(
    category: String,
    mealViewModel: MealViewModel,
    scope: SharedTransitionScope,
    onBack: () -> Unit
) {
    val visible = remember {
        mutableStateOf(true)
    }
    with(scope) {
        AnimatedVisibility(
            visible = true,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                visible.value = false
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
                    .sharedBounds(
                        rememberSharedContentState(key = "category-${category}"),
                        animatedVisibilityScope = this@AnimatedVisibility,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut(),
                        resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                    )

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
                    items(state.meals.meals.meals) {
                        MealList(it)
                    }
                }
                when (state.loadState) {
                    is MealContract.MealListState.LoadState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is MealContract.MealListState.LoadState.Success -> {
                        Log.d("MealScreen", "Success")
                        mealViewModel.setVisible()
                    }

                    is MealContract.MealListState.LoadState.Before -> {
                        if (visible.value) {
                            mealViewModel.setInit()
                            Log.d("MealScreen", "Before")
                        }

                    }

                    is MealContract.MealListState.LoadState.Init -> {
                        Log.d("MealScreen", "Init")
                        mealViewModel.getMeals(category)
                    }
                }


            }
        }
    }
}

@Composable
fun MealList(meal: MealModel) {
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