package com.mesum.showtimes

import android.graphics.Movie
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.mesum.showtimes.ui.theme.MoviesAndTvShowsScreen
import com.mesum.showtimes.ui.theme.PopularMoviesScreen
import com.mesum.showtimes.ui.theme.ShowTimesTheme
import com.mesum.showtimes.ui.theme.TopRateMoviesScreen
import com.mesum.showtimes.ui.theme.TrendingMoviesScreen
import com.mesum.showtimes.ui.theme.UpcomingMoviesScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel : MovieViewModel = viewModel()

            val navController = rememberNavController()
            ShowTimesTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    }
                    Scaffold(bottomBar = {
                        BottomNavigation(navController, viewModel)
                    }, topBar = {
                        TopAppBar(
                            title = { Text(text = "FilmFlix", fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Light)}, navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(painter = painterResource(id = R.drawable.app_icon), contentDescription = "", modifier = Modifier.height(24.dp).width(24.dp))
                                }

                            },
                        )
                    }){
                        it

                        NavHost(
                            navController = navController,
                            startDestination = Movies.Trending.name,
                            modifier = Modifier.padding(),
                        ) {
                            composable(route = Movies.Trending.name) {
                                TrendingMoviesScreen({

                                    //navigate
                                    navController.navigate(ShowTime.Trailer.name)

                                }) { video ->
                                    viewModel.updatedTrailer(video)

                                }
                            }

                            composable(route = Movies.Upcoming.name) {
                                UpcomingMoviesScreen({

                                    //navigate
                                    navController.navigate(ShowTime.Trailer.name)


                                }, { video ->
                                    viewModel.updatedTrailer(video)

                                })


                            }

                            composable(route = Movies.Popular.name) {
                                PopularMoviesScreen({

                                    //navigate
                                    navController.navigate(ShowTime.Trailer.name)

                                }, { video ->
                                    viewModel.updatedTrailer(video)

                                })


                            }
                            composable(route = Movies.TopRated.name) {
                                TopRateMoviesScreen({

                                    //navigate
                                    navController.navigate(ShowTime.Trailer.name)


                                }, { video->
                                    viewModel.updatedTrailer(video)
                                })
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
            }
        }
    }
}



data class BottomNavigationItem(val title: String, val selectedIcon: Painter, val unselectedIcon: Painter)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigation(navController: NavHostController, viewModel: MovieViewModel) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val trending: Painter = painterResource(id = R.drawable.trending)
    val upcoming : Painter = painterResource(id = R.drawable.upcoming)
    val popular : Painter = painterResource(id = R.drawable.popular)
    val topRated : Painter = painterResource(id = R.drawable.toprated)

    val movieCategories = listOf(
        BottomNavigationItem("Trending",trending, trending),
        BottomNavigationItem("Upcoming", upcoming, upcoming),
        BottomNavigationItem("Popular", popular, popular),
        BottomNavigationItem("Top Rated", topRated, topRated)
    )



    NavigationBar() {
        
        movieCategories.forEachIndexed { index, bottomNavigationItem ->

            NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                selectedItemIndex = index

                when(selectedItemIndex){
                    0 ->{   navController.navigate(Movies.Trending.name) }
                    1 ->{navController.navigate(Movies.Upcoming.name)}
                    2 -> {navController.navigate(Movies.Popular.name)}
                    3 -> {navController.navigate(Movies.TopRated.name)}

                }

            }, icon = {
                Icon(painter =
                    if (index == selectedItemIndex) {
                        bottomNavigationItem.selectedIcon

                    }else{
                        bottomNavigationItem.selectedIcon
                    }
                , contentDescription =bottomNavigationItem.title )
            },

                label = {
                    Text(text = bottomNavigationItem.title , style = TextStyle(
                        color = if (index == selectedItemIndex){
                            Color.LightGray
                        }else{
                            Color.Gray
                        }
                    ))
                })


        }
        

    }
    

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShowTimesTheme {
    }
}

enum class Movies(@StringRes val title: Int) {
    Trending(title = R.string.trending),
    Upcoming(title = R.string.upcoming),
    Popular(title =R.string.popular),
    TopRated(title = R.string.top_rated)
}

