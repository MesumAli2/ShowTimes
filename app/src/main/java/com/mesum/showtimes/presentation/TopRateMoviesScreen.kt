package com.mesum.showtimes.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mesum.showtimes.data.NetworkStatus
import com.mesum.showtimes.data.Result
import com.mesum.showtimes.ui.theme.MovieViewModel

@Composable
fun TopRateMoviesScreen(onMovieClicked: () -> Unit, video: (Result) -> Unit)
{

    val viewModel: MovieViewModel = viewModel()
    viewModel.fetchTopRatedMovies()
    val topRatedMoviesState by viewModel.topRatedMoviesState.collectAsState()

    val networkStatus by viewModel.networkStatus.collectAsState()
    when (networkStatus) {
        NetworkStatus.Connected -> {
            // Show the movie grid when there's a network connection

            val movieList = topRatedMoviesState
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
                                viewModel.fetchTopRatedMovies()
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
                                viewModel.fetchTopRatedMovies()
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
                                viewModel.fetchTopRatedMovies()
                            }

                            MovieGridItem(movie = movieList[it], onMovieClicked, video)
                        }
                    }
                }
            }

        }
        NetworkStatus.Disconnected -> {
            // Show a "no internet connection" screen when there's no network connection
            NoInternetConnectionScreen(onRetryClick = { viewModel.fetchTopRatedMovies()})
        }
    }




}

@Composable
fun NoInternetConnectionScreen(onRetryClick:() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = com.mesum.showtimes.R.drawable.baseline_wifi_off_24),
            contentDescription = "No Internet",
            modifier = Modifier.size(100.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Internet Connection",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = Color(0xFFCC0000) // Use a slightly lighter red color
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Please check your internet connection and try again.",
            style = TextStyle(fontSize = 16.sp),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onRetryClick() }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Retry")
        }
    }
}
