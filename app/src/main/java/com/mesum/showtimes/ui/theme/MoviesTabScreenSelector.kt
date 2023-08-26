package com.mesum.showtimes.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.mesum.showtimes.data.Result



@Composable
fun MoviesAndTvShowsScreen(
    onMovieClicked: () -> Unit,
    video: (Result) -> Unit,
) {
    val viewModel: MovieViewModel = viewModel()
    val tabs = listOf("Trending", "Upcoming", "Popular", "Top Rated")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        AppBar(tabs, selectedTabIndex) { index -> selectedTabIndex = index }
        ContentSection(selectedTabIndex, onMovieClicked, video)
    }
}

@Composable
fun AppBar(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 10.dp)
    ) {
        LogoSection()
        Spacer(modifier = Modifier.width(16.dp))
        TabsSection(tabs, selectedTabIndex, onTabSelected)
    }
}

@Composable
fun LogoSection() {
    Image(
        modifier = Modifier
            .size(30.dp),
        painter = painterResource(id = com.mesum.showtimes.R.drawable.app_icon),
        contentDescription = null
    )
}

@Composable
fun TabsSection(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
        edgePadding = 8.dp
    ) {
        tabs.forEachIndexed { index, text ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier.height(50.dp),
                text = { Text(text) }
            )
        }
    }
}

@Composable
fun ContentSection(
    selectedTabIndex: Int,
    onMovieClicked: () -> Unit,
    video: (Result) -> Unit,
) {
    when (selectedTabIndex) {
        0 -> TrendingMoviesScreen(onMovieClicked, video)
        1 -> UpcomingMoviesScreen(onMovieClicked, video)
        2 -> PopularMoviesScreen(onMovieClicked, video)
        3 -> TopRateMoviesScreen(onMovieClicked, video)
    }
}



@Composable
fun MovieGridItem(movie: Result, onClick: () -> Unit, video: (Result) -> Unit) {
    // Customize the UI for each movie item in the grid here
    Column(
        modifier = Modifier
            .padding(8.dp, bottom = 12.dp)
            .fillMaxWidth()
            .clickable { onClick()
            video(movie)
            }
    ) {

        SubcomposeAsyncImage(model ="https://image.tmdb.org/t/p/w500/${movie.poster_path}" ,
            contentDescription = null, loading = { CircularProgressIndicator()})
        var currentRating by remember { mutableStateOf(0f) }
        Spacer(modifier = Modifier.height(4.dp))

        RatingBar(
            rating = movie.vote_average.toFloat(),
            maxRating = 5,
            onRatingChanged = { newRating ->
                currentRating = newRating
            }
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = movie.title,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Int = 5,
    onRatingChanged: (Float) -> Unit
) {
    val convertedRating = rating / 2 // Convert rating from 0-10 to 0-5

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= convertedRating) Icons.Filled.Star else Icons.Default.Star,
                contentDescription = null,
                tint = if (i <= convertedRating) Color(0xFFFFC107) else  Color(0xFFE0E0E0),
                modifier = Modifier
                    .clickable { onRatingChanged(i.toFloat() * 2) }.padding(4.dp) // Convert back to 0-10 range
            )
        }
    }
}

