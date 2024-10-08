package com.posite.koinex.ui.presenter.main

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.posite.koinex.domain.model.category.CategoryModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CategoriesScreen(
    viewModel: MainViewModel,
    navigateToDetail: (CategoryModel) -> Unit
) {
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
                .alpha(if (viewModel.currentState.loadState is MainContract.CategoryListState.Visible) 1f else 0f)
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

            is MainContract.CategoryListState.Visible -> {
                Log.d("MealCategories", "Visible")
            }

            is MainContract.CategoryListState.Invisible -> {
                Log.d("MealCategories", "Invisible")
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
fun MealCategory(
    category: CategoryModel,
    navigateToDetail: (CategoryModel) -> Unit
) {
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