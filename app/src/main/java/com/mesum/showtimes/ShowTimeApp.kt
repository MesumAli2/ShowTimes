package com.mesum.showtimes

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.mesum.showtimes.ui.theme.MoviesAndTvShowsScreen


@Composable
fun ShowTimeApp() {
    val viewModel : MovieViewModel = viewModel()

    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = ShowTime.Movies.name,
        modifier = androidx.compose.ui.Modifier.padding(),
    ) {
        composable(route = ShowTime.Movies.name) {
           MoviesAndTvShowsScreen (onMovieClicked = {
               navController.navigate(ShowTime.Trailer.name)

           }, { Movie ->
                Log.d("MovieInfo", Movie.toString())

               viewModel.updatedTrailer(Movie)

           })
        }

        composable(route = ShowTime.Trailer.name) {

            TrailerScreen(viewModel, videoString = { videoString ->
                Log.d("VideoString", videoString)
                viewModel.fetchVideo(videoString)
            })

        }



    }



}


enum class ShowTime(@StringRes val title: Int) {
    Movies(title = R.string.showtime),
    Trailer(title = R.string.movie)

    }
