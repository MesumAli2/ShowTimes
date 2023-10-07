package com.mesum.showtimes.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mesum.showtimes.TrailerScreen
import com.mesum.showtimes.data.toResult
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.mesum.showtimes.presentation.PopularMoviesScreen
import com.mesum.showtimes.presentation.SearchMoviesScreen
import com.mesum.showtimes.presentation.TopRateMoviesScreen
import com.mesum.showtimes.presentation.TrendingMoviesScreen
import com.mesum.showtimes.presentation.UpcomingMoviesScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieNavHost(navController: NavHostController, viewModel: MovieViewModel, padding : PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Movies.Trending.name,
        modifier = Modifier.padding( padding),
    ) {
        composable(route = Movies.Trending.name) {
            viewModel.fetchTrendingMovies()
            TrendingMoviesScreen({
                navController.navigate(ShowTime.Trailer.name)
            }, { video ->
                viewModel.updatedTrailer(video)
            })
        }

        composable(route = Movies.Upcoming.name) {
            viewModel.fetchUpcomingMovies()
            UpcomingMoviesScreen({
                navController.navigate(ShowTime.Trailer.name)
            }, { video ->
                viewModel.updatedTrailer(video)
            })
        }

        composable(route = Movies.Popular.name) {
            viewModel.fetchPopularMovies()
            PopularMoviesScreen({
                navController.navigate(ShowTime.Trailer.name)
            }, { video ->
                viewModel.updatedTrailer(video)
            })
        }

        composable(route = Movies.TopRated.name) {
            TopRateMoviesScreen({
                navController.navigate(ShowTime.Trailer.name)
            }, { video ->
                viewModel.updatedTrailer(video)
            })
        }

        composable(route = Movies.SearchScreen.name) {
            SearchMoviesScreen(
                searchQuery = viewModel.stringState.value,
                onSearchQueryChanged = { query ->
                    viewModel.searchMovies(query = query)
                },
                onMovieClicked = {
                    navController.navigate(ShowTime.Trailer.name)
                },
                video = { viewModel.updatedTrailer(it.toResult()) },
                viewModel = viewModel
            )
        }

        composable(route = ShowTime.Trailer.name) {
            val videoDetails = viewModel.uiState.collectAsState().value
            TrailerScreen(viewModel, videoString = {
                videoDetails.original_title.toString()
                viewModel.fetchVideo(it)
            })
        }
    }

}