package com.mesum.showtimes.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.mesum.showtimes.data.Result



@Composable
fun MoviesAndTvShowsScreen(lifecycle: LifecycleOwner) {
    val viewMode : MovieViewModel = viewModel()
    val tabs = listOf("Trending", "Upcoming", "Popular", "Top Rated")
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.fillMaxWidth()) {





//        Image(modifier = Modifier
//            .width(50.dp)
//            .height(50.dp), painter = painterResource(id = com.mesum.showtimes.R.drawable.app_icon), contentDescription = null)


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

        when(selectedTabIndex){
            0 -> TrendingMoviesScreen()
            1 -> UpcomingMoviesScreen()
            3 -> PopularMoviesScreen()
            4 -> TopRateMoviesScreen()
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