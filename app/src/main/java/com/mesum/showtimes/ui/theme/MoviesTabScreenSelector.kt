package com.mesum.showtimes.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.mesum.showtimes.data.Result


@Composable
fun MoviesAndTvShowsScreen(onMovieClicked: () -> Unit,video: (Result) -> Unit,
) {
    val viewMode : MovieViewModel = viewModel()
    val tabs = listOf("Trending", "Upcoming", "Popular", "Top Rated")
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.fillMaxWidth()) {

        Row {


        Image(modifier = Modifier.align(CenterVertically).padding(start = 10.dp)
            .width(30.dp)
            .height(30.dp), painter = painterResource(id = com.mesum.showtimes.R.drawable.app_icon), contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between logo and tabs


            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 8.dp// Don't specify the TabRow's height!
            ) {


                tabs.forEachIndexed { i, text ->
                    Tab(
                        selected = selectedTabIndex == i,
                        onClick = { selectedTabIndex = i },
                        modifier = Modifier.height(50.dp), // Specify the Tab's height instead
                        text = { Text(text) }
                    )
                }
            }

        }

        when(selectedTabIndex){
            0 -> TrendingMoviesScreen(onMovieClicked, video)
            1 -> UpcomingMoviesScreen(onMovieClicked, video)
            2 -> PopularMoviesScreen(onMovieClicked,video)
            3 -> TopRateMoviesScreen(onMovieClicked, video)
        }

        }




    }





@Composable
fun MovieGridItem(movie: Result, onClick: () -> Unit, video: (Result) -> Unit) {
    // Customize the UI for each movie item in the grid here
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick()
            video(movie)
            }
    ) {

        SubcomposeAsyncImage(model ="https://image.tmdb.org/t/p/w500/${movie.poster_path}" ,
            contentDescription = null, loading = { CircularProgressIndicator()})

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}