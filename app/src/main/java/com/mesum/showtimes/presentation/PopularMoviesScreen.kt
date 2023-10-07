package com.mesum.showtimes.presentation

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
import com.mesum.showtimes.data.NetworkStatus
import com.mesum.showtimes.data.Result
import com.mesum.showtimes.ui.theme.MovieViewModel

@Composable
fun PopularMoviesScreen(
    onMovieClicked: () -> Unit,
    video: (Result) -> Unit,
    viewModel: MovieViewModel = viewModel() // Pass the ViewModel as a parameter
) {

    viewModel.fetchPopularMovies()
    val popularMoviesState by viewModel.popularMoviesState.collectAsState()
    val movieList = popularMoviesState

    val networkStatus by viewModel.networkStatus.collectAsState()
    when (networkStatus) {
      is  NetworkStatus.Connected -> {
            // Show the movie grid when there's a network connection
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
                                viewModel.fetchPopularMovies()
                            }
                            MovieGridItem(movie = movieList[it], onMovieClicked, video)
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
                                viewModel.fetchPopularMovies()
                            }
                            MovieGridItem(movie = movieList[it], onMovieClicked, video)
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
                                viewModel.fetchPopularMovies()
                            }
                            MovieGridItem(movie = movieList[it], onMovieClicked, video)
                        }
                    }
                }
            }
        }
       is NetworkStatus.Disconnected -> {
           // Show a "no internet connection" screen when there's no network connection
            NoInternetConnectionScreen(onRetryClick = { viewModel.fetchPopularMovies() })
        }
    }
}
