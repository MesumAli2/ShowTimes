package com.mesum.showtimes.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PopularMoviesScreen()
{

    val viewModel: MovieViewModel = viewModel()
    val popularMoviesState by viewModel.popularMoviesState.collectAsState()
    viewModel.fetchPopularMovies()
    val movieList = popularMoviesState

    val configuration = LocalConfiguration.current
    when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {


            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(4)
            ) {
                items(movieList.size) {
                    if (it == movieList.size - 1) {
                        viewModel.fetchTrendingMovies()
                    }

                    MovieGridItem(movie = movieList[it])
                }
            }
        }
        Configuration.ORIENTATION_PORTRAIT -> {

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(2)
            ) {
                items(movieList.size) {
                    if (it == movieList.size - 1) {
                        viewModel.fetchTrendingMovies()
                    }

                    MovieGridItem(movie = movieList[it])
                }
            }
        }



        Configuration.ORIENTATION_UNDEFINED -> {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(2)
            ) {
                items(movieList.size) {
                    if (it == movieList.size - 1) {
                        viewModel.fetchTrendingMovies()
                    }

                    MovieGridItem(movie = movieList[it])
                }
            }
        }
    }








}