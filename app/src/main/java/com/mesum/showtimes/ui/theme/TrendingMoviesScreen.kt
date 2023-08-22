package com.mesum.showtimes.ui.theme

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mesum.showtimes.data.Result

@Composable
fun TrendingMoviesScreen(name: String, modifier: Modifier = Modifier) {

    val viewModel: MovieViewModel = viewModel()
    val trendingMoviesState by viewModel.trendingMoviesState.collectAsState()
    val movieList = trendingMoviesState

    val configuration = LocalConfiguration.current
    when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
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
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
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
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
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

@Composable
fun MovieGridItem(movie: Result) {
    // Customize the UI for each movie item in the grid here
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        SubcomposeAsyncImage(model ="https://image.tmdb.org/t/p/w500/${movie.poster_path}" ,
            contentDescription = null, loading = { CircularProgressIndicator()})
//        Image(
//            painter = rememberImagePainter(movie.posterPath),
//            contentDescription = movie.title,
//            modifier = Modifier
//                .size(150.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(Color.Gray)
//        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}